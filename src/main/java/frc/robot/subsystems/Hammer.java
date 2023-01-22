package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.PneumaticsModuleType;


public class Hammer {
    public static DoubleSolenoid hammer;
    public Hammer() {
    hammer = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 3, 4);
    hammer.set(DoubleSolenoid.Value.kReverse);
    //hammer.close();
    }
    public static Command contract(final boolean contracted) {
        System.out.println("Hammer extended: " + contracted);
        if (contracted) {    // if true then shift to low gear
          hammer.set(DoubleSolenoid.Value.kForward); // solenoid controls output that pulls piston in or out
        } else {
          hammer.set(DoubleSolenoid.Value.kReverse);
        }
        return null;
    }
    public static Command hammerToggle(){
      hammer.toggle();
      return null;
    }
}
