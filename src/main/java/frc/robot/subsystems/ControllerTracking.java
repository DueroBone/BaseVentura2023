package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.HIDType;
import frc.robot.RobotContainer.*;

public class ControllerTracking {
  static HIDType isXbox = HIDType.kXInputGamepad;
  static HIDType isPS4 = HIDType.kHIDGamepad;
  static HIDType isJoystick = HIDType.kHIDJoystick;

  private static HIDType JoystickTypes[] = new HIDType[5];
  private static String JoystickNames[] = new String[5];

  public static void updatePortNumbers() {
    JoystickTypes = new HIDType[5];
    JoystickNames = new String[5];
    GenericHID testHID;
    for (int i = 0; i < JoystickTypes.length; i++) {
      testHID = new GenericHID(i);
      if (testHID.isConnected()) {
        JoystickTypes[i] = testHID.getType();
        JoystickNames[i] = DriverStation.getJoystickName(i);
      }
    }
    dynamicControllerXbox1.object = new XboxController(indexOfType(JoystickTypes, isXbox));
    dynamicControllerPlaystation1.object = new XboxController(indexOfType(JoystickTypes, HIDType.kHIDGamepad));
    dynamicJoystick1.object = new Joystick(indexOfType(JoystickTypes, HIDType.kHIDJoystick));
  }

  private static int indexOfType(HIDType[] HIDarray, HIDType type) {
    for (int i = 0; i < HIDarray.length; i++) {
      if (HIDarray[i] == type) {
        return i;
      }
    }
    return 5;
  }
}
