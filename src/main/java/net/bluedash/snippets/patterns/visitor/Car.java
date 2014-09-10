package net.bluedash.snippets.patterns.visitor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class Car extends DefaultCarPartImpl {
    private Set<CarPart> parts = new HashSet<CarPart>();

    public Car() {
        Wheels wheels = new Wheels();
        Engine engine = new Engine();
        parts.add(wheels);
        parts.add(engine);
    }

    void enter() {
        System.out.println("entering the car");
    }

    @Override
    public void accept(CarVisitor visitor) {
        visitor.visit(this);
        for (CarPart part : parts) {
            visitor.visit(part);
        }
    }
}
