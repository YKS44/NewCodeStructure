// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Loops.Looper;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    private final MechanismManager mechanismManager = MechanismManager.getInstance();

    private final Looper enabledLooper = new Looper();
    private final Looper disabledLooper = new Looper();

    @Override
    public void robotInit()
    {
        mechanismManager.setMechanisms(null);

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
