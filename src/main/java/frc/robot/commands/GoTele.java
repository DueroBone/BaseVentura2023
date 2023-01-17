package frc.robot.commands;

import java.text.MessageFormat;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;
import frc.robot.commands.DriveCommand;

public class GoTele extends CommandBase {
    @Override
    public void initialize() {
      System.out.println(MessageFormat.format("**Started {0} ", this.getName()));
    }
    double teleLeft = 0;
    double teleRight = 0;
    private final DriveTrain drivetrain;

    public GoTele () {
        this.drivetrain = RobotContainer.m_driveTrain;    // get driveTrain object from RobotContainer
      
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(this.drivetrain); 
      }

    @Override
    public void execute() {
      DriveCommand dcObj = new DriveCommand(() -> teleLeft, () -> teleRight);
      double deadzone = 0.1;
      boolean usingCon0 = Math.abs(RobotContainer.controller0.getLeftY()) < deadzone || Math.abs(RobotContainer.controller0.getRightY()) < deadzone;
      double teleLeft = 0;
      double teleRight = 0;
      if (RobotContainer.controller0.isConnected() && usingCon0) {
        //Using two controllers
        teleLeft = RobotContainer.controller0.getLeftY();
        teleRight = RobotContainer.controller0.getRightY();
      }

      double a = 1 - deadzone;
      a = 1 / a;
      teleLeft = teleLeft - deadzone;
      teleLeft = teleLeft * a;
      teleRight = teleRight - deadzone;
      teleRight = teleRight * a;

      dcObj.execute();
      SmartDashboard.putNumber("Left Drive Speed", teleLeft);
      SmartDashboard.putNumber("Right Drive Speed", teleRight);
    }
    @Override
    public boolean isFinished() {
      return false;   // this command just turns off shooter
    }
}
