package robot;

import robot.RobotController.State;

public class CleaningState implements RobotStates {
	int surface; 
	
	public void execute(RobotController robot) {
		if(robot.getCurrentDirtCapacity() == 0) {
			robot.currentState = State.GOING_HOME.getValue();
		}
		else if(robot.currentPower <= (robot.maxPower/2)) {
			robot.currentState = State.GOING_HOME.getValue();
		}
		else
		{
			if(robot.sensors.isClean(robot.currentX, robot.currentY) == true) {
				robot.currentState = State.EXPLORING.getValue();
			}
			else if(robot.currentPower - robot.getPowerConsumption(robot.sensors.getSurface(robot.currentX, robot.currentY)) <= (robot.maxPower/2)) {
				robot.currentState = State.GOING_HOME.getValue();
			}
			else {
				System.out.println("before "+robot.sensors.getCell(robot.currentX, robot.currentY).getDirt());
				surface = robot.sensors.getSurface(robot.currentX, robot.currentY);
				robot.sensors.clean(robot.currentX, robot.currentY);
				robot.setCurrentDirtCapacity(robot.getCurrentDirtCapacity() - 1); 
				robot.currentPower = robot.currentPower - robot.getPowerConsumption(surface);
				System.out.println("After "+robot.sensors.getCell(robot.currentX, robot.currentY).getDirt());
				System.out.println("current dirt capacity "+ robot.getCurrentDirtCapacity());
			}
		}
	}
}
