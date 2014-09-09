package net.bluedash.snippets.logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class MyHtmlFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        StringBuffer buffer = new StringBuffer(1000);
        buffer.append("<tr>\n");

        // colorize any levels >= WARNING in red
        if (record.getLevel().intValue() >= Level.WARNING.intValue()) {
            buffer.append("\t<td style=\"color:red\">");
            buffer.append("<b>");
            buffer.append(record.getLevel());
            buffer.append("</b>");
        } else {
            buffer.append("\t<td>");
            buffer.append(record.getLevel());
        }

        buffer.append("</td>\n");
        buffer.append("\t<td>");
        buffer.append(calcDate(record.getMillis()));
        buffer.append("</td>\n");
        buffer.append("\t<td>");
        buffer.append(formatMessage(record));
        buffer.append("</td>\n");
        buffer.append("</tr>\n");

        return buffer.toString();
    }

    private String calcDate(long millisecs) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultDate = new Date(millisecs);
        return dateFormat.format(resultDate);
    }
}
