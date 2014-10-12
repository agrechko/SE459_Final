package robot;

import robot.RobotController.State;

public class ExploringState implements RobotStates
{
	@Override
	public void execute(RobotController robot) 
	{
		if(robot.currentPower <= (robot.maxPower/2))
		{
			robot.currentState = State.GOING_HOME.getValue();
		}
		else
		{
			//0: unknown 1: open, 2: obstical, 4: stairs
			int[] paths = robot.sensors.getPaths(robot.currentX, robot.currentY);
			//1: x pos, 2: x neg, 3: y pos, 4: y neg
			int cameFrom = 0;
			if(robot.route.size() != 0){
				cameFrom = robot.route.peek();
			}
			
			if(paths[0] == 1 && (cameFrom == 0 || cameFrom != 1))//can go x pos and did not come from x pos
			{
				robot.currentX += 1;
				robot.route.push(1);
			}
			else if(paths[1] == 1 && (cameFrom == 0 || cameFrom != 2))//can go x neg and did not come from x neg
			{
				robot.currentX -= 1;
				robot.route.push(2);
			}
			else if(paths[2] == 1 && (cameFrom == 0 || cameFrom != 3))//can go y pos and did not come from y pos
			{
				robot.currentY += 1;
				robot.route.push(3);
			}
			else if(paths[3] == 1 && (cameFrom == 0 || cameFrom != 4))//can go y neg and did not came from y neg
			{
				robot.currentY -= 1;
				robot.route.push(4);
			}
			else //need to go back no way out
			{
				goBackOneStep(robot);
			}
			
			System.out.println("Current Location x: " + robot.currentX +" y: " + robot.currentY);
			robot.currentPower -= 1;
			
			if(!robot.sensors.isClean(robot.currentX, robot.currentY))
			{
				System.out.println("Entering cleaning mode");
				robot.currentState = State.CLEANING.getValue();
			}
		}
	}
	
	private void goBackOneStep(RobotController robot)
	{
		int prevStep = robot.route.pop();
		switch(prevStep){
		case 1:
			robot.currentX -= 1;
			break;
		case 2:
			robot.currentX += 1;
			break;
		case 3:
			robot.currentY -= 1;
			break;
		case 4:
			robot.currentY += 1;
			break;
		}
	}
}
