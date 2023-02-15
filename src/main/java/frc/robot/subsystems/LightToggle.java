import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.TimedRobot;

public class LightToggle {


    PWM pwm = new PWM(0);
    // Set the PWM pulse width in milliseconds
    pwm.setSpeed(1.5);

    // Set the period of the PWM signal in milliseconds
    pwm.setPeriodMs(10);

    // Disable the PWM output
    pwm.disable();
}