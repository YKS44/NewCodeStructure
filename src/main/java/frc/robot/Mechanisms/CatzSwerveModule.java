package frc.robot.Mechanisms;

import javax.print.attribute.standard.MediaPrintableArea;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import frc.robot.CatzConstants;
import frc.robot.Utils.Conversions;

public class CatzSwerveModule extends Mechanism{
    private final CANSparkMax STEER_MOTOR;
    private final WPI_TalonFX DRIVE_MOTOR;


    private DutyCycleEncoder magEnc;
    private DigitalInput MagEncPWMInput;

    private PIDController pid;
    private final double kP = 0.01;
    private final double kI = 0.0;
    private final double kD = 0.0;

    private SupplyCurrentLimitConfiguration swerveModuleCurrentLimit;
    private final int     CURRENT_LIMIT_AMPS            = 55;
    private final int     CURRENT_LIMIT_TRIGGER_AMPS    = 55;
    private final double  CURRENT_LIMIT_TIMEOUT_SECONDS = 0.5;
    private final boolean ENABLE_CURRENT_LIMIT          = true;

    private final int     STEER_CURRENT_LIMIT_AMPS      = 30;
    
    private final double WHEEL_OFFSET;

    private PeriodicIO periodicIO = new PeriodicIO();

    public CatzSwerveModule(int driveMotorID, int steerMotorID, int encoderDIOChannel, double offset)
    {
        STEER_MOTOR = new CANSparkMax(steerMotorID, MotorType.kBrushless);
        DRIVE_MOTOR = new WPI_TalonFX(driveMotorID);

        STEER_MOTOR.restoreFactoryDefaults();
        DRIVE_MOTOR.configFactoryDefault();

        //Set current limit
        swerveModuleCurrentLimit = new SupplyCurrentLimitConfiguration(ENABLE_CURRENT_LIMIT, CURRENT_LIMIT_AMPS, CURRENT_LIMIT_TRIGGER_AMPS, CURRENT_LIMIT_TIMEOUT_SECONDS);

        STEER_MOTOR.setSmartCurrentLimit(STEER_CURRENT_LIMIT_AMPS);
        DRIVE_MOTOR.configSupplyCurrentLimit(swerveModuleCurrentLimit);

        STEER_MOTOR.setIdleMode(IdleMode.kCoast);
        DRIVE_MOTOR.setNeutralMode(NeutralMode.Brake);
        
        MagEncPWMInput = new DigitalInput(encoderDIOChannel);
        magEnc = new DutyCycleEncoder(MagEncPWMInput);

        pid = new PIDController(kP, kI, kD);

        WHEEL_OFFSET = offset;
        
    }

    /**
     * 
     * @param desiredState The desired state of the module
     * @param isOpenLoop   Open loop meaning: is the input of the motor dependent on its output? For auton, yes, for telop, no.
     * 
     */

    public void setDesiredState(SwerveModuleState desiredState, boolean isOpenLoop) //basically a function made solely for the purpose of following a trajectory. could be used for teleop though.
    {
        desiredState = SwerveModuleState.optimize(desiredState, Rotation2d.fromDegrees(periodicIO.currentAngleRotation)); //optimizes wheel rotation so that the furthest a wheel will ever rotate is 90 degrees.

        if(isOpenLoop)
        {
            periodicIO.driveControlMode = ControlMode.PercentOutput;
        }
        else
        {
            periodicIO.driveControlMode = ControlMode.Velocity;
        }

        periodicIO.driveCommand = Conversions.MPSToFalcon(desiredState.speedMetersPerSecond, CatzConstants.DriveConstants.DRVTRAIN_WHEEL_CIRCUMFERENCE, CatzConstants.DriveConstants.SDS_L2_GEAR_RATIO);


        // double targetAngle = (Math.abs(desiredState.speedMetersPerSecond) <= (CatzConstants.MAX_SPEED * 0.01)) ? getCurrentRotation().getDegrees() : desiredState.angle.getDegrees(); //Prevent rotating module if speed is less then 1%. Prevents Jittering.

        periodicIO.steerCommand = Math.max(-1.0, Math.min(1.0, - pid.calculate(periodicIO.currentAngleRotation, desiredState.angle.getDegrees())));

        // System.out.println(desiredState.angle.getDegrees() + " " + getCurrentRotation().getDegrees());
    }

    @Override
    public void readPeriodicInputs()
    {
        periodicIO.currentDrivePosition = DRIVE_MOTOR.getSelectedSensorPosition();
        periodicIO.currentAngleRotation = (magEnc.get() - WHEEL_OFFSET) * 360;
    }

    @Override
    public void writePeriodicOutputs()
    {
        DRIVE_MOTOR.set(periodicIO.driveControlMode, periodicIO.driveCommand);

        STEER_MOTOR.set(periodicIO.steerCommand);
    }

    private class PeriodicIO
    {
        public ControlMode driveControlMode; //ControlMode.Velocity is used for auton, ControlMode.PercentOutput is used for teleop.
        //Inputs
        public double currentDrivePosition;
        public double currentAngleRotation;

        //Outputs
        public double steerCommand;
        public double driveCommand;
    }
}
