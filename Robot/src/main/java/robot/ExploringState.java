package robot;

import java.util.ArrayList;
import java.util.Random;

import robot.RobotController.State;

public class ExploringState implements RobotStates
{
	public void execute(RobotController robot) 
	{
		if(robot.firstStart)
		{
			robot.firstStart = false;
			int[] paths = robot.sensors.getPaths(robot.currentX, robot.currentY);//this is needed so that sensors scan the current cell for info
			if(!robot.sensors.isClean(robot.currentX, robot.currentY))
			{
				System.out.println("Entering cleaning mode");
				robot.currentState = State.CLEANING.getValue();
			}
		}
		else
		{
			if(robot.currentPower <= (robot.maxPower/2))
			{
				robot.currentState = State.GOING_HOME.getValue();
			}
			else
			{
				//allowed indexes of paths to be chosen randomly to go to
				ArrayList<Integer> pathsIndexes = new ArrayList<Integer>();
				int floorType = -1;
				
				int[] paths = null;

				//0: unknown 1: open, 2: obstical, 4: stairs
				paths = robot.sensors.getPaths(robot.currentX, robot.currentY);

				//0: x pos, 1: x neg, 2: y pos, 3: y neg
				int cameFrom = -1;
				if(robot.route.size() != 0){
					cameFrom = robot.route.peek();
				}
				
				if(paths[0] == 1 && robot.wentBackFrom != 0 && (cameFrom == -1 || cameFrom != 1))//can go x pos and did not come from x neg
				{
					pathsIndexes.add(0);
					//robot.currentX += 1;
					//robot.route.push(1);
				}
				if(paths[1] == 1 && robot.wentBackFrom != 1 && (cameFrom == -1 || cameFrom != 0))//can go x neg and did not come from x pos
				{
					pathsIndexes.add(1);
					//robot.currentX -= 1;
					//robot.route.push(2);
				}
				if(paths[2] == 1 && robot.wentBackFrom != 2 && (cameFrom == -1 || cameFrom != 3))//can go y pos and did not come from y neg
				{
					pathsIndexes.add(2);
					//robot.currentY += 1;
					//robot.route.push(3);
				}
				if(paths[3] == 1 && robot.wentBackFrom != 3 && (cameFrom == -1 || cameFrom != 2))//can go y neg and did not came from y pos
				{
					pathsIndexes.add(3);
					//robot.currentY -= 1;
					//robot.route.push(4);
				}
				
				if(pathsIndexes.size() == 0) //need to go back no way out
				{
					goBackOneStep(robot);
					floorType = robot.sensors.getSurface(robot.currentX, robot.currentY);
				}
				else
				{
					robot.wentBackFrom = -1;//reset the coming back path
					Random gen = new Random();
					Boolean selected = false;
					int selectedPath = -1;
					
					//unvisited paths indexes from the available path indexes in pathsIndexes array
					ArrayList<Integer> dirtyPaths = new ArrayList<Integer>();
					for(Integer i : pathsIndexes)
					{
						if(!getCleanStatus(robot, i)){
							dirtyPaths.add(i);
						}
					}

					while(!selected && dirtyPaths.size() > 0)//pick a random unvisited path to go to
					{
						int selectedIndex = gen.nextInt(dirtyPaths.size());
						selectedPath = dirtyPaths.get(selectedIndex);//gets a random unvisited path
						floorType = getFloorType(robot, selectedPath);
						if(checkIfEnoughPowerToMove(robot, floorType))
						{
							selected = true;
							continue;
						}
						dirtyPaths.remove(selectedIndex);
					}
					
					while(!selected && pathsIndexes.size() > 0)
					{
						int selectedIndex = gen.nextInt(pathsIndexes.size());
						selectedPath = pathsIndexes.get(selectedIndex);
						floorType = getFloorType(robot, selectedPath);//robot.sensors.getSurface(robot.currentX, robot.currentY);
							
						if(checkIfEnoughPowerToMove(robot, floorType))
						{
							selected = true;
							continue;
						}
						pathsIndexes.remove(selectedIndex);//remove index because moving to this path will draw to much power
					}
					if(pathsIndexes.size() != 0)
					{
						move(robot, selectedPath);
					}
					else
					{
						//going home because we were not able to go to any of the available paths, meaning we do not have enough power to go to them
						robot.currentState = State.GOING_HOME.getValue();
					}
				}
				
				if(robot.currentState != State.GOING_HOME.getValue())
				{
//					System.out.println("Current Location x: " + robot.currentX +" y: " + robot.currentY);
					System.out.println("Currently: " + robot.sensors.getCell(robot.currentX, robot.currentY));
					robot.currentPower -= robot.getPowerConsumption(floorType);
					
					if(robot.sensors.getCell(robot.currentX, robot.currentY).isChargingStation())
					{
						robot.route.clear();//cleans back route stack if we encountered a charging station because we know a shorter way back home
					}
					
					if(!robot.sensors.isClean(robot.currentX, robot.currentY))
					{
						System.out.println("Entering cleaning mode");
						robot.currentState = State.CLEANING.getValue();
					}
				}
			}
		}
	}
	
	private boolean checkIfEnoughPowerToMove(RobotController robot, int floorType)
	{
		return robot.currentPower - robot.getPowerConsumption(floorType) > robot.maxPower/2;
	}
	
	private void move(RobotController robot, int selectedPath)
	{
		switch(selectedPath){
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
		}
	}
	
	//call sensors to check is the given path has dirt
	private boolean getCleanStatus(RobotController robot, int i)
	{
		boolean status = false;
		switch(i){
		case 0:
			robot.sensors.getCell(robot.currentX + 1, robot.currentY);//gets basic information about adjacent cell into memory
			status = robot.sensors.isClean(robot.currentX + 1, robot.currentY);//checks to see if the adjacent cell is clean
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
		}
		return status;
	}
	
	private void goBackOneStep(RobotController robot)
	{
		int prevStep = robot.route.pop();
		switch(prevStep){
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
		}
	}
	
	//looks up the floor type of an adjacent cell to make sure we have enough power to move there
	public int getFloorType(RobotController robot, int selectedPath)
	{
		int floorType = -1;
		switch(selectedPath){
		case 0: 
			floorType = robot.sensors.getCell(robot.currentX + 1, robot.currentY).getSurface();
			break;
		case 1:
			floorType = robot.sensors.getCell(robot.currentX - 1, robot.currentY).getSurface();
			break;
		case 2:
			floorType = robot.sensors.getCell(robot.currentX, robot.currentY + 1).getSurface();
			break;
		case 3:
			floorType = robot.sensors.getCell(robot.currentX, robot.currentY - 1).getSurface();
			break;
		}
		return floorType;
	}
}
