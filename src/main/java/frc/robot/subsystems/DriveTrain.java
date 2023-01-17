// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.DriveConstants;

import java.io.Console;

public class DriveTrain extends SubsystemBase {

  //// ----- Motor Controllers ----- /////
  // There are 4 separate motor controllers with 1 pwm channel per controller
  private static final CANSparkMax motorDriveLeft1 = new CANSparkMax(DriveConstants.leftDrive1Id, MotorType.kBrushless);
  private static final CANSparkMax motorDriveLeft2 = new CANSparkMax(DriveConstants.leftDrive2Id, MotorType.kBrushless);
  private static final CANSparkMax motorDriveRight1 = new CANSparkMax(DriveConstants.rightDrive1Id, MotorType.kBrushless);
  private static final CANSparkMax motorDriveRight2 = new CANSparkMax(DriveConstants.rightDrive2Id, MotorType.kBrushless);

  // define Speed Controller Groups and Differential Drive for use in drive train
  private static final MotorControllerGroup driveGroupLeft = new MotorControllerGroup(motorDriveLeft1, motorDriveLeft2);
  private static final MotorControllerGroup driveGroupRight = new MotorControllerGroup(motorDriveRight1, motorDriveRight2);
  private static final DifferentialDrive differentialDrive = new DifferentialDrive(driveGroupLeft, driveGroupRight);

  // Pneumatic solenoids for Hammer
  // private Solenoid solenoidHammerRaise; // The solenoids we use have two
  // channels, one for each output
  private Solenoid solenoidGearChange;
  // private Solenoid solenoidHammerDown;
  private Solenoid solenoidGearDef;
  private boolean highGear = false;

  // navX Gyro on RoboRio
  private AHRS m_Gyro;

  private static final boolean kSquareInputs = true;
  private static final boolean kSkipGyro = true;
  private static int counter = 0; // for limiting display

  /**
   * Creates a new DriveTrain.
   */
  public DriveTrain() {

    motorDriveLeft1.restoreFactoryDefaults(); // Clear any non default configuration/settings
    motorDriveLeft2.restoreFactoryDefaults();
    motorDriveRight1.restoreFactoryDefaults();
    motorDriveRight2.restoreFactoryDefaults();

    //SupplyCurrentLimitConfiguration supplyLimit = new SupplyCurrentLimitConfiguration(true, 30, 35, 1.0);
    int ampsMax = 40;
    motorDriveLeft1.setSmartCurrentLimit(ampsMax); //Set current limist
    motorDriveLeft2.setSmartCurrentLimit(ampsMax);
    motorDriveRight1.setSmartCurrentLimit(ampsMax);
    motorDriveRight2.setSmartCurrentLimit(ampsMax);

    // DifferentialDrive inverts right side by default, so no need to setInvert()
    // here
    // differentialDrive.setRightSideInverted(true);
    motorDriveLeft1.setInverted(true); // Invert 1 side of robot so will drive forward
    motorDriveLeft2.setInverted(true);

    motorDriveLeft1.setIdleMode(IdleMode.kCoast); // set neutral mode
    motorDriveLeft2.setIdleMode(IdleMode.kCoast);
    motorDriveRight1.setIdleMode(IdleMode.kCoast);
    motorDriveRight2.setIdleMode(IdleMode.kCoast);

    // driveStraightControl.setTolerance(0.02); // set tolerance around setpoint

    // Initialize the solenoids
    //solenoidHammerRaise = new Solenoid(0);
    solenoidGearChange = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    //solenoidHammerDown = new Solenoid(1);
    solenoidGearDef = new Solenoid(PneumaticsModuleType.CTREPCM, 1);

    if (kSkipGyro) {
      m_Gyro = null;

    } else {
      // navX-MXP Gyro instantiation
      try {
        // Instantiate Gyro - communicate w/navX-MXP via the MXP SPI Bus
        // Alternatively: I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB
        // See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for
        // details
        m_Gyro = new AHRS(SPI.Port.kMXP);

      } catch (RuntimeException ex) {
        DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
      }
      while (m_Gyro.isCalibrating()) {
        try {
          Thread.sleep(500);
        } catch (Exception e) {
          System.out.println(e);
        } // sleep in milliseconds
        System.out.println("**gyro isCalibrating . . .");
      }
      SmartDashboard.putBoolean("gyro connected", m_Gyro.isConnected());
      System.out.println("gyro connected " + m_Gyro.isConnected());
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  /**
   * Tank style driving for the DriveTrain.
   *
   * @param left  Speed in range [-1,1]
   * @param right Speed in range [-1,1]
   */
  public static void doTankDrive(double leftDrivePercent, double rightDrivePercent) {

    if (counter++ % 100 == 0) {
      System.out.println("**driveTrain power L/R: " + leftDrivePercent + " | " + rightDrivePercent);
    }
    // SquareInputs adjust inputs at low speeds so better control - note 1.0 * 1.0
    // is still 1
    // differentialDrive.tankDrive(leftDrivePercent, rightDrivePercent,
    // kSquareInputs); // send output to drive train
<<<<<<< HEAD
    System.out.println("Left: " + leftDrivePercent + " Right: " + rightDrivePercent);
    //motorDriveLeft1.set(leftDrivePercent);
    //motorDriveLeft2.set(leftDrivePercent);
    //motorDriveRight1.set(rightDrivePercent);
    //motorDriveRight2.set(rightDrivePercent);
=======
    //System.out.println("Left: " + leftDrivePercent + " Right: " + rightDrivePercent);
    motorDriveLeft1.set(leftDrivePercent);
    motorDriveLeft2.set(leftDrivePercent);
    motorDriveRight1.set(rightDrivePercent);
    motorDriveRight2.set(rightDrivePercent);
    /*
>>>>>>> 5488e27d113c8449bbbf09d4380c05bbbd8306f4
    driveGroupLeft.set(leftDrivePercent);
    driveGroupRight.set(rightDrivePercent);
  }

  public void doTankDriveDefault(double leftDrivePercent, double rightDrivePercent) {

    // if (counter++ % 100 == 0) { System.out.println("**default driveTrain power: "
    // + leftDrivePercent+"-"+rightDrivePercent); }
    // SquareInputs adjust inputs at low speeds so better control - note 1.0 * 1.0
    // is still 1
    differentialDrive.tankDrive(leftDrivePercent, rightDrivePercent, kSquareInputs); // send output to drive train
  }

  /**
   * Arcade style driving for the DriveTrain.
   *
   * @param speed    Speed in range [-1,1]
   * @param rotation Rotation in range [-1,1]
   */
  public void doArcadeDrive(double speed, double rotation) {
    // if (counter++ % 100 == 0) { System.out.println("**arcadeDrive power
    // speed/rotation: " + speed+"-"rotation); }
    // SquareInputs adjust inputs at low speeds so better control - note 1.0 * 1.0
    // is still 1
    differentialDrive.arcadeDrive(speed, rotation, kSquareInputs);
  }

  // http://pdocs.kauailabs.com/navx-mxp/guidance/terminology (for pitch, roll,
  // yaw, IMU terminology)
  public double getHeadingAngle() {
    return m_Gyro.getAngle(); // get current heading
    // return Math.IEEEremainder(m_Gyro.getAngle(), 360.0);
    //return 0.0;
  }

  public double getYaw() {
    return m_Gyro.getYaw(); // get rotation around Z axis for current heading
    //return 0.0;
  }

  public void resetGyro() {
    m_Gyro.reset();
    // "Zero" yaw (whatever direction sensor is pointing now becomes new "Zero"
    // degrees
    m_Gyro.zeroYaw();
  }

  // public void resetEncoder() {
  public void resetEncoders() {
    //m_leftEncoder.reset();
    //m_rightEncoder.reset();
  }

  public int getLeftEncoderCount() {
    //return m_leftEncoder.get();
    return 0;
  }

  public int getRightEncoderCount() {
    //return m_rightEncoder.get();
    return 0;
  }

  // get current distance since last encoder reset
  public double getLeftDistance() {
    //return m_leftEncoder.getDistance();
    return 0.0;
  }

  public double getLeftDistanceInch() {
  //  return Math.PI * DriveConstants.WHEEL_DIAMETER * (getLeftEncoderCount() /
  //   DriveConstants.PULSES_PER_REVOLUTION);
    return 0.0;
  }

  public double getRightDistanceInch() {
  //  return Math.PI * DriveConstants.WHEEL_DIAMETER * (getRightEncoderCount() /
  //   DriveConstants.PULSES_PER_REVOLUTION);
    return 0.0;
  }

  public double getAveDistanceInch() {
    //return (getLeftDistanceInch() + getRightDistanceInch()) / 2.0;
    return 0.0;
  }

  
  // Function to set the solenoids
public void doHighGear(final boolean fast) {

  highGear = fast;
  // Make sure the solenoids are set to opposite values!
  //solenoidHammerRaise.set(!hammerExtended); // solenoid controls output that pulls piston in, so set it to ! hammerExtended
  solenoidGearChange.set(!highGear);
  System.out.println("Gear shifter in Low Torque Mode");
  //solenoidHammerDown.set(hammerExtended); // solenoid controls output that pushes piston out, so set it to hammerExtended
  solenoidGearDef.set(highGear);
  System.out.println("Gear shifter in High Torque Mode");
}


  //public double clampValue(double value, double min, double max) {
  //  return Math.max(min, Math.min(value, max)); // make sure within range
  //}

  public void stop() {
    System.out.println("in drivetrain stop");
    doTankDrive(0.0, 0.0);
  }

}