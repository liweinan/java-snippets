package net.bluedash.snippets.patterns.visitor;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public interface CarVisitor {
    void visit(CarPart part);
}
