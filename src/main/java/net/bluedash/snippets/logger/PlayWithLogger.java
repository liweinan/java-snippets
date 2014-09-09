package net.bluedash.snippets.logger;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class PlayWithLogger {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public void doSomethingAndLog() {
        LOGGER.setLevel(Level.FINEST);
        LOGGER.severe("42severe");
        LOGGER.warning("42warning");
        LOGGER.info("42info");
        LOGGER.finest("42finest");


    }

    public static void main(String[] args) throws Exception {
        PlayWithLogger play = new PlayWithLogger();
        MyLogger.setup();
        play.doSomethingAndLog();

        // no output to console
        MyLogger.disableRootLogger();
        play.doSomethingAndLog();
    }
}
