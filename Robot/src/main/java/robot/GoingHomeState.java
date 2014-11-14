package robot;

import java.util.logging.Level;
import java.util.logging.Logger;

import robot.RobotController.State;

public class GoingHomeState implements RobotStates {
    Logger logger = Logger.getLogger("main");

    public final void execute(final RobotController robot) {
        if (!robot.route.isEmpty()) {
            // 1: x pos, 2: x neg, 3: y pos, 4: y neg
            int prevStep = robot.route.pop();
            switch (prevStep) {
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
            default:
                throw new IllegalStateException();
            }
            int floorType = robot.sensors.getSurface(robot.currentX,
                    robot.currentY);
            robot.currentPower -= robot.getPowerConsumption(floorType);
        }
        logger.log(Level.FINE, "going home! currently at x: " + robot.currentX
                + " y: " + robot.currentY);
        if (robot.sensors.getCell(robot.currentX, robot.currentY)
                .isChargingStation()) {
            robot.currentState = State.CHARGING.getValue();
        }
    }
}
