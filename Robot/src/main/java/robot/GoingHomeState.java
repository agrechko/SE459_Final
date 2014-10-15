package robot;

import robot.RobotController.State;

public class GoingHomeState implements RobotStates
{
	public void execute(RobotController robot) 
	{
		//1: x pos, 2: x neg, 3: y pos, 4: y neg
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
		
		robot.currentPower -= 1;
		System.out.println("going home! currently at x: " + robot.currentX + " y: " + robot.currentY);
		if(robot.currentX == robot.sensors.getChargingStationLocation().getCellX() &&
				robot.currentY == robot.sensors.getChargingStationLocation().getCellY())
		{
			robot.currentState = State.CHARGING.getValue();
		}
	}
}
