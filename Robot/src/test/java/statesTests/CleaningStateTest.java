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
 * @author Jimmy
 *
 */
public class CleaningStateTest {

	@SuppressWarnings("unchecked")
	@Test
	//Checks if Cleaning Capacity is at max, sends robot to go home
	public void maxDirtCapacity()
	{
		final int MAX_POWER = 50;
		final int MAX_DIRT_CAPACITY = 0;
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
		robot.run();
		
		assertTrue(robot.getCurrentDirtCapacity() == 0);
		assertTrue(robot.currentState == State.GOING_HOME.getValue());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link robot.CleaningState#execute(robot.RobotController)}.
	 */
	@Test
	public final void testExecute() {
		fail("Not yet implemented"); // TODO
	}

}
