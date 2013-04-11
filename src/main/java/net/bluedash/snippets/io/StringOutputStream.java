package net.bluedash.snippets.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class StringOutputStream extends OutputStream {

    private StringBuilder string = new StringBuilder();

    @Override
    public void write(int b) throws IOException {
        this.string.append((char) b);
    }

    public String toString() {
        return this.string.toString();
    }

    public StringOutputStream renew() {
        return new StringOutputStream();
    }


}
