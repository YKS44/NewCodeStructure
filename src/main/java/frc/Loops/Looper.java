package frc.robot.Loops;

import edu.wpi.first.wpilibj.Notifier;

public class Looper implements ILooper{
    private final double loopPeriod;

    private boolean running;

    //used as a thread. Notifiers schedule and trigger callbacks at precise intervals. This allows for more accurate timing and synchronization with other robot operations.
    private final Notifier notifier; 

    //All loopers should have one loop that has a specific type of loop that they want to run. (ex enabled and disabled loops)
    private Loop loop;

    private final Runnable runnable = () -> {
        while(running)
        {
            loop.onLoop();
        }
    };

    public Looper(double loopPeriod)
    {
        notifier = new Notifier(runnable);
        this.loopPeriod = loopPeriod;
        running = false;
    }

    public Looper()
    {
        this(0.02);
    }

    public void start()
    {
        if(!running)
        {
            System.out.println("Starting loops");

            loop.onStart();

            running = true;

            notifier.startPeriodic(loopPeriod);
        }
    }

    public void stop()
    {
        if(running)
        {
            System.out.println("Stopping loops");
            notifier.stop();

            loop.onStop();

            running = false;
        }
    }

    @Override
    public void register(Loop loop)
    {
        this.loop = loop;
    }
}
