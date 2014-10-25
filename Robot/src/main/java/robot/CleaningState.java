package robot;

import robot.RobotController.State;
import sensors.SensorsController;

public class CleaningState implements RobotStates
{
	int surface; 
	
	public void execute(RobotController robot) 
	{
		if(robot.currentDirtCapacity == 0)
		{
			robot.currentState = State.GOING_HOME.getValue();
		}
		else if(robot.currentPower <= (robot.maxPower/2))
		{
			robot.currentState = State.GOING_HOME.getValue();
		}
		else
		{
			if(robot.sensors.isClean(robot.currentX, robot.currentY) == true)
			{
				robot.currentState = State.EXPLORING.getValue();
			}
			else
			{
				surface = robot.sensors.getSurface(robot.currentX, robot.currentY);
				robot.sensors.clean(robot.currentX, robot.currentY);
				robot.currentDirtCapacity--; 
				robot.currentPower = robot.currentPower - surface;

			}
		}
	}
}
