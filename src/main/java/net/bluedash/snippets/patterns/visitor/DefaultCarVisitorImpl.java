package net.bluedash.snippets.patterns.visitor;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class DefaultCarVisitorImpl implements CarVisitor {
    @Override
    public void visit(CarPart part) {
        if (part.getClass() == Wheels.class) {
            ((Wheels) part).rotate();
        } else if (part.getClass() == Engine.class) {
            ((Engine) part).roar();
        } else {
            ((Car) part).enter();
        }
    }
}
