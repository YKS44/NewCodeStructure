# NewCodeStructure

## Introduction

This is a new code structure run in a single-threaded environment. Using this structure will allow us to write more unified and structured code regardless of who wrote it. AdvantageKit can also be implemented in this code.


## Code Overview 
### Loop.java and Looper.java
Looper.java is what runs all the loops in the robot. It uses the wpilib's built-in Notifier class. This is because Notifiers schedule and trigger callbacks at precise intervals, which allows for more accurate timing and synchronization with other robot operations.

There are two Loopers EnabledLoops and DisabledLoops.
EnabledLoops are run while the robot is on and execute all periodic methods in the mechanisms.
DisabledLoops only runs the readPeriodicInput() while the robot is disabled.

### Mechanism.java
Mechanism.java will serve as the parent function for all mechanisms in the robot. 

<img width="680" alt="Screenshot 2023-09-15 at 8 56 14 PM" src="https://github.com/YKS44/NewCodeStructure/assets/79933420/a6d48728-dfdd-416f-a248-dde78c3b8ee4">

readPeriodicInputs() and writePeriodicOutputs() will be called periodically in MechanismManager.
readPeriodicInputs() should be used to read input values (e.g. encoder values, gyro angle).

writePeriodicOutputs() will be used to write the outputs, such as moving the motors.

Each mechanism can also override the registerEnabledLoop and return its Loop to be called in MechanismManager. This Loop contains what to do when the robot starts, while the robot is running, and after the robot disables.
The onLoop() of the Loop interface can be used to do any other tasks that require periodic updates, like the control logic.

The basic flow of mechanism is:
  1. Read inputs
  2. Do some calculations
  3. Write the output.

All input and output values must be stored inside an instance of the PeriodicIO class (a private nested class within the mechanism class).

<img width="399" alt="Screenshot 2023-09-15 at 10 26 19 PM" src="https://github.com/YKS44/NewCodeStructure/assets/79933420/36610211-50c0-4445-85ed-09c92c81b3d9">

<img width="781" alt="Screenshot 2023-09-15 at 10 26 37 PM" src="https://github.com/YKS44/NewCodeStructure/assets/79933420/f1908a17-328f-4ce4-b8ad-5df71ae9bdd3">

<img width="568" alt="Screenshot 2023-09-15 at 10 26 52 PM" src="https://github.com/YKS44/NewCodeStructure/assets/79933420/19ad0b4a-3312-454c-b1fc-e23ec50641d6">

### MechanismManager.java

The MechanismManager is used to reset. stop, and update all mechanisms at once. In the class definition of Robot.java, all mechanisms should be instantiated using their own getInstance() method.
In robotInit(), all the mechanisms should be added to the list of mechanisms in MechanismManager.


<img width="623" alt="Screenshot 2023-09-15 at 8 52 31 PM" src="https://github.com/YKS44/NewCodeStructure/assets/79933420/9dd003a4-8e04-4842-a800-19a6fec3b3c7">

The setMechanisms() method MUST be called in robotInit() before any other code.
All mechanisms that want to have their debug values printed to the SmartDashboard can do so by adding it to the debugMechanisms list in MechanismManager.


