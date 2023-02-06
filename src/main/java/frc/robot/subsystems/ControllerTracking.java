package frc.robot.subsystems;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.HIDType;
import frc.robot.RobotContainer.*;
//controller.getType()  Ps4 = kHIDGamepad  Xbox = kXInputGamepad  ATK3 = kHIDJoystick 
class Xbox1 {
  public static HIDType Type = HIDType.kXInputGamepad;
  public static String Name = null;
  public static int Port = 5;
}
class Xbox2 {
  public static HIDType Type = HIDType.kXInputGamepad;
  public static String Name = null;
  public static int Port = 5;
}

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
    dynamicControllerXbox1.object = new XboxController(portOf(JoystickTypes, isXbox, JoystickNames));
    dynamicControllerPlaystation1.object= new XboxController(portOf(JoystickTypes, HIDType.kHIDGamepad, JoystickNames));
    dynamicJoystick1.object = new Joystick(portOf(JoystickTypes, HIDType.kHIDJoystick, JoystickNames));
  }

  private static int portOf(HIDType[] HIDarray, HIDType type, String[] NAMEarray) {
    int[] PossiblePorts = new int[HIDarray.length];
    for (int i = 0; i < PossiblePorts.length; i++) {PossiblePorts[i] = 0;} //Fill possible with zeros

    for (int i = 0; i < HIDarray.length; i++) {
      if (HIDarray[i] == type) {
        PossiblePorts[indexOfNumber(PossiblePorts, 0)] = i;
      }
    }
    PossiblePorts = removeEmptyFromArray(PossiblePorts); //All controllers of type | id,port
    if (PossiblePorts.length == 1) {
      return PossiblePorts[0];
    }

    if (type == isXbox) {
      if ((Xbox1.Name == null) && (Xbox2.Name == null)) {
        Xbox1.Name = DriverStation.getJoystickName(PossiblePorts[0]);
      } else if (Xbox2.Name == null) {
        Xbox2.Name = DriverStation.getJoystickName(PossiblePorts[1]);
      } else if (Xbox1.Port == PossiblePorts[0]) {

      }
    } else if (type == isPS4) {

    } else if (type == isJoystick){

    }
    return 0; 
  }
  private static int indexOfNumber(int[] IntArray, int number) {
    for (int i = 0; i < IntArray.length; i++) {
      if (IntArray[i] == number) {
        return i;
      }
    }
    return 5;
  }
  private static int[] removeEmptyFromArray(int[] InputArray) {
    InputArray[1] = 0;
    int[] output = new int[0];
    int i2 = 0;
    for (int i = 0; i < InputArray.length; i++) {
      if (InputArray[i] != 0) {
        output[i2++] = InputArray[i];
      }
    }
    return output;
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
