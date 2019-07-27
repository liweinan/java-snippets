package io.weli.types;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public interface ParamConverterProvider {
    public <T> ParamConverter<T> getConverter(Class<T> rawType,
                                              Type genericType,
                                              Annotation annotations[]);

}
