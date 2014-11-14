package robot;

import java.util.logging.Level;
import java.util.logging.Logger;

import robot.RobotController.State;

public class CleaningState implements RobotStates {
    int surface;
    Logger logger = Logger.getLogger("main");

    public final void execute(final RobotController robot) {
        if (robot.getCurrentDirtCapacity() == 0) {
            robot.currentState = State.GOING_HOME.getValue();
        } else if (robot.currentPower <= (robot.maxPower / 2)) {
            robot.currentState = State.GOING_HOME.getValue();
        } else {
            if (robot.sensors.isClean(robot.currentX, robot.currentY)) {
                robot.currentState = State.EXPLORING.getValue();
            } else if (robot.currentPower
                    - robot.getPowerConsumption(robot.sensors.getSurface(
                            robot.currentX, robot.currentY)) <= (robot.maxPower / 2)) {
                robot.currentState = State.GOING_HOME.getValue();
            } else {
                logger.log(
                        Level.FINE,
                        "before "
                                + robot.sensors.getCell(robot.currentX,
                                        robot.currentY).getDirt());
                surface = robot.sensors.getSurface(robot.currentX,
                        robot.currentY);
                robot.sensors.clean(robot.currentX, robot.currentY);
                robot.setCurrentDirtCapacity(robot.getCurrentDirtCapacity() - 1);
                robot.currentPower = robot.currentPower
                        - robot.getPowerConsumption(surface);
                logger.log(
                        Level.FINE,
                        "After "
                                + robot.sensors.getCell(robot.currentX,
                                        robot.currentY).getDirt());
                logger.log(
                        Level.FINE,
                        "current dirt capacity "
                                + robot.getCurrentDirtCapacity());
            }
        }
    }
}
