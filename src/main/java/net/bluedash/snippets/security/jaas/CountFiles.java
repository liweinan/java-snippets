package net.bluedash.snippets.security.jaas;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import java.security.PrivilegedAction;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class CountFiles {
    static class NullCallbackHandler implements CallbackHandler {
        public void handle(Callback[] cb) {
            throw new IllegalArgumentException("Not implemented yet");
        }
    }

    static class CountFilesAction implements PrivilegedAction {
        public Object run() {
            return null;
        }
    }

    static LoginContext lc = null;

    public static void main(String[] args) throws Exception {
        lc = new LoginContext("CountFiles", new NullCallbackHandler());
        lc.login();

        Object o = Subject.doAs(lc.getSubject(), new CountFilesAction());
        System.out.println("User " + lc.getSubject() + " found " + o + " files.");

    }
}
