// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.GoTele;
import frc.robot.subsystems.Hammer;
import frc.robot.subsystems.Piston;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;


public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  //private final Piston m_piston = new Piston();
  //private final Hammer m_hammer = new Hammer();
  public static final DriveTrain m_driveTrain = new DriveTrain();

  Compressor c = new Compressor(5, PneumaticsModuleType.REVPH);
  //boolean enabled = c.enabled();
  //boolean pressureSwitch = c.getPressureSwitchValue();
  //double current = c.getCompressorCurrent();
  //System.out.println("**Compressor is on " + enabled + "  pressure switch: " + pressureSwitch);

  public static final XboxController controller0 = new XboxController(0);
    public static final JoystickButton con0ButtonA = new JoystickButton(controller0, OIConstants.kXboxButtonA);
    public static final JoystickButton con0ButtonB = new JoystickButton(controller0, OIConstants.kXboxButtonB);
    public static final JoystickButton con0ButtonX = new JoystickButton(controller0, OIConstants.kXboxButtonX);
    public static final JoystickButton con0ButtonY = new JoystickButton(controller0, OIConstants.kXboxButtonY);
    public static final JoystickButton con0ButtonBack = new JoystickButton(controller0, OIConstants.kXboxButtonBack);
    public static final JoystickButton con0ButtonStart = new JoystickButton(controller0, OIConstants.kXboxButtonStart);
	  public static final JoystickButton con0BumperLeft =  new JoystickButton(controller0, OIConstants.kXboxBumperLeft);
    public static final JoystickButton con0StickPressLeft = new JoystickButton(controller0, OIConstants.kXboxStickPressLeft);
    public static final JoystickButton con0StickPressRight = new JoystickButton(controller0, OIConstants.kXboxStickPressRight);
    public POVButton con0PovUp = new POVButton(controller0, 0);
    public POVButton con0PovRight = new POVButton(controller0, 90);
    public POVButton con0PovDown = new POVButton(controller0, 180);
    public POVButton con0PovLeft = new POVButton(controller0, 270);

  public static final XboxController controller2 = new XboxController(2);
    public static final JoystickButton con2ButtonA = new JoystickButton(controller2, OIConstants.kXboxButtonA);
    public static final JoystickButton con2ButtonB = new JoystickButton(controller2, OIConstants.kXboxButtonB);
    public static final JoystickButton con2ButtonX = new JoystickButton(controller2, OIConstants.kXboxButtonX);
    public static final JoystickButton con2ButtonY = new JoystickButton(controller2, OIConstants.kXboxButtonY);
    public static final JoystickButton con2ButtonBack = new JoystickButton(controller2, OIConstants.kXboxButtonBack);
    public static final JoystickButton con2ButtonStart = new JoystickButton(controller2, OIConstants.kXboxButtonStart);
    public static final JoystickButton con2BumperLeft =  new JoystickButton(controller2, OIConstants.kXboxBumperLeft);
    public static final JoystickButton con2StickPressLeft = new JoystickButton(controller2, OIConstants.kXboxStickPressLeft);
    public static final JoystickButton con2StickPressRight = new JoystickButton(controller2, OIConstants.kXboxStickPressRight);
    public static final JoystickButton con2Pad = new JoystickButton(controller2, OIConstants.kPlaystationDuelsBigButton);
    public static final JoystickButton con2Whatever = new JoystickButton(controller2, OIConstants.kPlaystationRightTrigger);
    public POVButton con2PovUp = new POVButton(controller2, 0);
    public POVButton con2PovRight = new POVButton(controller2, 90);
    public POVButton con2PovDown = new POVButton(controller2, 180);
    public POVButton con2PovLeft = new POVButton(controller2, 270);
  
    public static final Joystick controller5 = new Joystick(5);
     public static final JoystickButton con5Trigger = new JoystickButton(controller5, OIConstants.kATK3BigTrigge);
     public static final JoystickButton con5Button2 = new JoystickButton(controller5, OIConstants.kATK3Button2);
     public static final JoystickButton con5Button3 = new JoystickButton(controller5, OIConstants.kATK3Button3);
     public static final JoystickButton con5Button4 = new JoystickButton(controller5, OIConstants.kATK3Button4);
     public static final JoystickButton con5Button5 = new JoystickButton(controller5, OIConstants.kATK3Button5);
     public static final JoystickButton con5Button6 = new JoystickButton(controller5, OIConstants.kATK3Button6);
     public static final JoystickButton con5Button7 = new JoystickButton(controller5, OIConstants.kATK3Button7);
     public static final JoystickButton con5Button8 = new JoystickButton(controller5, OIConstants.kATK3Button8);
     public static final JoystickButton con5Button9 = new JoystickButton(controller5, OIConstants.kATK3Button9);
     public static final JoystickButton con5Button10 = new JoystickButton(controller5, OIConstants.kATK3Button10);
     public static final JoystickButton con5Button11 = new JoystickButton(controller5, OIConstants.kATK3Button11);
     public static final JoystickButton con5Button12 = new JoystickButton(controller5, OIConstants.kATK3Button12);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    m_driveTrain.setDefaultCommand(new GoTele(true, 0.1));
    //m_driveTrain.setDefaultCommand(new DriveCommand(() -> controller0.getLeftY(), () -> controller0.getRightY()));
    // Configure the button bindings
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    con0PovUp.whenPressed(() -> Piston.contract(true));
    con0PovDown.whenPressed(() -> Piston.contract(false));
    con0ButtonA.whenPressed(() -> Piston.pistonToggle());
    con0PovLeft.whenPressed(() -> Hammer.contract(true));
    con0PovRight.whenPressed(() -> Hammer.contract(false));
    con0ButtonY.whenPressed(() -> Hammer.hammerToggle());
    con2Pad.whenPressed(() -> Hammer.hammerToggle());
    con2Whatever.whileActiveContinuous(new StartEndCommand(() -> Hammer.contract(true), 
      () -> Hammer.contract(false)));
    con2ButtonA.whileActiveContinuous(() -> System.out.println(RobotController.getBatteryVoltage() + " Hi"));
  }

  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
