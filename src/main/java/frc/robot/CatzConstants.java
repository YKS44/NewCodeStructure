package frc.robot;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.DigitalInput;

public class CatzConstants {
    public static final class SwerveModuleConstants
    { //finish this later
        public static final double STEER_MOTORkP = 0.01;
        public static final double STEER_MOTORkI = 0.0;
        public static final double STEER_MOTORkD = 0.0;
        
        public static SupplyCurrentLimitConfiguration swerveModuleCurrentLimit;
        public static final int     CURRENT_LIMIT_AMPS            = 55;
        public static final int     CURRENT_LIMIT_TRIGGER_AMPS    = 55;
        public static final double  CURRENT_LIMIT_TIMEOUT_SECONDS = 0.5;
        public static final boolean ENABLE_CURRENT_LIMIT          = true;
        public static final int     STEER_CURRENT_LIMIT_AMPS      = 30;

        public static DigitalInput MagEncPWMInput;
    }

    public static final class DriveConstants
    {
        private static final double MODULE_DISTANCE_FROM_CENTER = 0.2984;

        private static final Translation2d SWERVE_LEFT_FRONT_LOCATION  = new Translation2d(-MODULE_DISTANCE_FROM_CENTER,MODULE_DISTANCE_FROM_CENTER);
        private static final Translation2d SWERVE_LEFT_BACK_LOCATION   = new Translation2d(-MODULE_DISTANCE_FROM_CENTER, -MODULE_DISTANCE_FROM_CENTER);
        private static final Translation2d SWERVE_RIGHT_FRONT_LOCATION = new Translation2d(MODULE_DISTANCE_FROM_CENTER, MODULE_DISTANCE_FROM_CENTER);
        private static final Translation2d SWERVE_RIGHT_BACK_LOCATION  = new Translation2d(MODULE_DISTANCE_FROM_CENTER, -MODULE_DISTANCE_FROM_CENTER);

        public static final SwerveDriveKinematics swerveDriveKinematics = new SwerveDriveKinematics(
            SWERVE_LEFT_FRONT_LOCATION,
            SWERVE_LEFT_BACK_LOCATION,
            SWERVE_RIGHT_FRONT_LOCATION,
            SWERVE_RIGHT_BACK_LOCATION
        );

        public static final double MAX_SPEED = 4.0;

        public static final double SDS_L1_GEAR_RATIO = 8.14;       //SDS mk4i L1 ratio
        public static final double SDS_L2_GEAR_RATIO = 6.75;       //SDS mk4i L2 ratio
    
        public static final double DRVTRAIN_WHEEL_DIAMETER             = 4.0;
        public static final double DRVTRAIN_WHEEL_CIRCUMFERENCE        = (Math.PI * DRVTRAIN_WHEEL_DIAMETER);


        public final double POS_ENC_CNTS_HIGH_EXTEND_THRESHOLD_ELEVATOR = 73000.0;

        //uses a trapezoidal velocity/time graph enforced with a PID loop
        private static ProfiledPIDController autoTurnPIDController
                = new ProfiledPIDController(5, 0, 0, new TrapezoidProfile.Constraints(8, 8));

        static{
            autoTurnPIDController.enableContinuousInput(-Math.PI, Math.PI); //offset clamped between these two values
            autoTurnPIDController.setTolerance(Math.toRadians(0.05)); //tolerable error
        }

        // calculates target chassis motion when given current position and desired trajectory
        public static final HolonomicDriveController holonomicDriveController = new HolonomicDriveController(
            new PIDController(0.35, 0, 0), // PID values for x offset
            new PIDController(0.35, 0, 0), // PID values for y offset
            autoTurnPIDController // PID values for orientation offset
        );
 }
}
