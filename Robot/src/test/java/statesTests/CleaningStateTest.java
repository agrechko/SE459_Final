/**
 * 
 */
package statesTests;

import static org.junit.Assert.*;
import static org.junit.Assume.*;

import java.util.LinkedList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import robot.RobotController;
import robot.RobotController.State;
import sensors.SensorsController;

/**
 * @author jimmykurian
 *
 */
public class CleaningStateTest {

	@SuppressWarnings("unchecked")
	@Test
	//Checks if Cleaning Capacity is at max, sends robot to go home
	public void maxDirtCapacityTest()
	{
		final int MAX_POWER = 20;
		final int MAX_DIRT_CAPACITY = 5;
		final int START_X = 0;
		final int START_Y = 0;
		
		String floorPlanLocation = "../Simulation/sample_floorplan_exploreTest4.xml";
		SensorsController sensors = new SensorsController(floorPlanLocation);
		
		RobotController robot = new RobotController(sensors, MAX_POWER, MAX_DIRT_CAPACITY, START_X, START_Y);

		robot.run();
		assertTrue(robot.getCurrentX() == 0); 
		assertTrue(robot.getCurrentY() == 0);
		assertTrue(robot.getCurrentDirtCapacity() == 1);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	//Checks if power is low, sends robot to go home
	public void lowPowerTest()
	{
		final int MAX_POWER = 4;
		final int MAX_DIRT_CAPACITY = 50;
		final int START_X = 0;
		final int START_Y = 0;

		String floorPlanLocation = "../Simulation/sample_floorplan_exploreTest1.xml";
		SensorsController sensors = new SensorsController(floorPlanLocation);
		
		RobotController robot = new RobotController(sensors, MAX_POWER, MAX_DIRT_CAPACITY, START_X, START_Y);
		
		robot.run();
		
		//Checks if Robot has Returned home and not entered Cleaning State
		assertTrue(robot.getCurrentX() == 0); 
		assertTrue(robot.getCurrentY() == 0);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	//Checks if floor unit is already clean
	public void floorUnitIsCleanTest()
	{
		final int MAX_POWER = 8;
		final int MAX_DIRT_CAPACITY = 50;
		final int START_X = 0;
		final int START_Y = 0;

		String floorPlanLocation = "../Simulation/sample_floorplan_exploreTest1.xml";
		SensorsController sensors = new SensorsController(floorPlanLocation);
		
		RobotController robot = new RobotController(sensors, MAX_POWER, MAX_DIRT_CAPACITY, START_X, START_Y);
		
		robot.run();
		assertTrue(robot.getCurrentX() == 0); 
		assertTrue(robot.getCurrentY() == 0);
		assertTrue(sensors.isClean(robot.getCurrentX(), robot.getCurrentY()) == true); 
	}
	
	@SuppressWarnings("unchecked")
	@Test
	//Checks if robot is cleaning floor unit
	public void isCleaningTest()
	{
		final int MAX_POWER = 20;
		final int MAX_DIRT_CAPACITY = 5;
		final int START_X = 0;
		final int START_Y = 0;
			
		String floorPlanLocation = "../Simulation/sample_floorplan_exploreTest4.xml";
		SensorsController sensors = new SensorsController(floorPlanLocation);
			
		RobotController robot = new RobotController(sensors, MAX_POWER, MAX_DIRT_CAPACITY, START_X, START_Y);

		assertTrue(sensors.isClean(2, 1) == false);
		robot.run();
		assertTrue(robot.getCurrentX() == 0); 
		assertTrue(robot.getCurrentY() == 0);
		assertTrue(sensors.isClean(2, 1) == true);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	//Checks if the entire Floor has been cleaned
	public void entireFloorIsCleanTest()
	{
		final int MAX_POWER = 20;
		final int MAX_DIRT_CAPACITY = 5;
		final int START_X = 0;
		final int START_Y = 0;
			
		String floorPlanLocation = "../Simulation/sample_floorplan_exploreTest4.xml";
		SensorsController sensors = new SensorsController(floorPlanLocation);
			
		RobotController robot = new RobotController(sensors, MAX_POWER, MAX_DIRT_CAPACITY, START_X, START_Y);

		assertTrue(sensors.isAllClean() == false);
		robot.run();
		assertTrue(robot.getCurrentX() == 0); 
		assertTrue(robot.getCurrentY() == 0);
		assertTrue(sensors.isAllClean() == true);
	}
}
