package robot;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import robot.RobotController.State;

public class ExploringState implements RobotStates {
	Logger logger = Logger.getLogger("main");
	Random gen = new Random();
	Boolean selected = false;
	int selectedPath = -1;
	int floorType = -1;

	public void execute(RobotController robot) {
		if (robot.firstStart) {
			robot.firstStart = false;
			// this is needed so that sensors scan the current cell for info
			int[] paths = robot.sensors
					.getPaths(robot.currentX, robot.currentY);
			if (!robot.sensors.isClean(robot.currentX, robot.currentY)) {
				logger.log(Level.FINE, "Entering cleaning mode");
				robot.currentState = State.CLEANING.getValue();
			}
		} else {
			if (robot.currentPower <= (robot.maxPower / 2)) {
				robot.currentState = State.GOING_HOME.getValue();
			} else {
				choosePathAndMove(robot);
			}
		}
	}
	
	private void choosePathAndMove(RobotController robot){
		// allowed indexes of paths to be chosen randomly to go to
		ArrayList<Integer> pathsIndexes = new ArrayList<Integer>();

		int[] paths = null;

		// 0: unknown 1: open, 2: obstical, 4: stairs
		paths = robot.sensors.getPaths(robot.currentX, robot.currentY);

		// 0: x pos, 1: x neg, 2: y pos, 3: y neg
		int cameFrom = -1;
		if (!robot.route.isEmpty()) {
			cameFrom = robot.route.peek();
		}

		populatePaths(robot, paths, pathsIndexes, cameFrom);

		// need to go back no way out
		if (pathsIndexes.isEmpty()){
			goBackOneStep(robot);
			floorType = robot.sensors.getSurface(robot.currentX,
					robot.currentY);
		} else {
			// reset the coming back path
			robot.wentBackFrom = -1;
			

			// unvisited paths indexes from the available path indexes
			// in pathsIndexes array
			ArrayList<Integer> dirtyPaths = new ArrayList<Integer>();
			for (Integer i : pathsIndexes) {
				if (!getCleanStatus(robot, i)) {
					dirtyPaths.add(i);
				}
			}

			pickPath(robot, dirtyPaths);
			pickPath(robot, pathsIndexes);
			
			if (!pathsIndexes.isEmpty()) {
				move(robot, selectedPath);
			} else {
				// going home because we were not able to go to any of
				// the available paths, meaning we do not have enough
				// power to go to them
				robot.currentState = State.GOING_HOME.getValue();
			}
		}

		if (robot.currentState != State.GOING_HOME.getValue()) {
			logger.log(Level.FINE, "Currently: " + robot.sensors.getCell(robot.currentX, robot.currentY));
			robot.currentPower -= robot.getPowerConsumption(floorType);

			if (robot.sensors.getCell(robot.currentX, robot.currentY)
					.isChargingStation()) {
				// cleans back route stack if we encountered a charging 
				//station because we know a shorter way back home
				robot.route.clear();
			}

			if (!robot.sensors.isClean(robot.currentX, robot.currentY)) {
				logger.log(Level.FINE, "Entering cleaning mode");
				robot.currentState = State.CLEANING.getValue();
			}
		}
	}

	private void pickPath(RobotController robot, ArrayList<Integer> paths){
		while (!selected && !paths.isEmpty()){
			int selectedIndex = gen.nextInt(paths.size());
			// gets a random unvisited path
			selectedPath = paths.get(selectedIndex);
			floorType = getFloorType(robot, selectedPath);
			if (checkIfEnoughPowerToMove(robot, floorType)) {
				selected = true;
				continue;
			}
			// remove index because moving to this path will draw to much power
			paths.remove(selectedIndex);
		}
	}
	
	private void populatePaths(RobotController robot, int[] paths, ArrayList<Integer> pathsIndexes, int cameFrom){		
		// can go x pos and did not come from x neg
		if (paths[0] == 1 && robot.wentBackFrom != 0
				&& (cameFrom == -1 || cameFrom != 1)){
			pathsIndexes.add(0);
		}
		// can go x neg and did not come from x pos
		if (paths[1] == 1 && robot.wentBackFrom != 1
				&& (cameFrom == -1 || cameFrom != 0)){
			pathsIndexes.add(1);
		}
		// can go y pos and did not come from y neg
		if (paths[2] == 1 && robot.wentBackFrom != 2
				&& (cameFrom == -1 || cameFrom != 3)){
			pathsIndexes.add(2);
		}
		// can go y neg and did not came from y pos
		if (paths[3] == 1 && robot.wentBackFrom != 3
				&& (cameFrom == -1 || cameFrom != 2)){
			pathsIndexes.add(3);
		}
	}
	
	private boolean checkIfEnoughPowerToMove(RobotController robot,
			int floorType) {
		return robot.currentPower - robot.getPowerConsumption(floorType) > robot.maxPower / 2;
	}

	private void move(RobotController robot, int selectedPath) {
		switch (selectedPath) {
		case 0:
			robot.currentX += 1;
			robot.route.push(0);
			break;
		case 1:
			robot.currentX -= 1;
			robot.route.push(1);
			break;
		case 2:
			robot.currentY += 1;
			robot.route.push(2);
			break;
		case 3:
			robot.currentY -= 1;
			robot.route.push(3);
			break;
		default:
		    throw new IllegalStateException();
		}
	}

	// call sensors to check is the given path has dirt
	private boolean getCleanStatus(RobotController robot, int i) {
		boolean status = false;
		switch (i) {
		case 0:
			// gets basic information about adjacent cell into memory
			robot.sensors.getCell(robot.currentX + 1, robot.currentY);
			// checks to see if the adjacent cell is clean
			status = robot.sensors.isClean(robot.currentX + 1, robot.currentY);
			break;
		case 1:
			robot.sensors.getCell(robot.currentX - 1, robot.currentY);
			status = robot.sensors.isClean(robot.currentX - 1, robot.currentY);
			break;
		case 2:
			robot.sensors.getCell(robot.currentX, robot.currentY + 1);
			status = robot.sensors.isClean(robot.currentX, robot.currentY + 1);
			break;
		case 3:
			robot.sensors.getCell(robot.currentX, robot.currentY - 1);
			status = robot.sensors.isClean(robot.currentX, robot.currentY - 1);
			break;
		default:
		    throw new IllegalStateException();
		}
		return status;
	}

	private void goBackOneStep(RobotController robot) {
		int prevStep = robot.route.pop();
		switch (prevStep) {
		case 0:
			robot.currentX -= 1;
			robot.wentBackFrom = 0;
			break;
		case 1:
			robot.currentX += 1;
			robot.wentBackFrom = 1;
			break;
		case 2:
			robot.currentY -= 1;
			robot.wentBackFrom = 2;
			break;
		case 3:
			robot.currentY += 1;
			robot.wentBackFrom = 3;
			break;
		default:
		    throw new IllegalStateException();
		}
	}

	// looks up the floor type of an adjacent cell to make sure we have enough
	// power to move there
	public int getFloorType(RobotController robot, int selectedPath) {
		int floor = -1;
		switch (selectedPath) {
		case 0:
			floor = robot.sensors.getCell(robot.currentX + 1,
					robot.currentY).getSurface();
			break;
		case 1:
			floor = robot.sensors.getCell(robot.currentX - 1,
					robot.currentY).getSurface();
			break;
		case 2:
			floor = robot.sensors.getCell(robot.currentX,
					robot.currentY + 1).getSurface();
			break;
		case 3:
			floor = robot.sensors.getCell(robot.currentX,
					robot.currentY - 1).getSurface();
			break;
		default:
		    throw new IllegalStateException();
		}
		return floor;
	}
}
