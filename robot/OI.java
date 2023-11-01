/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	//Declaring the joysticks that will be used
	static Joystick driveController = new Joystick(0);
	static Joystick manipController = new Joystick(1);


	public static final class manipButtons {
		public static final Button a = new JoystickButton(manipController, 1);
		public static final Button b = new JoystickButton(manipController, 2);
		public static final Button x = new JoystickButton(manipController, 3);
		public static final Button y = new JoystickButton(manipController, 4);
		public static final Button lb = new JoystickButton(manipController, 5);
		public static final Button rb = new JoystickButton(manipController, 6);
		public static final Button start = new JoystickButton(manipController, 7);
		public static final Button select = new JoystickButton(manipController, 8);
		public static final Button leftStickButton = new JoystickButton(manipController, 9);
		public static final Button rightStickButton = new JoystickButton(manipController, 10);
		public static final POVButton dPadRight = new POVButton(manipController, 270);
		public static final POVButton dPadUpRight = new POVButton(manipController, 315);
		public static final POVButton dPadUp = new POVButton(manipController, 0);
		public static final POVButton dPadUpLeft = new POVButton(manipController, 45);
		public static final POVButton dPadLeft = new POVButton(manipController, 90);
		public static final POVButton dPadDownLeft = new POVButton(manipController, 135);
		public static final POVButton dPadDown = new POVButton(manipController, 180);
		public static final POVButton dPadDownRight = new POVButton(manipController, 225);
	}
	
	public static final class driveButtons {
		public static final Button a = new JoystickButton(driveController, 1);
		public static final Button b = new JoystickButton(driveController, 2);
		public static final Button x = new JoystickButton(driveController, 3);
		public static final Button y = new JoystickButton(driveController, 4);
		public static final Button lb = new JoystickButton(driveController, 5);
		public static final Button rb = new JoystickButton(driveController, 6);
		public static final Button start = new JoystickButton(driveController, 7);
		public static final Button select = new JoystickButton(driveController, 8);
		public static final Button leftStickButton = new JoystickButton(driveController, 9);
		public static final Button rightStickButton = new JoystickButton(driveController, 10);
		public static final POVButton dPadRight = new POVButton(driveController, 270);
		public static final POVButton dPadUpRight = new POVButton(driveController, 315);
		public static final POVButton dPadUp = new POVButton(driveController, 0);
		public static final POVButton dPadUpLeft = new POVButton(driveController, 45);
		public static final POVButton dPadLeft = new POVButton(driveController, 90);
		public static final POVButton dPadDownLeft = new POVButton(driveController, 135);
		public static final POVButton dPadDown = new POVButton(driveController, 180);
		public static final POVButton dPadDownRight = new POVButton(driveController, 225);
	}

	public static double getLeftX(Joystick stick) {
		return stick.getRawAxis(0);
	}
	public static double getLeftY(Joystick stick) {
		//Returns the inverted value because pushing the stick forward returns a negative value
		return -stick.getRawAxis(1);
	}
	public static double getRightX(Joystick stick) {
		return stick.getRawAxis(4);
	}
	public static double getRightY(Joystick stick) {
		//Returns the inverted value because pushing the stick forward returns a negative value
		return -stick.getRawAxis(5);
	}
	public static double getLeftTrigger(Joystick stick) {
		return stick.getRawAxis(2);
	}
	public static double getRightTrigger(Joystick stick) {
		return stick.getRawAxis(3);
	}
	
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
}

  
	
  