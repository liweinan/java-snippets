package io.weli.jmx;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class HelloNotification extends NotificationBroadcasterSupport implements HelloNotificationMBean {
    @Override
    public void sendNotification() {
        Notification notification = new Notification("myType", this, 1, System.currentTimeMillis(), "abcdefg");
        sendNotification(notification);
    }
}
