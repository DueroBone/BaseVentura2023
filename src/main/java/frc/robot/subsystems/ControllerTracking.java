package frc.robot.subsystems;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.HIDType;
import frc.robot.RobotContainer.*;
// controller.getType()  Ps4 = kHIDGamepad  Xbox = kXInputGamepad  ATK3 = kHIDJoystick 

public class ControllerTracking {
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
      dynamicControllerXbox1.object = new XboxController(indexOf(JoystickTypes, HIDType.kXInputGamepad, JoystickNames, null));
      dynamicControllerPlaystation1.object= new XboxController(indexOf(JoystickTypes, HIDType.kHIDGamepad, JoystickNames, null));
      dynamicJoystick1.object = new Joystick(indexOf(JoystickTypes, HIDType.kHIDJoystick, JoystickNames, null));
  }
  private static int indexOf(HIDType[] HIDarray, HIDType type, String[] NAMEarray, String[] name) {
      for (int i = 0; i < HIDarray.length; i++) {
        if (HIDarray[i] == type) {
          return i;
        }
      }
      return 5;
    }
}
