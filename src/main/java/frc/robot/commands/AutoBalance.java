// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class AutoBalance extends CommandBase {
  /** Creates a new AutoBalance. */
  DriveTrain m_driveTrain;
  int balancingStage;
  int counter1;

  public AutoBalance() {
    // Use addRequirements() here to declare subsystem dependencies.
    // m_driveTrain = new DriveTrain();
    // addRequirements(m_driveTrain);
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
      if (balancingStage == 0) {
        while (DriveTrain.m_Gyro.getYaw() > 0) {
          System.out.println("FORWARDS");
        }
        balancingStage = 1;
      } else if (balancingStage == 1) {
        while (DriveTrain.m_Gyro.getYaw() != 0) {
          System.out.println("BACKWARDS");
        }
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
    return false;
  }
}
