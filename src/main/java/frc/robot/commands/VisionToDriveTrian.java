package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import frc.robot.RobotContainer;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.math.controller.PIDController;


public class VisionToDriveTrian {
  public static int videoWidth = 640;
  public static int videoHeight = 480;
  public static double X;
  public static double Y;
  public static void track() {
    //Set x and y to correct
    convert(X, Y);
    //find closest to center

  }
  static void convert(double XInput, double YInput) {
    X = XInput/videoWidth;
    Y = YInput/videoHeight;
  }





  public static void AutoSpinToAnglePID(double turnPowerIn, double targetAngleIn) {
    double tolerance = 3;
    final double kP = 0.03;    // P term constant (Proportional)
    final double kI = 0.00;    // I term constant (Integral)
    final double kD = 0.00;    // D term constant (Derivative)
    // final double kF = 0.0001;  // F term constant (Feedforward)
    double speed = 0.0;          // adjusted turn rate based on closeness to target 
    double slowDownReducer = 0.85;  // amount to reduce power when near target
    boolean inRange = false;
    int counter1 = 2;
    int counter2 = 2;
    int counter3 = 2;

    DriveTrain m_driveTrain = RobotContainer.m_driveTrain; // get driveTrain object from RobotContainer
    double targetAngle = targetAngleIn;   // in degrees from current heading
    double turnPower = turnPowerIn;

    PIDController pid = new PIDController(kP, kI, kD);    // create PID object 
    DriveTrain.stop();        // make sure robot is stopped
    DriveTrain.resetGyro();   // reset gyro so 0 is current heading
    pid.reset();
    pid.setSetpoint(targetAngle);
    pid.setTolerance(tolerance);    // set tolerance around setpoint, targetAngle in this case
    pid.enableContinuousInput(-180.0, 180.0); // Enable continuous input in range from -180 to 180
    System.out.println("**starting AutoSpinToAnglePID target angle: " +targetAngle +" heading: "+String.format("%.3f", m_driveTrain.getHeadingAngle()));
    
    
    //needs to run continously
    double currentHeading = m_driveTrain.getHeadingAngle();      // get current heading
    double currentDiff = targetAngle - currentHeading;         // get how far off we are
    inRange = (Math.abs(currentDiff)  <= tolerance);

    if (inRange) {
      DriveTrain.stop();  // in range so stop 
      System.out.println("**in range stop turn - heading: "+String.format("%.3f", currentHeading));
    } else {
      // clamp pid output to between -turnPower and +turnPower
      double pidOutput =  MathUtil.clamp(pid.calculate(currentHeading, targetAngle), -turnPower, turnPower);
      if (counter1++ % 3 == 0) { System.out.println("**diff: "+String.format("%.3f", currentDiff)+" heading: "+String.format("%.3f", currentHeading)+" target: "+targetAngle+" inRange: "+inRange); }
      if (pidOutput >= -0.5 && pidOutput <= 0.5) {
        speed = turnPower * slowDownReducer;   // slow down if close to target
      } else {
        speed = turnPower;          // else do speed requested 
      }
      if (pidOutput <= 0) {
        if (counter2++ % 3 == 0) { System.out.println("**turn Left Correction pidOut: "+String.format("%.3f  ", pidOutput)+" heading: "+String.format("%.3f", currentHeading)); }
        DriveTrain.doTankDrive(-speed, speed); // turn to left ( reverse left side)
      } else {
        if (counter3++ % 3 == 0) { System.out.println("**turn Right Correction pidOut: "+String.format("%.3f  ", pidOutput)+" heading: "+String.format("%.3f", currentHeading)); }
        DriveTrain.doTankDrive(speed, -speed); // turn to right ( reverse right side)
      }
    }
  }
}