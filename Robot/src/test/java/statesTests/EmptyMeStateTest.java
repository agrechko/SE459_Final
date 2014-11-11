package statesTests;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Test;

import robot.RobotController;
import sensors.SensorsController;

public class EmptyMeStateTest 
{
	@Test
	public void movePositiveXAndBack() throws Exception 
	{
		final int MAX_POWER = 10;
		final int MAX_DIRT_CAPACITY = 3;
		final int START_X = 0;
		final int START_Y = 0;

		String floorPlanLocation = "../Simulation/sample_floorplan_emptymeTest.xml";
		SensorsController sensors = new SensorsController(floorPlanLocation);
		sensors.setup();
		
		RobotController robot = new RobotController(sensors, MAX_POWER, MAX_DIRT_CAPACITY, START_X, START_Y);
		robot.devModeOn = true;
		robot.devCommands = new LinkedList<String>();
		robot.devCommands.add("empty");
		robot.run();

		assertTrue(robot.getCurrentDirtCapacity() == 1);
	}
}
