package io.weli.types;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
//@Provider
public class ColorConverterProvider {

    private final ColorConverter converter = new ColorConverter();

    public <T> ColorConverter getConverter(Class<T> rawType,
                                           Type genericType,
                                           Annotation[] annotations) {
        if (!rawType.equals(Color.class)) return null;
        return converter;
    }
}
