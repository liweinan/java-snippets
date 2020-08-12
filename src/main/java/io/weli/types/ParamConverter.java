package io.weli.types;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public interface ParamConverter<T> {
    public T fromString(String value);
    public String toString(T value);
}
