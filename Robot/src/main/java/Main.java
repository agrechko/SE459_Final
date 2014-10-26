import java.io.File;
import java.util.Scanner;
import java.util.logging.Level;

import robot.RobotController;
import sensors.SensorsController;
import utils.LogFactory;


public class Main 
{
	final static int MAX_POWER = 50;
	final static int MAX_DIRT_CAPACITY = 50;
	final static int START_X = 0;
	final static int START_Y = 0;
//    Loggelog = new LogFactory().generateLog("Floorplan", Level.INFO);

		
	public static void main(String[] args) throws Exception 
	{
		String floorPlanLocation = "../Simulation/sample_floorplan.xml";
		if(args.length == 1)
			floorPlanLocation = args[0];
		
		File f = new File(floorPlanLocation);
		if(f.exists() && !f.isDirectory()) { 
			System.out.println("file found");
			floorPlanLocation = f.getAbsolutePath();
		}
		
		SensorsController sensors = new SensorsController(floorPlanLocation);
		
//		RobotController robot = new RobotController(sensors, MAX_POWER, MAX_DIRT_CAPACITY,
//				sensors.getChargingStationLocation().getCellX(), sensors.getChargingStationLocation().getCellY());
		RobotController robot = new RobotController(sensors, MAX_POWER, MAX_DIRT_CAPACITY, 0, 0);
		robot.start();
		
		Scanner scan = new Scanner(System.in);
		robot.printAvailableCommands();
		while(true)
		{
			String userCommand = scan.nextLine().toLowerCase().trim();
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
	}
}
