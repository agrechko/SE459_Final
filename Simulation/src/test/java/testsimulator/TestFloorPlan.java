package testsimulator;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.junit.*;

import static org.junit.Assert.* ;
import sensors.FloorPlan;
import objectsDTO.CellData;
import objectsDTO.Coord;


public class TestFloorPlan {
	
	@Test
	public void testrun() throws IOException, XMLStreamException {
		FloorPlan f = new FloorPlan("test_floorplan.xml");
		f.read();
		CellData cd0 = f.get(new Coord(0,0));
		assertTrue(cd0 != null);
		assertTrue(cd0.isChargingStation());
		assertEquals(cd0.getCellX(), 0);
		assertEquals(cd0.getCellY(), 0);
		assertEquals(cd0.getDirt(), 1);
		assertEquals(cd0.getSurface(), 2);
		int[] testIntArray1 = new int[] {1,2,1,2};
		int count = 0;
		for (int i: cd0.getPaths()) {
			assertEquals(i, testIntArray1[count]);
			count++;
		}
		CellData cd1 = f.get(new Coord(1, 0));
		assertTrue(cd1 != null);
		assertFalse(cd1.isChargingStation());
		assertEquals(cd1.getCellX(), 1);
		assertEquals(cd1.getCellY(), 0);
		assertEquals(cd1.getDirt(), 1);
		assertEquals(cd1.getSurface(), 2);
		int[] testIntArray2 = new int[] {1,1,1,2};
		count = 0;
		for (int i: cd1.getPaths()) {
			assertEquals(i, testIntArray2[count]);
			count++;
		}
		
	}

}
