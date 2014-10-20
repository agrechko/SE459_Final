package robot;

import robot.RobotController.State;
import robot.DirtController;
import sensors.SensorsController;

public class CleaningState implements RobotStates
{
	DirtController dirtController = new DirtController();
	int surface; 
	
	public void execute(RobotController robot) 
	{
		if(robot.currentDirtCapacity <= (robot.maxDirtCapacity/2))
		{
			robot.currentState = State.GOING_HOME.getValue();
		}
		else
		{
			if(robot.sensors.isVisited(robot.currentX, robot.currentY) == false)
			{
				robot.currentState = State.EXPLORING.getValue();
			}
			else
			{
				if(robot.sensors.isClean(robot.currentX, robot.currentY) == true)
				{
					robot.currentState = State.STOP.getValue();
				}
				else
				{
					surface = robot.sensors.getSurface(robot.currentX, robot.currentY);
					robot.dirtController.clean(surface);
				}
			}
		}
	}
	
}
