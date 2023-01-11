package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.PneumaticsModuleType;


public class Piston {
    private static DoubleSolenoid piston;
    //private static Solenoid piston0;
    //private static Solenoid piston1;
    Compressor compressor;
    public Piston() {
    piston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
  }

    public static Command contract(final boolean contracted) {
        System.out.println("Piston extended: " + contracted);
        //piston0.set(contracted);
        //piston1.set(!contracted);
    
        if (contracted) {    // if true then shift to low gear
          piston.set(DoubleSolenoid.Value.kForward); // solenoid controls output that pulls piston in or out
        } else {
          piston.set(DoubleSolenoid.Value.kReverse);
        }
        return null;
    }
    public static void pistonToggle(){
      //piston.toggle();
    }
}
