package frc.robot.Mechanisms;

import frc.robot.Loops.ILooper;
//CREDIT: Team 1678
import frc.robot.Loops.Loop;

/**
 * The Subsystem abstract class, which serves as a basic framework for all robot subsystems. Each subsystem outputs
 * commands to SmartDashboard, has a stop routine (for after each match), and a routine to zero all sensors, which helps
 * with calibration.
 * <p>
 * All Subsystems only have one instance (after all, one robot does not have two drivetrains), and functions get the
 * instance of the drivetrain and act accordingly. Subsystems are also a state machine with a desired state and actual
 * state; the robot code will try to match the two states with actions. Each Subsystem also is responsible for
 * instantializing all member components at the start of the match.
 */

public abstract class Mechanism {

    protected boolean TURN_ON_DEBUG = false;

    // Optional design pattern for caching periodic reads to avoid hammering the HAL/CAN.
    public void readPeriodicInputs(){} 

    // Optional design pattern for caching periodic writes to avoid hammering the HAL/CAN.
    public void writePeriodicOutputs(){}

    public void zeroSensors(){}

    public void smartDashboard(){}

    public void smartDashboard_DEBUG(){}

    public Loop sendControlLoop(){return null;}
}
