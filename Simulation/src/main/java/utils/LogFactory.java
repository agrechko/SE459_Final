package utils;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.Formatter;

public class LogFactory {
	
	public LogFactory () {}
	
	public Logger generateLog() {
		Logger log = Logger.getAnonymousLogger();
		log.setUseParentHandlers(false);
		log.setLevel(Level.ALL);
		ConsoleHandler hdlr = new ConsoleHandler();
		hdlr.setFormatter(new ProjectLogFormat());
		log.addHandler(hdlr);
		return log;
	}

	public Logger generateLog(String name) {
		Logger log = Logger.getLogger(name);
		log.setUseParentHandlers(false);
		log.setLevel(Level.ALL);
		ConsoleHandler hdlr = new ConsoleHandler();
		hdlr.setFormatter(new ProjectLogFormat());
		log.addHandler(hdlr);
		return log;
	}

	public Logger generateLog(String name, Level level) {
		Logger log = Logger.getLogger(name);
		log.setUseParentHandlers(false);
		log.setLevel(level);
		ConsoleHandler hdlr = new ConsoleHandler();
		hdlr.setFormatter(new ProjectLogFormat());
		log.addHandler(hdlr);
		return log;
	}
	
	public Logger generateLog(String name, Level level, Formatter format) {
		Logger log = Logger.getLogger(name);
		log.setUseParentHandlers(false);
		log.setLevel(level);
		ConsoleHandler hdlr = new ConsoleHandler();
		hdlr.setFormatter(new ProjectLogFormat());
		log.addHandler(hdlr);
		return log;
	}

}
