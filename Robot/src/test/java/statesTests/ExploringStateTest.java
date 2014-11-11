package statesTests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;

import robot.RobotController;
import sensors.SensorsController;

public class ExploringStateTest 
{
	@SuppressWarnings("unchecked")
	@Test
	public void movePositiveXAndBack() throws Exception 
	{
		final int MAX_POWER = 4;
		final int MAX_DIRT_CAPACITY = 50;
		final int START_X = 0;
		final int START_Y = 0;

		String floorPlanLocation = "../Simulation/sample_floorplan_exploreTest1.xml";
		SensorsController sensors = new SensorsController(floorPlanLocation);
		sensors.setup();
		
		RobotController robot = new RobotController(sensors, MAX_POWER, MAX_DIRT_CAPACITY, START_X, START_Y);
		
		robot.run();
		
		assertTrue(robot.getCurrentX() == 0);
		assertTrue(robot.getCurrentY() == 0);
		assertTrue(MAX_POWER == robot.getCurrentPower());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void movePositiveYAndBack() throws Exception 
	{
		final int MAX_POWER = 4;
		final int MAX_DIRT_CAPACITY = 50;
		final int START_X = 0;
		final int START_Y = 0;
		
		String floorPlanLocation = "../Simulation/sample_floorplan_exploreTest2.xml";
		SensorsController sensors = new SensorsController(floorPlanLocation);
		sensors.setup();
		
		RobotController robot = new RobotController(sensors, MAX_POWER, MAX_DIRT_CAPACITY, START_X, START_Y);

		robot.run();
		
		assertTrue(robot.getCurrentX() == 0);
		assertTrue(robot.getCurrentY() == 0);
		assertTrue(robot.getCurrentPower() == MAX_POWER);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void moveMixedPositiveXThenYAndBack() throws Exception 
	{
		final int MAX_POWER = 8;
		final int MAX_DIRT_CAPACITY = 50;
		final int START_X = 0;
		final int START_Y = 0;
		
		String floorPlanLocation = "../Simulation/sample_floorplan_exploreTest3.xml";
		SensorsController sensors = new SensorsController(floorPlanLocation);
		sensors.setup();
		
		RobotController robot = new RobotController(sensors, MAX_POWER, MAX_DIRT_CAPACITY, START_X, START_Y);

		robot.run();
		
		assertTrue(robot.getCurrentX() == 0);
		assertTrue(robot.getCurrentY() == 0);
		assertTrue(MAX_POWER == robot.getCurrentPower());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void moveMixedPositiveXThenYLowPower() throws Exception 
	{
		final int MAX_POWER = 12;
		final int MAX_DIRT_CAPACITY = 50;
		final int START_X = 0;
		final int START_Y = 0;
		
		String floorPlanLocation = "../Simulation/sample_floorplan_exploreTest4.xml";
		SensorsController sensors = new SensorsController(floorPlanLocation);
		sensors.setup();
		
		RobotController robot = new RobotController(sensors, MAX_POWER, MAX_DIRT_CAPACITY, START_X, START_Y);

		robot.run();
		
		assertTrue(robot.getCurrentX() == 0);
		assertTrue(robot.getCurrentY() == 0);
		assertTrue(MAX_POWER == robot.getCurrentPower());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void movePositiveXThenBackThenPosiviteYAndBack() throws Exception 
	{
		final int MAX_POWER = 5;
		final int MAX_DIRT_CAPACITY = 50;
		final int START_X = 0;
		final int START_Y = 0;
		
		String floorPlanLocation = "../Simulation/sample_floorplan_exploreTest5.xml";
		SensorsController sensors = new SensorsController(floorPlanLocation);
		sensors.setup();
		
		RobotController robot = new RobotController(sensors, MAX_POWER, MAX_DIRT_CAPACITY, START_X, START_Y);

		robot.run();
		
		assertTrue(robot.getCurrentX() == 0);
		assertTrue(robot.getCurrentY() == 0);
		assertTrue(MAX_POWER == robot.getCurrentPower());
	}
}
