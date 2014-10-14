package utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public final class ProjectLogFormat extends Formatter {
	
	StringBuilder sb = new StringBuilder();
	
	@Override
	public String format(LogRecord record) {
		return new java.util.Date() + " " + record.getLevel() + " " + record.getMessage() + "\r\n";
	}
	

}

