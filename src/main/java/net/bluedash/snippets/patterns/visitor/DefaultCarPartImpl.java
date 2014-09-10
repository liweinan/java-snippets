package net.bluedash.snippets.patterns.visitor;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public abstract class DefaultCarPartImpl implements CarPart {
    @Override
    public void accept(CarVisitor visitor) {
        visitor.visit(this);
    }
}
