package net.bluedash.snippets.patterns.visitor;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class AlternateCarVisitorImpl implements CarVisitor {
    @Override
    public void visit(CarPart part) {
        System.out.println(part);
    }
}
