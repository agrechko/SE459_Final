package app;

import java.io.File;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import robot.RobotController;
import robot.RobotController.State;
import sensors.SensorsController;
import utils.ProjectLog;

public class Main {
    static final int MAX_POWER = 50;
    static final int MAX_DIRT_CAPACITY = 50;
    static final int START_X = 0;
    static final int START_Y = 0;
    // command user issues after the clean cycle has ended
    int command = -1;

    public static void main(final String[] args) {
        ProjectLog pl = new ProjectLog();
        if (args.length == 2 && "debug".equals(args[1])) {
            pl.addConsole();
        }
        while (true) {
            if (!startLoop(args)) {
                break;
            }
        }
    }

    public static boolean startLoop(final String[] args) {
        boolean restart = false;
        Logger logger = Logger.getLogger("main");
        String floorPlanLocation = "../Simulation/sample_floorplan.xml";
        if (args.length == 1) {
            floorPlanLocation = args[0];
        }

        File f = new File(floorPlanLocation);
        if (f.exists() && !f.isDirectory()) {
            logger.log(Level.FINE, "file found");
            floorPlanLocation = f.getAbsolutePath();
        }

        SensorsController sensors = new SensorsController(floorPlanLocation);
        sensors.setup();

        RobotController robot = new RobotController(sensors, MAX_POWER,
                MAX_DIRT_CAPACITY, 0, 0);
        robot.start();

        Scanner scan = new Scanner(System.in);
        robot.printAvailableCommands();
        while (true) {
            String userCommand = scan.nextLine().toLowerCase().trim();

            if (robot.currentState == State.STOP.getValue()) {
                if ("restart".equals(userCommand)) {
                    restart = true;
                    break;
                } else if ("shutdown".equals(userCommand)) {
                    break;
                } else {
                    System.out
                            .println("Command not recognized. Please try again.");
                    robot.printAvailableCommands();
                    continue;
                }
            }

            if (!robot.userInput(userCommand)) {
                System.out.println("Command not recognized. Please try again.");
                robot.printAvailableCommands();
            } else {
                if ("stop".equals(userCommand)) {
                    break;
                }
            }
        }
        scan.close();
        return restart;
    }
}
