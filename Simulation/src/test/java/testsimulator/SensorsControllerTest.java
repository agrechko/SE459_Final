package testsimulator;
import objectsdto.CellData;
import objectsdto.Coord;
import sensors.SensorsController;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SensorsControllerTest {
	String floorPlanLocation = "../Simulation/sample_floorplan.xml";
	SensorsController sensor = new SensorsController(floorPlanLocation);

	@Before
	public void setUp() {
		sensor.setup();
		sensor.getCell(0, 0); // Setup test cell
		sensor.getCell(5, 7); // Setup test cell
	}

	@Test
	public void TestConstructor() {
		try {
			assertNotNull(sensor.memory);

		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	@Test
	public void getPathsTest() {
		try {
			int[] expected = { 1, 2, 1, 2 };
			int[] Paths = sensor.getPaths(0, 0);
			assertArrayEquals(expected, Paths);
		} catch (IllegalArgumentException e) {
			fail();
		}

	}

	@Test
	public void cleanTest() {
		CellData cd = sensor.getCell(0, 0);
		try {
			int curDirt00 = 1;
			int actualDirt = cd.getDirt();
			assertTrue(curDirt00 == actualDirt);
			sensor.clean(0, 0);
			int newDirt = cd.getDirt();
			assertTrue(0 == newDirt);
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	@Test
	public void isCleanTest() {
		try {
			assertFalse(sensor.isClean(0, 0));
			sensor.clean(0, 0);
			assertTrue(sensor.isClean(0, 0));
			assertFalse(sensor.isClean(5, 7));

		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	@Test
	public void SurfaceTest() {
		try {
			int currentSurface = sensor.getSurface(0, 0);
			assertTrue(currentSurface == 2);
			sensor.setSurface(0, 0, 1);// setting a new surface from 2 to 1
			int newSurface = sensor.getSurface(0, 0);
			assertTrue(newSurface == 1);
		} catch (IllegalArgumentException e) {
			fail();
		}

	}

	@Test
	public void isAllCleanTest() {
		try {

			for (Coord xy : sensor.memory.gridSet()) {
				CellData cd = sensor.memory.get(xy);
				while (cd.getDirt() != 0) {
					sensor.clean(cd.getCellX(), cd.getCellY());
				}
			}
			assertFalse(sensor.isAllClean());
			for (int i = 0; i < 50; i++) {
				for (int j = 0; j < 50; j++) {
					CellData cd = sensor.getCell(i, j);
					if (cd != null) {
						while (cd.getDirt() > 0) {
							sensor.clean(i, j);
						}
					}
				}
			}
			assertTrue(sensor.isAllClean());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	@Test
	public void getChargingStationLocationTest() {

		try {
			CellData cd = sensor.getChargingStationLocation();
			CellData cd2 = sensor.getCell(0, 0);
			assertTrue(cd.equals(cd2));
		} catch (IllegalArgumentException e) {

		}

	}

}
