package frc.robot.commands;

import java.text.MessageFormat;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;
import java.lang.Math;

public class GoTele extends CommandBase {
    @Override
    public void initialize() {
      System.out.println(MessageFormat.format("**Started {0} ", this.getName()));
    }
    private double teleLeft = 0;
    private double teleRight = 0;
    private boolean GoTeleEnabled = true;
    private double deadzone = -1;
    private final DriveTrain drivetrain;

    public GoTele (boolean enabled, double deadzone) {
        this.drivetrain = RobotContainer.m_driveTrain;    // get driveTrain object from RobotContainer
        this.GoTeleEnabled = enabled;
        this.deadzone = deadzone;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(this.drivetrain); 
      }

    @Override
    public void execute() {
      //double deadzone = 0.0;
      boolean usingCon0 = Math.abs(RobotContainer.controller0.getLeftY()) > deadzone || Math.abs(RobotContainer.controller0.getRightY()) > deadzone;
      boolean usingCon2 = Math.abs(RobotContainer.controller2.getLeftY()) > deadzone || Math.abs(RobotContainer.controller2.getRightY()) > deadzone;
      boolean usingCon5 = Math.abs(RobotContainer.controller5.getY()) > deadzone || Math.abs(RobotContainer.controller5.getX()) > deadzone;
      double teleLeft = 0;
      double teleRight = 0;
      double teleRotate = 0;
      double teleSpeed = 0;

      if (RobotContainer.controller0.isConnected() && usingCon0) {
        //Using two controllers
        teleLeft = RobotContainer.controller0.getLeftY() * -1;
        teleRight = RobotContainer.controller0.getRightY() * -1;
      } else { 
        if (RobotContainer.controller2.isConnected() && usingCon2) {
          //Using two controllers
          teleLeft = RobotContainer.controller2.getLeftY() * -0.5;
          teleRight = RobotContainer.controller2.getRightY() * -0.5;
        } else {
          if (RobotContainer.controller5.isConnected() && usingCon5) {
            //Using single joystick
            teleRotate = RobotContainer.controller5.getX() * 1;
            teleSpeed = RobotContainer.controller5.getY() * -1;
          } else {
            teleLeft = 0;
            teleRight = 0;
          }
        }
      }
      // RobotContainer.controller0.getType()  Ps4 = kHIDGamepad  Xbox = kXInputGamepad  ATK3 = kHIDJoystick 
      double speedMultiplier = 1;
      double a = 1 - deadzone;
      a = 1 / a;

      if (Math.abs(teleLeft) > deadzone) {
        if (teleLeft>0){
          teleLeft = teleLeft - deadzone;
        } else {
          teleLeft = teleLeft + deadzone;
        }
        teleLeft = teleLeft * a;
        teleLeft = smartSquare(teleLeft, 1);
        teleLeft = teleLeft * speedMultiplier;
      } else {teleLeft = 0;}

      if (Math.abs(teleRight) > deadzone) {
        if (teleRight>0){
          teleRight = teleRight - deadzone;
        } else {
          teleRight = teleRight + deadzone;
        }
        teleRight = teleRight * a;
        teleRight = smartSquare(teleRight, 1);
        teleRight = teleRight * speedMultiplier;
      } else {teleRight = 0;}

      if (Math.abs(teleSpeed) > deadzone) {
        if (teleSpeed>0){
          teleSpeed = teleSpeed - deadzone;
        } else {
          teleSpeed = teleSpeed + deadzone;
        }
        teleSpeed = teleSpeed * a;
        teleSpeed = smartSquare(teleSpeed, 1);
        teleSpeed = teleSpeed * speedMultiplier;
      } else {teleSpeed = 0;}

      if (Math.abs(teleRotate) > deadzone) {
        if (teleRotate>0){
          teleRotate = teleRotate - deadzone;
        } else {
          teleRotate = teleRotate + deadzone;
        }
        teleRotate = teleRotate * a;
        teleRotate = smartSquare(teleRotate, 1);
        teleRotate = teleRotate * speedMultiplier;
      } else {teleRotate = 0;}


      //System.out.println("Left Y:" + RobotContainer.controller0.getLeftY() + " Left speed:" + teleLeft + " Right Y:" + RobotContainer.controller0.getRightY() + " Right speed:" + teleRight);
      if (GoTeleEnabled){
        if (usingCon5) {
          DriveTrain.doArcadeDrive(teleSpeed, teleRotate); 
        } else {
          DriveTrain.doTankDrive(teleLeft, teleRight);
        }
      }
      SmartDashboard.putNumber("Left Drive Speed", teleLeft);
      SmartDashboard.putNumber("Right Drive Speed", teleRight);
    }
    @Override
    public boolean isFinished() {
      return false;
    }
    private static double smartSquare(double input, int exponent) {
      double output = Math.pow(input, exponent);
      if (Math.signum(input) != Math.signum(output)) {
        output = output * -1;
      }
      return output;
    }
}
