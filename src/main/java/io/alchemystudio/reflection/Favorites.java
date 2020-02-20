package io.alchemystudio.reflection;

import java.util.HashMap;
import java.util.Map;

/**
 * Typesafe heterogeneous container
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class Favorites {
    private Map<Class<?>, Object> favorites = new HashMap<Class<?>, Object>();

    public <T> void putFavorite(Class<T> type, T instance) {
        if (type == null)
            throw new NullPointerException("key is null");
        favorites.put(type, instance);
    }

    public <T> T getFavorite(Class<T> type) {
        return type.cast(favorites.get(type));
    }
}
