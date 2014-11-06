package utils;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.text.SimpleDateFormat;

public final class ProjectLogFormat extends Formatter {
	
	StringBuilder sb = new StringBuilder();
	
	@Override
	public String format(LogRecord record) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSXXX");
		return dateformat.format(new java.util.Date()) + " " 
				               + record.getSourceClassName() + " "
				               + record.getSourceMethodName() + " "
		                       + record.getLevel() + " " 
				               + record.getMessage() + "\r\n";
	}
	

}

