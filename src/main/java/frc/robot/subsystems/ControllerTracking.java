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
        GenericHID testHID;
        for (int i = 0; i < JoystickTypes.length; i++) {
            testHID = new GenericHID(i);
            if (!testHID.isConnected()) {
                JoystickTypes[i] = testHID.getType();
                JoystickNames[i] = DriverStation.getJoystickName(i);
            }
        }
        dynamicControllerXbox.object = new XboxController(indexOf(JoystickTypes, HIDType.kXInputGamepad));
        dynamicControllerXbox.assignButtons();
        //dynamicControllerPlaystation.object= new XboxController(indexOf(JoystickTypes, HIDType.kHIDGamepad));
        //dynamicControllerPlaystation.assignButtons();
        //dynamicJoystick.object = new Joystick(indexOf(JoystickTypes, HIDType.kHIDJoystick));
    }

    private static int indexOf(HIDType[] array, HIDType type) {
        for (int i = 0; i < array.length; i++) {
          if (array[i] == type) {
            return i;
          }
        }
        return 0;
      }
      
}
