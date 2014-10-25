/**
 * 
 */
package statesTests;

import static org.junit.Assert.*;

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
		final int MAX_POWER = 50;
		final int MAX_DIRT_CAPACITY = 50;
		final int START_X = 0;
		final int START_Y = 0;
		
		String floorPlanLocation = "./resources/floorPlan";
		SensorsController sensors = new SensorsController(floorPlanLocation);
		
		RobotController robot = new RobotController(sensors, MAX_POWER, MAX_DIRT_CAPACITY, START_X, START_Y);
		robot.devModeOn = true;
		
		LinkedList<int[]> testPaths = new LinkedList<int[]>();
		testPaths.add(new int[]{1,2,2,2});//(1,0)
		testPaths.add(new int[]{2,2,1,2});//(1,1)
		testPaths.add(new int[]{1,2,2,2});//(2,1)
		
		robot.devpaths = (LinkedList<int[]>) testPaths.clone();
		robot.setCurrentDirtCapacity(0);
		robot.currentState = 2;
		robot.run();
		
		assertTrue(robot.getCurrentDirtCapacity() == 0);
		assertTrue(robot.currentState == State.GOING_HOME.getValue());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	//Checks if power is low, sends robot to go home
	public void lowPowerTest()
	{
		final int MAX_POWER = 50;
		final int MAX_DIRT_CAPACITY = 50;
		final int START_X = 0;
		final int START_Y = 0;
		
		String floorPlanLocation = "./resources/floorPlan";
		SensorsController sensors = new SensorsController(floorPlanLocation);
		
		RobotController robot = new RobotController(sensors, MAX_POWER, MAX_DIRT_CAPACITY, START_X, START_Y);
		robot.devModeOn = true;
		
		LinkedList<int[]> testPaths = new LinkedList<int[]>();
		testPaths.add(new int[]{1,2,2,2});//(1,0)
		testPaths.add(new int[]{2,2,1,2});//(1,1)
		testPaths.add(new int[]{1,2,2,2});//(2,1)
		testPaths.add(new int[]{2,2,1,2});//(2,2) low power state changed to going home
		
		robot.devpaths = (LinkedList<int[]>) testPaths.clone();
		robot.currentState = 2;
		robot.run();
		
		assertTrue(0 == robot.getCurrentPower());
		assertTrue(robot.currentState == State.GOING_HOME.getValue());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	//Checks if floor unit is already clean
	public void floorUnitIsCleanTest()
	{
		//needs to be implemented
	}
	
	@SuppressWarnings("unchecked")
	@Test
	//Checks if robot is cleaning floor unit
	public void isCleaningTest()
	{
		//needs to be implemented
	}
}
