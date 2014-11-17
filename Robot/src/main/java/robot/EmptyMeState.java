package robot;

import robot.RobotController.State;

public class EmptyMeState implements RobotStates {
    public final void execute(final RobotController robot) {
        robot.emptyMe = false;
        robot.setCurrentDirtCapacity(robot.maxDirtCapacity);
        robot.currentState = State.READY_TO_CLEAN.getValue();
    }
}
