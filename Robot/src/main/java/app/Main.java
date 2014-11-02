package app;

import java.io.File;
import java.util.Scanner;
import java.util.logging.Level;

import robot.RobotController;
import robot.RobotController.State;
import sensors.SensorsController;
import utils.LogFactory;


public class Main 
{
	final static int MAX_POWER = 50;
	final static int MAX_DIRT_CAPACITY = 50;
	final static int START_X = 0;
	final static int START_Y = 0;
	int command = -1;//command user issues after the clean cycle has ended
//    Loggelog = new LogFactory().generateLog("Floorplan", Level.INFO);

		
	public static void main(String[] args) throws Exception 
	{
		while(true)
		{
			if(!startLoop(args))
			{
				break;
			}
		}
	}
	
	public static boolean startLoop(String[] args)
	{
		boolean restart = false;
		//String floorPlanLocation = "../Simulation/sample_floorplan.xml";
		String floorPlanLocation = "../Simulation/sample_floorplan_small_room.xml";
		if(args.length == 1)
			floorPlanLocation = args[0];
		
		File f = new File(floorPlanLocation);
		if(f.exists() && !f.isDirectory()) { 
			System.out.println("file found");
			floorPlanLocation = f.getAbsolutePath();
		}
		
		SensorsController sensors = new SensorsController(floorPlanLocation);
		
//				RobotController robot = new RobotController(sensors, MAX_POWER, MAX_DIRT_CAPACITY,
//						sensors.getChargingStationLocation().getCellX(), sensors.getChargingStationLocation().getCellY());
		RobotController robot = new RobotController(sensors, MAX_POWER, MAX_DIRT_CAPACITY, 0, 0);
		robot.start();
		
		Scanner scan = new Scanner(System.in);
		robot.printAvailableCommands();
		while(true)
		{
			String userCommand = scan.nextLine().toLowerCase().trim();
			
			if(robot.currentState == State.STOP.getValue())
			{
				if(userCommand.equals("restart"))
				{
					restart = true;
					break;
				}
				else if(userCommand.equals("shutdown"))
				{
					break;
				}
				else
				{
					System.out.println("Command not recognized. Please try again.");
					robot.printStopCommands();
				}
			}
			
			if(!robot.userInput(userCommand))
			{
				System.out.println("Command not recognized. Please try again.");
				robot.printAvailableCommands();
			}
			else
			{
				if(userCommand.equals("stop"))
				{
					break;
				}
			}
		}
		
		return restart;
	}
}
