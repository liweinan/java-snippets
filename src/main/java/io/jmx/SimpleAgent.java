package io.jmx;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 *
 * Using the following command to start the agent:
 * <pre>
 * {@code
 * java -Dcom.sun.management.jmxremote \
 * -Dcom.sun.management.jmxremote.port=1617 \
 * -Dcom.sun.management.jmxremote.authenticate=false \
 * -Dcom.sun.management.jmxremote.ssl=false io.jmx.SimpleAgent
 * }
 * </pre>
 */
public class SimpleAgent {

    private MBeanServer mbs = null;

    public SimpleAgent() {
        mbs = ManagementFactory.getPlatformMBeanServer();
        Hello helloBean = new Hello();
        ObjectName helloName = null;

        try {
            helloName = new ObjectName("FOO:name=HelloBean");
            mbs.registerMBean(helloBean, helloName);

            ObjectName helloNotificationName = new ObjectName("FOO:name=HelloBeanNotification");
            HelloNotification helloNotification = new HelloNotification();
            mbs.registerMBean(helloNotification, helloNotificationName);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static void waitForEnterPressed() {
        try {
            System.out.println("Press to continue...");
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String argv[]) {
        SimpleAgent agent = new SimpleAgent(); // call constructor
        System.out.println("SimpleAgent is running...");
        SimpleAgent.waitForEnterPressed();
    }
}
