package objectsdto;

import static org.junit.Assert.*;

import java.awt.List;

import org.junit.*;

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
		int[] p = new int[] {1,2,1,4};
		CellData t1 = new CellData(5, 6);
		t1.setChargingStation(false);
		t1.setDirt(4);
		t1.setPaths(p);
		t1.setSurface(2);
		assertEquals(t1, cd);
		assertEquals(t1.hashCode(), cd.hashCode());
		int[] s = new int[] {1,2,1,1};
		t1.setPaths(s);		
		assertNotSame(t1, cd);
		List l = new List();
		assertNotSame(t1, l);
		
		
	}

}
