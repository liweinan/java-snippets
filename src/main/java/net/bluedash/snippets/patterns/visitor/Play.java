package net.bluedash.snippets.patterns.visitor;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class Play {

    public static final void main(String[] args) {

        Car car = new Car();

        // In this way, a single algorithm can be written for traversing a graph of elements,
        // and many different kinds of operations can be performed during that traversal by
        // supplying different kinds of visitors to interact with the elements based on the
        // dynamic types of both the elements and the visitors.
        CarVisitor visitor = new DefaultCarVisitorImpl();
        car.accept(visitor); // object can 'accept' visitor.

        CarVisitor alternateVisitor = new AlternateCarVisitorImpl();
        car.accept(alternateVisitor);
    }
}
