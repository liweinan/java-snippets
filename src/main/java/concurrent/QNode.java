package concurrent;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
class QNode {
    boolean locked = false;
    QNode next = null;
}
