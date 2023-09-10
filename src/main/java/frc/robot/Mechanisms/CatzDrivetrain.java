package frc.robot.Mechanisms;

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

    private CatzDrivetrain()
    {

    }

    @Override
    public Loop sendControlLoop(){
        return new Loop() {

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