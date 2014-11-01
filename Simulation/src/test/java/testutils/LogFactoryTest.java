package testutils;
import java.util.logging.Logger;

import org.junit.*;

import utils.LogFactory;
import utils.ProjectLogFormat;
import static org.junit.Assert.*;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Formatter;

class TestFormatter extends Formatter {

	@Override
	public String format(LogRecord arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
}


public class LogFactoryTest {
	LogFactory testLF = new LogFactory();

	@Test
	public void test_generateLog() {
		Logger testLog = testLF.generateLog();
		assertEquals(testLog.getLevel(), Level.ALL);
		assertEquals(testLog.getName(), null);
		for (Handler lh: testLog.getHandlers()) {
			assertTrue( lh instanceof ConsoleHandler );
			assertTrue(lh.getFormatter() instanceof ProjectLogFormat);
		}
		
		testLog = testLF.generateLog("test");
		assertEquals(testLog.getName(), "test");

		testLog = testLF.generateLog("test", Level.INFO);
		assertEquals(testLog.getLevel(), Level.INFO);


	}
	@Test
	public void test_generateLog2() {
		Logger testLog1 = testLF.generateLog("test", Level.INFO, new TestFormatter());
		for (Handler lh1: testLog1.getHandlers()) {
			System.out.println(lh1.getFormatter());
			assertTrue(lh1.getFormatter() instanceof TestFormatter);
		}
	}

}
