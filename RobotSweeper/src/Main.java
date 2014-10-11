import robot.RobotController;
import sensors.SensorsController;


public class Main 
{
	public static void main(String[] args) 
	{
		SensorsController sensors = new SensorsController(args[0]);
		RobotController robot = new RobotController(sensors);
		
	}
}
