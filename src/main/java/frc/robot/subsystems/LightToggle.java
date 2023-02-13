package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public class LightToggle {
  static CANSparkMax sparkLight = new CANSparkMax(10, CANSparkMaxLowLevel.MotorType.kBrushless);

  public LightToggle() {
    sparkLight.set(0.5);
  }

  public static void toggle() {
    if (sparkLight.get() > 0.25) {
      sparkLight.set(0);
    } else {
      sparkLight.set(0.5);
    }
    System.out.println("Light Toggle: " + sparkLight.get());
    // DriverStation.reportError("Light toggle: " + sparkLight.get(), null);
  }
}
