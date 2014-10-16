package robot;

import java.util.ArrayList;
import java.util.Random;

import robot.RobotController.State;

public class ExploringState implements RobotStates
{
	public void execute(RobotController robot) 
	{
		if(robot.currentPower <= (robot.maxPower/2))
		{
			robot.currentState = State.GOING_HOME.getValue();
		}
		else
		{
			//allowed indexes of paths to be chosen randomly to go to
			ArrayList<Integer> pathsIndexes = new ArrayList<Integer>();
			//0: unknown 1: open, 2: obstical, 4: stairs
			int[] paths = robot.sensors.getPaths(robot.currentX, robot.currentY);
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
			}
			else
			{
				robot.wentBackFrom = -1;//reset the coming back path
				Random gen = new Random();
				int selectedPath = pathsIndexes.get(gen.nextInt(pathsIndexes.size()));
				move(robot, selectedPath);
				
				//unvisited paths indexes from the available path indexes in pathsIndexes array
//				ArrayList<Integer> unvisitedPaths = new ArrayList<Integer>();
//				for(Integer i : pathsIndexes)
//				{
//					if(!getVisitStatus(robot, i)){
//						unvisitedPaths.add(i);
//					}
//				}
//				
//				int numUnvisitedPaths = unvisitedPaths.size();
//				if(numUnvisitedPaths != 0)//pick a random unvisited path to go to
//				{
//					
//				}
//				else
//				{
//					goBackOneStep(robot);
//				}
			}
			
			System.out.println("Current Location x: " + robot.currentX +" y: " + robot.currentY);
			robot.currentPower -= 1;
			
			if(!robot.sensors.isClean(robot.currentX, robot.currentY))
			{
				System.out.println("Entering cleaning mode");
				robot.currentState = State.CLEANING.getValue();
			}
		}
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
	
	//call sensors to check is the given path has been visited
	private boolean getVisitStatus(RobotController robot, int i)
	{
		boolean status = false;
		switch(i){
		case 0:
			status = robot.sensors.isVisited(robot.currentX + 1, robot.currentY);
			break;
		case 1:
			status = robot.sensors.isVisited(robot.currentX - 1, robot.currentY);
			break;
		case 2:
			status = robot.sensors.isVisited(robot.currentX, robot.currentY + 1);
			break;
		case 3:
			status = robot.sensors.isVisited(robot.currentX, robot.currentY - 1);
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
}
