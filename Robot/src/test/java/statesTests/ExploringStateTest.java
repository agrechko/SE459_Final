package statesTests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;

import robot.DirtController;
import robot.RobotController;
import sensors.SensorsController;

public class ExploringStateTest 
{
	@SuppressWarnings("unchecked")
	@Test
	public void movePositiveXAndBack() throws Exception 
	{
		final int MAX_POWER = 50;
		final int MAX_DIRT_CAPACITY = 50;
		final int START_X = 0;
		final int START_Y = 0;
		
		String floorPlanLocation = "./resources/floorPlan";
		SensorsController sensors = new SensorsController(floorPlanLocation);
		DirtController dirtController = new DirtController(); 
		
		RobotController robot = new RobotController(sensors, dirtController, MAX_POWER, MAX_DIRT_CAPACITY, START_X, START_Y);
		robot.devModeOn = true;
		
		LinkedList<int[]> testPaths = new LinkedList<int[]>();
		testPaths.add(new int[]{1,2,2,2});//(1,0)
		testPaths.add(new int[]{1,2,2,2});//(2,0)
		testPaths.add(new int[]{2,1,2,2});//no where to go turn around (1,0)
		testPaths.add(new int[]{1,2,2,2});//just came back from allowed path (0,0)
		
		robot.devpaths = (LinkedList<int[]>) testPaths.clone();
		robot.run();
		
		assertTrue(robot.getCurrentX() == 0);
		assertTrue(robot.getCurrentY() == 0);
		assertTrue(MAX_POWER - testPaths.size() == robot.getCurrentPower());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void movePositiveYAndBack() throws Exception 
	{
		final int MAX_POWER = 50;
		final int MAX_DIRT_CAPACITY = 50;
		final int START_X = 0;
		final int START_Y = 0;
		
		String floorPlanLocation = "./resources/floorPlan";
		SensorsController sensors = new SensorsController(floorPlanLocation);
		DirtController dirtController = new DirtController(); 
		
		RobotController robot = new RobotController(sensors, dirtController, MAX_POWER, MAX_DIRT_CAPACITY, START_X, START_Y);
		robot.devModeOn = true;
		
		LinkedList<int[]> testPaths = new LinkedList<int[]>();
		testPaths.add(new int[]{2,2,1,2});//(0,1)
		testPaths.add(new int[]{2,2,1,2});//(0,2)
		testPaths.add(new int[]{2,2,2,1});//no where to go turn around (0,1)
		testPaths.add(new int[]{2,2,1,2});//just came back from allowed path (0,0)
		
		robot.devpaths = (LinkedList<int[]>) testPaths.clone();
		robot.run();
		
		assertTrue(robot.getCurrentX() == 0);
		assertTrue(robot.getCurrentY() == 0);
		assertTrue(MAX_POWER - testPaths.size() == robot.getCurrentPower());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void moveMixedPositiveXThenYAndBack() throws Exception 
	{
		final int MAX_POWER = 50;
		final int MAX_DIRT_CAPACITY = 50;
		final int START_X = 0;
		final int START_Y = 0;
		
		String floorPlanLocation = "./resources/floorPlan";
		SensorsController sensors = new SensorsController(floorPlanLocation);
		DirtController dirtController = new DirtController(); 
		
		RobotController robot = new RobotController(sensors, dirtController, MAX_POWER, MAX_DIRT_CAPACITY, START_X, START_Y);
		robot.devModeOn = true;
		
		LinkedList<int[]> testPaths = new LinkedList<int[]>();
		testPaths.add(new int[]{1,2,2,2});//(1,0)
		testPaths.add(new int[]{2,2,1,2});//(1,1)
		testPaths.add(new int[]{1,2,2,2});//(2,1)
		testPaths.add(new int[]{2,2,1,2});//(2,2)
		
		testPaths.add(new int[]{2,2,2,1});//no where to go turn around (2,1)
		
		testPaths.add(new int[]{2,2,1,2});//just came back from allowed path (1,1)
		testPaths.add(new int[]{1,2,2,2});//just came back from allowed path (1,0)
		testPaths.add(new int[]{2,2,1,2});//just came back from allowed path (0,0)
		
		robot.devpaths = (LinkedList<int[]>) testPaths.clone();
		robot.run();
		
		assertTrue(robot.getCurrentX() == 0);
		assertTrue(robot.getCurrentY() == 0);
		assertTrue(MAX_POWER - testPaths.size() == robot.getCurrentPower());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void moveMixedPositiveXThenYLowPower() throws Exception 
	{
		final int MAX_POWER = 4;
		final int MAX_DIRT_CAPACITY = 50;
		final int START_X = 0;
		final int START_Y = 0;
		
		String floorPlanLocation = "./resources/floorPlan";
		SensorsController sensors = new SensorsController(floorPlanLocation);
		DirtController dirtController = new DirtController(); 
		
		RobotController robot = new RobotController(sensors, dirtController, MAX_POWER, MAX_DIRT_CAPACITY, START_X, START_Y);
		robot.devModeOn = true;
		
		LinkedList<int[]> testPaths = new LinkedList<int[]>();
		testPaths.add(new int[]{1,2,2,2});//(1,0)
		testPaths.add(new int[]{2,2,1,2});//(1,1)
		testPaths.add(new int[]{1,2,2,2});//(2,1)
		testPaths.add(new int[]{2,2,1,2});//(2,2) low power state changed to going home
		
		testPaths.add(new int[]{2,2,1,2});
		testPaths.add(new int[]{1,2,2,2});
		testPaths.add(new int[]{2,2,1,2});
		
		robot.devpaths = (LinkedList<int[]>) testPaths.clone();
		robot.run();
		
		assertTrue(robot.getCurrentX() == 0);
		assertTrue(robot.getCurrentY() == 0);
		assertTrue(0 == robot.getCurrentPower());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void movePositiveXThenBackThenPosiviteYAndBack() throws Exception 
	{
		final int MAX_POWER = 50;
		final int MAX_DIRT_CAPACITY = 50;
		final int START_X = 0;
		final int START_Y = 0;
		
		String floorPlanLocation = "./resources/floorPlan";
		SensorsController sensors = new SensorsController(floorPlanLocation);
		DirtController dirtController = new DirtController(); 
		
		RobotController robot = new RobotController(sensors, dirtController, MAX_POWER, MAX_DIRT_CAPACITY, START_X, START_Y);
		robot.devModeOn = true;
		
		LinkedList<int[]> testPaths = new LinkedList<int[]>();
		testPaths.add(new int[]{1,2,2,2});//(1,0)
		testPaths.add(new int[]{1,2,2,2});//(2,0)
		testPaths.add(new int[]{2,1,2,2});//(1,0) need to go back
		testPaths.add(new int[]{1,2,2,2});//(0,0) still going back
		
		testPaths.add(new int[]{1,2,1,2});//(0,1) switch to positive y
		testPaths.add(new int[]{2,2,1,2});//(0,2)
		testPaths.add(new int[]{2,2,2,1});//(0,1) need to go back
		testPaths.add(new int[]{2,2,1,2});//(0,0) still going back

		
		robot.devpaths = (LinkedList<int[]>) testPaths.clone();
		robot.run();
		
		assertTrue(robot.getCurrentX() == 0);
		assertTrue(robot.getCurrentY() == 0);
		assertTrue(MAX_POWER - testPaths.size() == robot.getCurrentPower());
	}
}
