package frc.robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import frc.robot.Loops.ILooper;
import frc.robot.Loops.Loop;
import frc.robot.Loops.Looper;
import frc.robot.Mechanisms.Mechanism;

public class MechanismManager implements ILooper{
    private static MechanismManager Instance;

    private List<Mechanism> allMechanisms;
    private List<Loop> loops = new ArrayList<>();

    private List<Mechanism> debugMechanisms;

    public static MechanismManager getInstance()
    {
        if(Instance == null)
        {
            Instance = new MechanismManager();
        }
        return Instance;
    }

    private MechanismManager(){}

    //This method MUST be called before any other method in robotInit
    public void setMechanisms(Mechanism... allMechanisms)
    {
        this.allMechanisms = Arrays.asList(allMechanisms);
    }

    public void setDebugMechanisms(Mechanism... debugMechanisms)
    {
        this.debugMechanisms = Arrays.asList(debugMechanisms);
    }

    public void smartDashboard()
    {
        allMechanisms.forEach(Mechanism::smartDashboard);
    }

    public void smartDashboard_DEBUG()
    {
        debugMechanisms.forEach(Mechanism::smartDashboard_DEBUG);
    }

    public void registerEnabledLoops(Looper enabledLooper)
    {
        allMechanisms.forEach(m -> loops.add(m.registerEnabledLoop()));
        enabledLooper.register(new EnabledLoop());
    }

    public void registerDisabledLoops(Looper disabledLooper)
    {
        disabledLooper.register(new DisabledLoop());
    }

    private class EnabledLoop implements Loop
    {
        @Override
        public void onStart() {
            loops.forEach(Loop::onStart);  
        }

        @Override
        public void onLoop() {
            allMechanisms.forEach(Mechanism::readPeriodicInputs);
            loops.forEach(Loop::onLoop); //onLoops are used for control logic loops or algorithmic calculations of the inputs.
            allMechanisms.forEach(Mechanism::writePeriodicOutputs);
        }

        @Override
        public void onStop() {
            loops.forEach(Loop::onStop);            
        }
    }

    private class DisabledLoop implements Loop {
        @Override
        public void onStart() {}

        @Override
        public void onLoop() {
            allMechanisms.forEach(Mechanism::readPeriodicInputs);
        }

        @Override
        public void onStop() {}

    }

    @Override
    public void register(Loop loop) {
        loops.add(loop);        
    }
}
