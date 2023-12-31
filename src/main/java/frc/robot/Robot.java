// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.Autonomous.AutonActionExecutor;
import frc.Autonomous.AutonRoutineSelector;
import frc.Loops.Looper;
import frc.Mechanisms.CatzDrivetrain;
import frc.Mechanisms.CatzRobotTracker;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    //Mechanisms
    private final MechanismManager mechanismManager = MechanismManager.getInstance();

    private final CatzDrivetrain drivetrain = CatzDrivetrain.getInstance();
    private final CatzRobotTracker robotTracker = CatzRobotTracker.getInstance();

    //Autonomous
    public static AutonActionExecutor autonExecutor = AutonActionExecutor.getInstance();
    public static AutonRoutineSelector autonRoutineSelector = AutonRoutineSelector.getInstance();

    //Loops
    private final Looper enabledLooper = new Looper();
    private final Looper disabledLooper = new Looper();

    @Override
    public void robotInit()
    {
        mechanismManager.setMechanisms(
            drivetrain,
            robotTracker
        );
        
        //Put mechanisms that needs to update debug smartdashboard outputs here
        mechanismManager.setDebugMechanisms(
            drivetrain,
            robotTracker
        );

        mechanismManager.registerEnabledLoops(enabledLooper);
        mechanismManager.registerDisabledLoops(disabledLooper);
    }

    @Override
    public void robotPeriodic()
    {
        mechanismManager.smartDashboard();
        mechanismManager.smartDashboard_DEBUG();
    }

    @Override
    public void autonomousInit()
    {
        disabledLooper.stop();
        enabledLooper.start();
    }

    @Override
    public void teleopInit()
    {
        disabledLooper.stop();
        enabledLooper.start();
    }

    @Override
    public void disabledInit()
    {
        enabledLooper.stop();
        disabledLooper.start();
    }
}
