package io.alchemystudio.my.log.handler;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class MyLogHandler extends Handler {
    @Override
    public void publish(LogRecord record) {
        System.out.println("+++ publish record: " + record.getLoggerName());
        System.out.println("+++ publish record: " + record.getMessage());
    }

    @Override
    public void flush() {
        System.out.println("+++ flush");
    }

    @Override
    public void close() throws SecurityException {
        System.out.println("+++ close");
    }
}
