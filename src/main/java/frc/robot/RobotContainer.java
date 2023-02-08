// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.GoTele;
import frc.robot.subsystems.Light;
import frc.robot.subsystems.ControllerTracking;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj2.command.Command;


public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  //private final Piston m_piston = new Piston();
  public static final DriveTrain m_driveTrain = new DriveTrain();
  

  Compressor c = new Compressor(5, PneumaticsModuleType.REVPH);
  public static class legacyControllers {
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
  }

  public static class dynamicControllerXbox1 {
    public static XboxController object = new XboxController(5);
    public static JoystickButton A;
    public static JoystickButton B;
    public static JoystickButton X;
    public static JoystickButton Y;
    public static JoystickButton LeftBumper;
    public static JoystickButton RightBumper;
    public static JoystickButton LeftStickPress;
    public static JoystickButton RightStickPress;
    public static JoystickButton Share;
    public static JoystickButton Options;
    public static String Name = null;
    public static void updateController() {
      ControllerTracking.updatePortNumbers();
      System.out.println("Assigning dXbox: " + object.getPort());
      A = new JoystickButton(object, OIConstants.SmartMap(object, "A"));
      B = new JoystickButton(object, OIConstants.SmartMap(object, "B"));
      X = new JoystickButton(object, OIConstants.SmartMap(object, "X"));
      Y = new JoystickButton(object, OIConstants.SmartMap(object, "Y"));
      LeftBumper = new JoystickButton(object, OIConstants.SmartMap(object, "LBump"));
      RightBumper = new JoystickButton(object, OIConstants.SmartMap(object, "RBump"));
      LeftStickPress = new JoystickButton(object, OIConstants.SmartMap(object, "LStick"));
      RightStickPress = new JoystickButton(object, OIConstants.SmartMap(object, "RStick"));
      Share = new JoystickButton(object, OIConstants.SmartMap(object, "DoubleSquare"));
      Options = new JoystickButton(object, OIConstants.SmartMap(object, "Options"));
    }
  }
  public static class dynamicControllerPlaystation1 {
    public static XboxController object = new XboxController(5);
    public static JoystickButton A;
    public static JoystickButton B;
    public static JoystickButton X;
    public static JoystickButton Y;
    public static JoystickButton LeftBumper;
    public static JoystickButton RightBumper;
    public static JoystickButton LeftStickPress;
    public static JoystickButton RightStickPress;
    public static JoystickButton Share;
    public static JoystickButton Options;
    public static String Name = null;
    public static void updateController() {
      ControllerTracking.updatePortNumbers();
      assignButtons();
    }
    public static void assignButtons() {
      System.out.println("Assigning dPlaystations: " + object.getPort());
      A = new JoystickButton(object, OIConstants.SmartMap(object, "A"));
      B = new JoystickButton(object, OIConstants.SmartMap(object, "B"));
      X = new JoystickButton(object, OIConstants.SmartMap(object, "X"));
      Y = new JoystickButton(object, OIConstants.SmartMap(object, "Y"));
      LeftBumper = new JoystickButton(object, OIConstants.SmartMap(object, "LBump"));
      RightBumper = new JoystickButton(object, OIConstants.SmartMap(object, "RBump"));
      LeftStickPress = new JoystickButton(object, OIConstants.SmartMap(object, "LStick"));
      RightStickPress = new JoystickButton(object, OIConstants.SmartMap(object, "RStick"));
      Share = new JoystickButton(object, OIConstants.SmartMap(object, "DoubleSquare"));
      Options = new JoystickButton(object, OIConstants.SmartMap(object, "Options"));
    }
  }
  public static class dynamicJoystick1 {
    public static Joystick object = new Joystick(5);
    public static JoystickButton Trigger;
    public static JoystickButton Two;
    public static JoystickButton Three;
    public static JoystickButton Four;
    public static JoystickButton Five;
    public static JoystickButton Six;
    public static JoystickButton Seven;
    public static JoystickButton Eight;
    public static JoystickButton Nine;
    public static JoystickButton Ten;
    public static JoystickButton Eleven;
    public static String Name = null;
    public static void updateController() {
      ControllerTracking.updatePortNumbers();
      assignButtons();
    }
    public static void assignButtons() {
      System.out.println("Assigning dJoystick: " + object.getPort());
      Trigger = new JoystickButton(object, OIConstants.SmartMap(object, "Trigger"));
      Two = new JoystickButton(object, 2);
      Three = new JoystickButton(object, 3);
      Four = new JoystickButton(object, 4);
      Five = new JoystickButton(object,5 );
      Six = new JoystickButton(object, 6);
      Seven = new JoystickButton(object, 7);
      Eight = new JoystickButton(object, 8);
      Nine = new JoystickButton(object, 9);
      Ten = new JoystickButton(object, 10);
      Eleven = new JoystickButton(object, 11);
    }
  }
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    m_driveTrain.setDefaultCommand(new GoTele(true, 0.1));
    //m_driveTrain.setDefaultCommand(new DriveCommand(() -> controller0.getLeftY(), () -> controller0.getRightY()));
    // Configure the button bindings
    configureButtonBindings();
    DriverStation.silenceJoystickConnectionWarning(true);
  }

  private void configureButtonBindings() {
    //con0ButtonA.whenPressed(() -> c.enableAnalog(30, 60));
    //con0BumperLeft.whileActiveContinuous(() -> System.out.println(c.getCurrent())); 
    //con2ButtonA.whileActiveContinuous(() -> System.out.println(RobotController.getBatteryVoltage() + " Hi"));
    //con0ButtonX.whileActiveContinuous(() -> System.out.println(c.getPressure()));
    dynamicControllerXbox1.updateController();    
    dynamicControllerPlaystation1.updateController();
    dynamicJoystick1.updateController();
    dynamicControllerXbox1.A.whileActiveContinuous(()-> System.out.println(dynamicControllerXbox1.object.getPort() + ": dynamic XBOX"));
    dynamicControllerPlaystation1.A.whileActiveContinuous(()-> System.out.println(dynamicControllerPlaystation1.object.getPort() + ": dynamic Playstation"));
    dynamicJoystick1.Trigger.whenPressed(()-> Light.toggle());
    dynamicControllerXbox1.RightBumper.whenPressed(()-> Light.toggle());
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
