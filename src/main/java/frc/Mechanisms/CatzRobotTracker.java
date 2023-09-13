package frc.robot.Mechanisms;

import frc.robot.Loops.Loop;

public class CatzRobotTracker extends Mechanism{

    private static CatzRobotTracker Instance = null;

    public static CatzRobotTracker getInstance()
    {
        if(Instance == null)
        {
            Instance = new CatzRobotTracker();
        }
        return Instance;
    }

    private CatzRobotTracker()
    {

    }

    @Override
    public Loop registerEnabledLoop() {
        return new Loop(){

            @Override
            public void onStart() {
                // TODO Auto-generated method stub
                
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
}
