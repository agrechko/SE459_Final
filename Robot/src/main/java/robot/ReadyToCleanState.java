package robot;

import robot.RobotController.State;

public class ReadyToCleanState implements RobotStates {
	/**
	 * Checks the preconditions for successful clean cycle. If not met then the
	 * state becomes stop
	 */
	public void execute(RobotController robot) {
		// if dirt capacity is <= 0 it means we do not have free space to put
		// dirt
		// if current power is less than 2 it means that we do not have enough
		// power to go half way and back
		// if sensors are null we cannot see the environment
		// if dirt controller is null we cannot clean dirt
		if (robot.getCurrentDirtCapacity() <= 0 || robot.currentPower <= 1 || robot.sensors == null) {
			robot.currentState = State.STOP.getValue();
		} else {
			robot.currentState = State.EXPLORING.getValue();
		}
	}
}
