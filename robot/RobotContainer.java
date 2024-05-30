// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final DriveTrain m_drivetrain = new DriveTrain();
  private final XboxController m_driveController =
      new XboxController(0);


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    m_drivetrain.setDefaultCommand(new RunCommand(
      () -> 
          // m_drivetrain.driveArcade(
          //   MathUtil.applyDeadband(m_driveController.getLeftY(), Constants.OIConstants.kDriveDeadband),
          //   MathUtil.applyDeadband(m_driveController.getRightX(), Constants.OIConstants.kDriveDeadband))
        m_drivetrain.driveTank(
          MathUtil.applyDeadband(m_driveController.getLeftY(), 0.05),
          MathUtil.applyDeadband(m_driveController.getRightY(), 0.05))
      , m_drivetrain)
    );
    // m_Intake.setDefaultCommand(new RunCommand(
    //   () -> 
    //       // m_drivetrain.driveArcade(
    //       //   MathUtil.applyDeadband(m_driveController.getLeftY(), Constants.OIConstants.kDriveDeadband),
    //       //   MathUtil.applyDeadband(m_driveController.getRightX(), Constants.OIConstants.kDriveDeadband))
    //     m_Intake.spinForward(
    //       m_driveController.getLeftBumper())
    //   , m_Intake)
    // );
    // m_Intake.setDefaultCommand(new RunCommand(
    //   () -> 
    //       // m_drivetrain.driveArcade(
    //       //   MathUtil.applyDeadband(m_driveController.getLeftY(), Constants.OIConstants.kDriveDeadband),
    //       //   MathUtil.applyDeadband(m_driveController.getRightX(), Constants.OIConstants.kDriveDeadband))
    //     m_Intake.spinBackwards(
    //       m_driveController.getRightBumper())
    //   , m_Intake)
    // );

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public boolean getAutonomousCommand() {
    // An example command will be run in autonomous
    return true;
  }
}
