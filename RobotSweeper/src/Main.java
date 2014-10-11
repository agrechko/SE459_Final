import robot.DirtController;
import robot.RobotController;
import sensors.SensorsController;


public class Main 
{
	final static int maxPower = 50;
	final static int maxDirtCapacity = 50;
	public static void main(String[] args) 
	{
		SensorsController sensors = new SensorsController(args[0]);
		DirtController dirtController = new DirtController(); 
		
		RobotController robot = new RobotController(sensors, dirtController, maxPower, maxDirtCapacity);
		robot.execute();
	}
}
