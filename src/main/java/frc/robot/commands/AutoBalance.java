// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class AutoBalance extends CommandBase {
  /** Creates a new AutoBalance. */
  DriveTrain m_driveTrain;
  int balancingStage;
  int counter1;
  double speed = 0;

  public AutoBalance(double speedIn) {
    // Use addRequirements() here to declare subsystem dependencies.
    // m_driveTrain = new DriveTrain();
    // addRequirements(m_driveTrain);
    this.speed = speedIn;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    DriveTrain.resetGyro();
    balancingStage = 0;
    counter1 = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Move Forward until gyro gets past a threshold
    if (counter1 % 10 == 0) {
      DriverStation.reportError("Gyro at " + DriveTrain.m_Gyro.getPitch(), false);
      if (balancingStage == 0) {
        DriverStation.reportWarning("Started balance " + balancingStage, false);
        while (DriveTrain.m_Gyro.getPitch() < 2) {
          System.out.println("FORWARDS");
          DriveTrain.doTankDrive(speed, speed);
        }
        balancingStage = 1;
      } else if (balancingStage == 1) {
        DriveTrain.motorDriveLeft1.setIdleMode(IdleMode.kBrake); // Set brake mode
        DriveTrain.motorDriveLeft2.setIdleMode(IdleMode.kBrake);
        DriveTrain.motorDriveRight1.setIdleMode(IdleMode.kBrake);
        DriveTrain.motorDriveRight2.setIdleMode(IdleMode.kBrake);
        DriverStation.reportWarning("Next stage balance " + balancingStage, false);
        while (Math.abs(DriveTrain.m_Gyro.getPitch()) > 1) {
          System.out.println("BACKWARDS");
          DriveTrain.doTankDrive(-speed / 4, -speed / 4);
        }

        balancingStage = 2;
      } else if (balancingStage == 2) {
        while (Math.abs(DriveTrain.m_Gyro.getPitch()) > 1) {
          System.out.println("SECOND FORWARDS");
          DriveTrain.doTankDrive(speed / 4, speed / 4);
        }
        balancingStage = 3;
      } else if (balancingStage == 3) {
        DriverStation.reportWarning("Finished balance " + balancingStage, false);
      }
    }
  }

  // Go Forward
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (balancingStage == 3);
  }
}
