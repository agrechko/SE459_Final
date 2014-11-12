package objectsdto;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.Before;
import org.junit.Test;

public class CellDataTest {
	CellData cd;

	@Before
	public void setUp() throws Exception {
		int[] p = new int[] {1,2,1,4};
		cd = new CellData(5, 6);
		cd.setChargingStation(false);
		cd.setDirt(4);
		cd.setPaths(p);
		cd.setSurface(2);
	}

	@Test
	public void test() {
		assertEquals(5, cd.getCellX());
		assertEquals(6, cd.getCellY());
		assertEquals(false, cd.isChargingStation());
		assertEquals(4, cd.getDirt());
		assertEquals(2, cd.getSurface());
		int[] t = new int[] {1,2,1,4};
		for (int i=0; i < 4;i++) {
			assertEquals(t[i], cd.getPaths()[i]);
		}
		
	}

}
