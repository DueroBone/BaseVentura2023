package frc.robot.commands;

import java.util.function.BooleanSupplier;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.RobotContainer.*;
import frc.robot.subsystems.DriveTrain;

public class RunInTeleop extends CommandBase {
  //Variables init
  int counter1;
  boolean isBraked = true;

  public RunInTeleop() { //Declare major variables
  }

  @Override
  public void initialize() { //Declare rest of variables
    dynamicControllerXbox.updateController();
    dynamicControllerPlaystation.updateController();
    dynamicJoystick.updateController();
    counter1 = 0;
  }

  @Override
  public void execute() {
    BooleanSupplier LT = () -> (dynamicControllerXbox.object.getLeftTriggerAxis() > 0.5);
    BooleanSupplier RT = () -> (dynamicControllerXbox.object.getRightTriggerAxis() > 0.5);
    dynamicControllerXbox.LeftTrigger = new Button(LT);
    dynamicControllerXbox.RightTrigger = new Button(RT);
    counter1++;
    if (counter1%5 == 0) {
      if ((dynamicControllerXbox.object.getRightTriggerAxis() < 0.5) != isBraked) {
        System.out.println("Switched DriveTrain brake mode to " + isBraked);
        if (dynamicControllerXbox.object.getRightTriggerAxis() < 0.5) {
          DriveTrain.motorDriveLeft1.setIdleMode(IdleMode.kCoast); // Set coast mode
          DriveTrain.motorDriveLeft2.setIdleMode(IdleMode.kCoast);
          DriveTrain.motorDriveRight1.setIdleMode(IdleMode.kCoast);
          DriveTrain.motorDriveRight2.setIdleMode(IdleMode.kCoast);
        } else {
          DriveTrain.motorDriveLeft1.setIdleMode(IdleMode.kBrake); // Set brake mode
          DriveTrain.motorDriveLeft2.setIdleMode(IdleMode.kBrake);
          DriveTrain.motorDriveRight1.setIdleMode(IdleMode.kBrake);
          DriveTrain.motorDriveRight2.setIdleMode(IdleMode.kBrake);        
        } 
      }

      if (dynamicControllerXbox.object.getRightTriggerAxis() < 0.5) {isBraked = true;}
      else if (dynamicControllerPlaystation.object.getRightTriggerAxis() < 0.5) {isBraked = true;} 
      else {isBraked = false;}
    }
    //Update trigger buttons
    //Update brake mode && MUST BE SMART
  }

  @Override
  public void end(boolean interrupted) {//Reset everything
  }

  @Override
  public boolean isFinished() {
    return !RobotState.isTeleop(); 
  }
}
