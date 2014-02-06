package net.bluedash.snippets.instrumentation;

import java.lang.instrument.Instrumentation;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class Agent {
    private static volatile Instrumentation instrumentation;

    public static void premain(String args, Instrumentation instr) {
        instrumentation = instr;
    }

    public static Instrumentation getInstrumentation() {
        if (instrumentation == null) {
            throw new IllegalStateException("instrumentation == not");
        }
        return instrumentation;
    }
}
