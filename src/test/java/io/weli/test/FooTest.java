package io.weli.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({MyTestFilter.class})
public class FooTest {
    @Test
    public void testFoo(){
        System.out.println("testFoo");
    }
}
