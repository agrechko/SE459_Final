package robot;

import java.util.Scanner;

import robot.RobotController.State;

public class ChargingState implements RobotStates
{
	public void execute(RobotController robot)
	{
		Boolean userAssistance = false;//user assistance is needed to continue
		robot.currentPower = robot.maxPower;
		if(robot.currentDirtCapacity == 0)
		{
			robot.emptyMe = true;
			userAssistance = true;
			System.out.println("----- Empty Me -----");
			
			Scanner scan = new Scanner(System.in);
			System.out.println("Available commands:");
			System.out.println("Empty - to empty the dirt cargo bay and continue cleaning");
			System.out.println("Stop - to empty the dirt cargo bay and do not continue cleaning");
			//wait for user input to empty the dirt cargo bay or stop clean cycle
			
			while(true)
			{
				String userCommand = scan.nextLine().toLowerCase().trim();
				if(userCommand.equals("emptyme"))
				{
					robot.emptyMe = false;
					robot.currentDirtCapacity = robot.currentDirtCapacity;
					robot.currentState = State.READY_TO_CLEAN.getValue();
					break;
				}
				else if(userCommand.equals("stop"))
				{
					robot.emptyMe = false;
					robot.currentDirtCapacity = robot.currentDirtCapacity;
					robot.currentState = State.STOP.getValue();
					break;
				}
				else
				{
					System.out.println("Command not recognized. Please try again.");
				}
			}
		}
		
		//all cells have been visited in this clean cycle so stop
		if(robot.sensors.isAllClean())
		{
			System.out.println("The floor is so clean you can eat off it. Stopping...");
			robot.currentState = State.STOP.getValue();
		}
		else
		{
			if(!userAssistance)
			{
				robot.currentState = State.READY_TO_CLEAN.getValue();
			}
		}
	}
}
