// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class AutoBalance extends CommandBase {
  /** Creates a new AutoBalance. */
  public AutoBalance() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(new DriveTrain());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    DriveTrain.resetGyro();
  }
  https://prod.liveshare.vsengsaas.visualstudio.com/join?759116659EBC73218D8FFB871835870DDDD5
  
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Move Forward until gyro gets past a threshold
    while (/*gyrodegrees < degrees*/){
      //goforward
    }
  }

  // Go Forward
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
