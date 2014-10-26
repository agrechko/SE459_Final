package robot;

import robot.RobotController.State;

public class EmptyMeState implements RobotStates
{
	public void execute(RobotController robot)
	{
		robot.emptyMe = false;
		robot.setCurrentDirtCapacity(robot.maxDirtCapacity);
		robot.currentState = State.READY_TO_CLEAN.getValue();
	}
}
