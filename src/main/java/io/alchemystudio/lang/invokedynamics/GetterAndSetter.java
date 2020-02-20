package io.alchemystudio.lang.invokedynamics;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

/**
 * Created by weinanli on 09/06/2017.
 */

class Point
{
    int x;
    int y;
}

public class GetterAndSetter {

    public static void main(String[] args) throws Throwable
    {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        Point point = new Point();

        // Set the x and y fields.

        MethodHandle handle = lookup.findSetter(Point.class, "x",
                int.class);
        handle.invoke(point, 15);

        handle = lookup.findSetter(Point.class, "y", int.class);
        handle.invoke(point, 30);

        handle = lookup.findGetter(Point.class, "x", int.class);
        int x = (int) handle.invoke(point);
        System.out.printf("x = %d%n", x);

        handle = lookup.findGetter(Point.class, "y", int.class);
        int y = (int) handle.invoke(point);
        System.out.printf("y = %d%n", y);
    }
}
