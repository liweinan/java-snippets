package net.bluedash.snippets.logger;

import java.io.IOException;
import java.util.logging.*;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class MyLogger {
    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    static private FileHandler fileHTML;
    static private Formatter formatterHTML;

    static public Logger setup() throws IOException {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        logger.setLevel(Level.INFO);
        fileTxt = new FileHandler("/tmp/my_log.txt");
        fileHTML = new FileHandler("/tmp/my_log.html");

        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);

        formatterHTML = new MyHtmlFormatter();
        fileHTML.setFormatter(formatterHTML);
        logger.addHandler(fileHTML);
        return logger;
    }

    public static void disableRootLogger() {
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        for (int i = 0; i < handlers.length; i++) {
            rootLogger.removeHandler(handlers[i]);
        }
    }

    public static void main(String[] args) {
        System.out.println(Logger.getLogger(Logger.GLOBAL_LOGGER_NAME));
        Logger rootLogger = Logger.getLogger("");
        System.out.println(rootLogger);
        Handler[] handlers = rootLogger.getHandlers();
        for (Handler handler : handlers) {
            System.out.println(handler);
        }
    }
}
