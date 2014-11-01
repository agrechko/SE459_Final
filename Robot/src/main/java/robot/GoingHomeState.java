package robot;

import robot.RobotController.State;

public class GoingHomeState implements RobotStates
{
	public void execute(RobotController robot) 
	{
		if(robot.route.size() != 0)
		{
			//1: x pos, 2: x neg, 3: y pos, 4: y neg
			int prevStep = robot.route.pop();
			switch(prevStep){
			case 0:
				robot.currentX -= 1;
				break;
			case 1:
				robot.currentX += 1;
				break;
			case 2:
				robot.currentY -= 1;
				break;
			case 3:
				robot.currentY += 1;
				break;
			}
			int floorType = robot.sensors.getSurface(robot.currentX, robot.currentY);
			robot.currentPower -= robot.getPowerConsumption(floorType);
		}
		System.out.println("going home! currently at x: " + robot.currentX + " y: " + robot.currentY);
		if(robot.sensors.getCell(robot.currentX, robot.currentY).isChargingStation())
		{
			robot.currentState = State.CHARGING.getValue();
		}
	}
}
