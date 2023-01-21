package frc.robot.commands;

import java.text.MessageFormat;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;
import frc.robot.commands.DriveCommand;
import java.lang.Math;

public class GoTele extends CommandBase {
    @Override
    public void initialize() {
      System.out.println(MessageFormat.format("**Started {0} ", this.getName()));
    }
    private double teleLeft = 0;
    private double teleRight = 0;
    private boolean GoTeleEnabled = true;
    private final DriveTrain drivetrain;

    public GoTele (boolean enabled) {
        this.drivetrain = RobotContainer.m_driveTrain;    // get driveTrain object from RobotContainer
        this.GoTeleEnabled = enabled;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(this.drivetrain); 
      }

    @Override
    public void execute() {
      double deadzone = 0.05;
      boolean usingCon0 = Math.abs(RobotContainer.controller0.getLeftY()) > deadzone || Math.abs(RobotContainer.controller0.getRightY()) > deadzone;
      boolean usingCon2 = Math.abs(RobotContainer.controller2.getLeftY()) > deadzone || Math.abs(RobotContainer.controller2.getRightY()) > deadzone;
      double teleLeft = 0;
      double teleRight = 0;
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
          teleLeft = 0;
          teleRight = 0;
        }
      }

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
        teleLeft = smartSquare(teleLeft, 2);
        teleLeft = teleLeft * speedMultiplier;
      } else {teleLeft = 0;}

      if (Math.abs(teleRight) > deadzone) {
        teleRight = teleRight - deadzone;
        teleRight = teleRight * a;
        teleRight = smartSquare(teleRight, 2);
        teleRight = teleRight * speedMultiplier;
      } else {teleRight = 0;}

      //System.out.println("Left Y:" + RobotContainer.controller0.getLeftY() + " Left speed:" + teleLeft + " Right Y:" + RobotContainer.controller0.getRightY() + " Right speed:" + teleRight);
      if (GoTeleEnabled){
        DriveTrain.doTankDrive(teleLeft, teleRight);
      }
      SmartDashboard.putNumber("Left Drive Speed", teleLeft);
      SmartDashboard.putNumber("Right Drive Speed", teleRight);
    }
    @Override
    public boolean isFinished() {
      return false;   // this command just turns off shooter
    }
    private static double smartSquare(double input, double exponent) {
      double output = Math.pow(input, exponent);
      if (Math.signum(input) != Math.signum(output)) {
        output = output * -1;
      }
      return output;
    }
}
