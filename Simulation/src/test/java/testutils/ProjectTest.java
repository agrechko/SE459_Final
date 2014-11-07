package testutils;
import java.util.logging.Logger;

import org.junit.*;

import utils.ProjectLog;
import utils.ProjectLogFormat;
import static org.junit.Assert.*;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;

public class ProjectTest {
	ProjectLog pl = new ProjectLog();
	Logger l = Logger.getLogger("main");
			
	@Test
	public void test_setup() {
		
		assertEquals(l.getLevel(), Level.FINE);
		assertEquals(l.getName(), "main");
		for (Handler lh: l.getHandlers()) {
			assertTrue( lh instanceof FileHandler );
			assertTrue(lh.getFormatter() instanceof ProjectLogFormat);
		}
				
		pl.addConsole();
		for (Handler lh: l.getHandlers()) {
			if ((lh instanceof FileHandler) == false) {
			    assertTrue( lh instanceof ConsoleHandler );
			}
		}
		
	}
		

}
