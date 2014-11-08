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
	final static int MAX_POWER = 50;
	final static int MAX_DIRT_CAPACITY = 50;
	final static int START_X = 0;
	final static int START_Y = 0;
	int command = -1;// command user issues after the clean cycle has ended

	public static void main(String[] args) throws Exception {
		ProjectLog pl = new ProjectLog();
		if (args.length == 2 && args[1] == "debug") {
			pl.addConsole();
		}
		while (true) {
			if (!startLoop(args)) {
				break;
			}
		}
	}

	public static boolean startLoop(String[] args) {
		boolean restart = false;
		Logger logger = Logger.getLogger("main");
		String floorPlanLocation = "../Simulation/sample_floorplan.xml";
		// String floorPlanLocation =
		// "../Simulation/sample_floorplan_small_room.xml";
		if (args.length == 1)
			floorPlanLocation = args[0];

		File f = new File(floorPlanLocation);
		if (f.exists() && !f.isDirectory()) {
			logger.log(Level.FINE, "file found");
			floorPlanLocation = f.getAbsolutePath();
		}

		SensorsController sensors = new SensorsController(floorPlanLocation);

		// RobotController robot = new RobotController(sensors, MAX_POWER,
		// MAX_DIRT_CAPACITY,
		// sensors.getChargingStationLocation().getCellX(),
		// sensors.getChargingStationLocation().getCellY());
		RobotController robot = new RobotController(sensors, MAX_POWER,
				MAX_DIRT_CAPACITY, 0, 0);
		robot.start();

		Scanner scan = new Scanner(System.in);
		robot.printAvailableCommands();
		while (true) {
			String userCommand = scan.nextLine().toLowerCase().trim();

			if (robot.currentState == State.STOP.getValue()) {
				if (userCommand.equals("restart")) {
					restart = true;
					break;
				} else if (userCommand.equals("shutdown")) {
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
				if (userCommand.equals("stop")) {
					break;
				}
			}
		}
		scan.close();
		return restart;
	}
}
