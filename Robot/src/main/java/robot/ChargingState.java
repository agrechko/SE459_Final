package robot;

import robot.RobotController.State;

public class ChargingState implements RobotStates
{
	public void execute(RobotController robot)
	{
		System.out.println("Charging is not implemented: changing state to STOP");
		robot.currentState = State.STOP.getValue();
	}
}
