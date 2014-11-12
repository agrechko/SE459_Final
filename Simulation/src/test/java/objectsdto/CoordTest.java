package objectsdto;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CoordTest {
	Coord c;
	
	@Before
	public void setUp() throws Exception {
		c = new Coord(7, 8);
	}

	@Test
	public void test() {
		assertEquals(7, c.getx());
		assertEquals(8, c.gety());
		Coord b = new Coord(5, 6);
		assertNotSame(b, c);
		assertNotSame(null, c);
		Coord a = new Coord();
		a.setx(7);
		a.sety(8);
		assertEquals(a.hashCode(), c.hashCode()); 
		assertEquals(a, c);
	}

}
