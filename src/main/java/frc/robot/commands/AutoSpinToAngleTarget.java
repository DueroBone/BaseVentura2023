// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;

public class AutoSpinToAngleTarget extends CommandBase {

  private final DriveTrain m_driveTrain;
  private double targetAngle;
  private double turnPower;

  private PIDController pid;
  private static final double kP = 0.03;    // P term constant (Proportional)
  private static final double kI = 0.00;    // I term constant (Integral)
  private static final double kD = 0.00;    // D term constant (Derivative)
  //private static final double kF = 0.0001;  // F term constant (Feedforward)

  private static double currentHeading;
  private double currentDiff;
  private double speed = 0.0;          // adjusted turn rate based on closeness to target 
  private double slowDownReducer = 0.85;  // amount to reduce power when near target
  private boolean inRange = false;
  private int counter1 = 2;
  private int counter2 = 2;
  private int counter3 = 2;

  public static int videoWidth = 640;
  public static int videoHeight = 480;
  private static double tolerance = 3;


  public AutoSpinToAngleTarget(double turnPowerIn, double targetAngleIn) {

    this.m_driveTrain = RobotContainer.m_driveTrain; // get driveTrain object from RobotContainer
    this.targetAngle = targetAngleIn;   // in degrees from current heading
    this.turnPower = turnPowerIn;

    this.pid = new PIDController(kP, kI, kD);    // create PID object 
 
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(this.m_driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    DriveTrain.stop();        // make sure robot is stopped
    DriveTrain.resetGyro();   // reset gyro so 0 is current heading
    pid.reset();
    pid.setSetpoint(targetAngle);
    pid.setTolerance(tolerance);    // set tolerance around setpoint, targetAngle in this case
    pid.enableContinuousInput(-180.0, 180.0); // Enable continuous input in range from -180 to 180
    System.out.println("**starting AutoSpinToAnglePID target angle: " +targetAngle +" heading: "+String.format("%.3f", m_driveTrain.getHeadingAngle()));
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    currentHeading = m_driveTrain.getHeadingAngle();      // get current heading
    currentDiff = targetAngle - currentHeading;         // get how far off we are
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

  //public static boolean between(double i, double minValueInclusive, double maxValueInclusive) {
  //  return (i >= minValueInclusive && i <= maxValueInclusive);
  //}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    DriveTrain.stop();  // stop driveTrain on exit
    System.out.println("**ending AutoSpinToAnglePID command  current heading: " + String.format("%.3f", currentHeading));
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return inRange;
  }
}
