/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI.driveButtons;
import frc.robot.OI.manipButtons;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

	//Declaring subsystems
  	public static final RobotMap map
	= new RobotMap();		//Maps the robot
	public static final ColorSensor kColorSensor 
	= new ColorSensor();     //Controls the color sensor 
	public static final OI m_oi
	= new OI();		//Object Interface. This creates the controllers
    public static final DriveTrain Drive
	= new DriveTrain();		//The drive train for the robot. Includes all the methods for driving the robot
	public static final Shooter kShooter
	= new Shooter();
    public static final limelight kLimelight
    = new limelight();		//The vision tracking classes require this to be used
    // public static final pneumatics kPneumatics
    // = new pneumatics();		//Pneumatic controls
	public static final encoder encode 
	= new encoder();	//Controls all the encoders
    public static final Timer time
	= new Timer();		//Used for keeping track of time
	public static final NavX kNavX
	= new NavX();
	// public static final Lidar kLidar
	// = new Lidar();

	//Declaring commands
	public static SeekVisionTarget kSeekVisionTarget
	= new SeekVisionTarget();
	public static ColorFinder kColorFinder
	= new ColorFinder();
	public NavXSeeker kNavXSeeker
	= new NavXSeeker();
	// public static RotationControl kRotationControl
	// = new RotationControl();

	double speedDrop;

	public static boolean hasCrashed = false;

	public boolean button = false;

    //Declaring commands
	Command m_autonomousCommand;
	
	//Used for the camera controls
	public static boolean primaryCamActive;
	
	//I really don't know what this thing is it was here when I made the program and I haven't bothered to figure out what it does yet
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */

//Declaring OI

	public void robotInit() {
		//Mapping all the hardware
		map.mapAll();
		kShooter.init();

		//limit = new DigitalInput(0);
		//encoder = new Encoder(9, 8);
	    
	  //Autonomous stuff I haven't figured out yet
	  		//m_chooser.addDefault("Default Auto", new ExampleCommand());
	  		// chooser.addObject("My Auto", new MyAutoCommand());
	  		SmartDashboard.putData("Auto mode", m_chooser);
	  		
	  		//Prepares the pneumatics
	  		// kPneumatics.clearStickyFault();
	  		// kPneumatics.solenoidOff();
	  		// kPneumatics.compressorOn();
	  		
			primaryCamActive = true;
		kColorSensor.robotColorValues();

	}
	
	// TODO: Figure out what this is for
	@Override
	public void robotPeriodic() {
		// TODO Auto-generated method stub
		super.robotPeriodic();
	}
	
	@Override
	public void disabledInit() {
		Drive.stop();
		
		super.disabledInit();
		System.out.println("disabled");
		//encoder.reset();
		kLimelight.setPipe(1);
	}
	
	@Override
	public void disabledPeriodic() {
		// TODO Auto-generated method stub
		super.disabledPeriodic();
	}
	
	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_chooser.getSelected();

		// String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		// switch(autoSelected) 
		// { case "My Auto": autonomousCommand
		// 	= new NavXSeeker(); 
		// 	break; 
		// case "Default Auto": 
		// default:
		// 	autonomousCommand = new ExampleCommand(); 
		// 	break; 
		// }
		
		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
		kLimelight.setStreamPrimary();
		//kLimelight.setStreamSecondary();
		kLimelight.setPipe(1);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
	
	@Override
	public void teleopInit() {
		System.out.println("*************************************************************************************");
		System.out.println("*************************************************************************************");
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
//		if (m_autonomousCommand != null) {
//			m_autonomousCommand.cancel();
//		}
		Drive.stop();

		//Initalizing variables
		kColorFinder.colorTargetValue = "Red";

		//Used to regulate the speed of the drive train
		speedDrop = 0.3;

		//Reseting the navX
		kNavX.resetNavx();
		kNavX.resetDisplacement();
	}
	
	@Override
	public void teleopPeriodic() {
		//Checks to see if left button one is pressed
		if (driveButtons.start.get()) {
			//Runs vision seeking controls
			driveButtons.start.whileHeld(kNavXSeeker);
		}
		else {
			//Runs manual controls
			teleOpControl();
		}
		Scheduler.getInstance().run();

		SmartDashboard.putBoolean("limit switch top:", map.lswitchTop.get());
		SmartDashboard.putBoolean("limit switch bottom:", map.lswitchBottom.get());
		SmartDashboard.putNumber("Pitch:", kNavX.getIMUPitch());
		SmartDashboard.putNumber("Yaw:", kNavX.getYaw());
		SmartDashboard.putNumber("Roll:", kNavX.getRoll());
		hasCrashed = kNavX.hasCrashed();
		SmartDashboard.putBoolean("Collision detected: ", hasCrashed);
		SmartDashboard.putBoolean("Arm position up:", kShooter.armPositionUp);

		Robot.kShooter.moveToPosition();
	}

	public void teleOpControl() {
		//Prints the encoder reading
		//System.out.println("Encoder: " + encoder.getDistance());
		//Drive controls
		
		//Runs manual controls
		Drive.driveTank(m_oi.getLeftY(m_oi.driveController)*speedDrop, m_oi.getRightY(m_oi.driveController)*speedDrop);

		//This controls the intake arm of the robot
		//find out the directions of the motor before you test the buttons on the robot
		if(driveButtons.a.get() && map.lswitchTop.get()){
			map.shooterLift.set(0.3);
		}
		else if(driveButtons.y.get() && map.lswitchBottom.get()){
			map.shooterLift.set(-0.1);
		}
		else{
			map.shooterLift.set(0);
		}
		// if(manipButtons.lb.get()){
		// 	kShooter.armPositionUp = true;
		// 	button = false;
		// }
		// else if(manipButtons.lb.get() && manipButtons.lb.get()){
		// 	kShooter.armPositionUp = false;
		// 	button = true;
		// }
		
		
		

		//shooter controls
		if(m_oi.getLeftTrigger(m_oi.driveController) > 0) {
			kShooter.shoot();
			
		}
		else if(m_oi.getRightTrigger(m_oi.driveController) > 0) {
			kShooter.intake();
		}
		else{
			kShooter.Stop();
		}

		//Climber controls
		//Forwards
		if(driveButtons.x.get()){
			map.climber.set(-0.5);
		} 
		//Reverse
		else if(manipButtons.x.get()) {
			map.climber.set(0.5);
		}
		else{
			map.climber.set(0);
		}

		//CPI controls
		//m_oi.leftButtons[10].toggleWhenPressed(kRotationControl);
		//m_oi.leftButtons[9].toggleWhenPressed(kColorFinder);
		
		SmartDashboard.putBoolean("limit switch top:", map.lswitchTop.get());
		SmartDashboard.putBoolean("limit switch bottom:", map.lswitchBottom.get());
		SmartDashboard.putNumber("Pitch:", kNavX.getIMUPitch());
		SmartDashboard.putNumber("Yaw:", kNavX.getYaw());
		SmartDashboard.putNumber("Roll:", kNavX.getRoll());
		SmartDashboard.putNumber("DisplacementX:", kNavX.getXDisplacement());
		SmartDashboard.putNumber("DisplacementY:", kNavX.getYDisplacement());
		SmartDashboard.putNumber("DisplacementZ:", kNavX.getZDisplacement());
		hasCrashed = kNavX.hasCrashed();
		SmartDashboard.putBoolean("Collision detected: ", hasCrashed);
		// SmartDashboard.putNumber("Lidar:", kLidar.getDistanceIn(false));

		if (driveButtons.rb.get()) {
			m_oi.driveController.setRumble(RumbleType.kLeftRumble, 1);
			m_oi.driveController.setRumble(RumbleType.kRightRumble, 1);
		}
		else {
			m_oi.driveController.setRumble(RumbleType.kLeftRumble, 0);
			m_oi.driveController.setRumble(RumbleType.kRightRumble, 0);
		}
		
	}

}
