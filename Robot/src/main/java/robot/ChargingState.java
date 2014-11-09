package robot;

import robot.RobotController.State;

public class ChargingState implements RobotStates {
	public void execute(RobotController robot) {
		// user assistance is needed to continue
		Boolean userAssistance = false;
		robot.currentPower = robot.maxPower;
		if (robot.getCurrentDirtCapacity() == 0) {
			//wait for user input to empty the dirt cargo bay or stop clean cycle
			userAssistance = true;
			robot.emptyMe = true;

			System.out.println("----- Empty Me -----");

			robot.printAvailableCommands();

			robot.currentState = State.WAITING_FOR_COMMAND.getValue();
		}

		// all cells have been visited in this clean cycle so stop
		if (robot.sensors.isAllClean()) {
			System.out.println("The floor is so clean you can eat off it. Stopping...");
			robot.currentState = State.STOP.getValue();
		} else {
			if (!userAssistance) {
				robot.currentState = State.READY_TO_CLEAN.getValue();
			}
		}
	}
}
