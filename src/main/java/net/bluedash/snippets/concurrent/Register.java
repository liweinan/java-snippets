package net.bluedash.snippets.concurrent;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public interface Register<T> {
    T read();
    void write(T v);
}
