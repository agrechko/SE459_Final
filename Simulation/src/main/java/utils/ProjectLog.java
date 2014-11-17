package utils;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectLog {

    Logger logger;
    FileHandler fhdlr;

    public ProjectLog() {
        logger = Logger.getLogger("main");
        setFileHandler();
        setup();
    }

    private void setFileHandler() {
        FileHandler hdlr = null;
        try {
            hdlr = new FileHandler("clean_sweep.log");
            hdlr.setFormatter(new ProjectLogFormat());
            logger.addHandler(hdlr);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException on log file handle: ", e);
        } catch (SecurityException e) {
            logger.log(Level.SEVERE, "SecurityException on log file handle: ",
                    e);
        }

    }

    public final void setup() {
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.FINE);
    }

    public final void addConsole() {
        ConsoleHandler chdlr = new ConsoleHandler();
        chdlr.setFormatter(new ProjectLogFormat());
        logger.addHandler(chdlr);
    }

}
