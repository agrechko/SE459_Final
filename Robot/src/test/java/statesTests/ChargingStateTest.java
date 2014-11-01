package statesTests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import robot.RobotController;
import sensors.SensorsController;

public class ChargingStateTest 
{
	@Test
	public void movePositiveXAndBack() throws Exception 
	{
		final int MAX_POWER = 4;
		final int MAX_DIRT_CAPACITY = 50;
		final int START_X = 0;
		final int START_Y = 0;

		String floorPlanLocation = "../Simulation/sample_floorplan_exploreTest1.xml";
		SensorsController sensors = new SensorsController(floorPlanLocation);
		
		RobotController robot = new RobotController(sensors, MAX_POWER, MAX_DIRT_CAPACITY, START_X, START_Y);
		
		robot.run();

		assertTrue(MAX_POWER == robot.getCurrentPower());
	}
}
