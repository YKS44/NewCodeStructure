package frc.robot.Mechanisms;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.Loops.Loop;

public class CatzDrivetrain extends Mechanism{

    private static CatzDrivetrain Instance;

    public static CatzDrivetrain getInstance()
    {
        if(Instance == null)
        {
            Instance = new CatzDrivetrain();
        }
        return Instance;
    }

    private static CatzSwerveModule[] swerveModules = new CatzSwerveModule[4];


    private final int LT_FRNT_DRIVE_ID = 1;
    private final int LT_BACK_DRIVE_ID = 3;
    private final int RT_BACK_DRIVE_ID = 5;
    private final int RT_FRNT_DRIVE_ID = 7;
    
    private final int LT_FRNT_STEER_ID = 2;
    private final int LT_BACK_STEER_ID = 4;
    private final int RT_BACK_STEER_ID = 6;
    private final int RT_FRNT_STEER_ID = 8;

    private final int LT_FRNT_ENC_PORT = 9;
    private final int LT_BACK_ENC_PORT = 6;
    private final int RT_BACK_ENC_PORT = 7;
    private final int RT_FRNT_ENC_PORT = 8;

    private double LT_FRNT_OFFSET = 0.0091;  //-0.0013; //MC ID 2
    private double LT_BACK_OFFSET = 0.0466; //0.0498; //MC ID 4
    private double RT_BACK_OFFSET = 0.2567; //0.2533; //MC ID 6
    private double RT_FRNT_OFFSET = 0.0281; //0.0222; //MC ID 8

    private PeriodicIO periodicIO = new PeriodicIO();

    private final AHRS navX;
    
    private CatzDrivetrain()
    {
        final CatzSwerveModule LT_FRNT_MODULE = new CatzSwerveModule(LT_FRNT_DRIVE_ID, LT_FRNT_STEER_ID, LT_FRNT_ENC_PORT, LT_FRNT_OFFSET);
        final CatzSwerveModule LT_BACK_MODULE = new CatzSwerveModule(LT_BACK_DRIVE_ID, LT_BACK_STEER_ID, LT_BACK_ENC_PORT, LT_BACK_OFFSET);
        final CatzSwerveModule RT_FRNT_MODULE = new CatzSwerveModule(RT_FRNT_DRIVE_ID, RT_FRNT_STEER_ID, RT_FRNT_ENC_PORT, RT_FRNT_OFFSET);
        final CatzSwerveModule RT_BACK_MODULE = new CatzSwerveModule(RT_BACK_DRIVE_ID, RT_BACK_STEER_ID, RT_BACK_ENC_PORT, RT_BACK_OFFSET);

        swerveModules[0] = LT_FRNT_MODULE;
        swerveModules[1] = LT_BACK_MODULE;
        swerveModules[2] = RT_FRNT_MODULE;
        swerveModules[3] = RT_BACK_MODULE;

        navX = new AHRS();
        navX.setAngleAdjustment(-navX.getYaw() + 180);
    }

    @Override
    public void readPeriodicInputs() 
    {
        for(CatzSwerveModule swerveModule : swerveModules)
        {
            swerveModule.readPeriodicInputs();
        }
    }

    @Override
    public void writePeriodicOutputs()
    {
        for(int i = 0; i < swerveModules.length; i++)
        {
            
        }

        for(CatzSwerveModule swerveModule : swerveModules)
        {
            swerveModule.writePeriodicOutputs();
        }
    }

    @Override
    public Loop registerEnabledLoop()
    {
        return new Loop() {

            @Override
            public void onStart() {

            }

            @Override
            public void onLoop() {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void onStop() {
                // TODO Auto-generated method stub
                
            }

        };
    }

    private class PeriodicIO
    {
        //Inputs
        public ChassisSpeeds des_chassis_speeds = new ChassisSpeeds();
        public double orientation = navX.getAngle();

        //Outputs
        public SwerveModuleState[] des_module_states = new SwerveModuleState[4];
    }
}