package net.bluedash.snippets.lang;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public enum Drill {
    ATTENTION("Attention!"), EYES_RIGHT("Eyes right!") {
        @Override
        public String toString() {
            return this.command.toUpperCase();
        }
    }, EYES_LEFT("Eyes left!"), AT_EASE("At ease!");

    protected String command;

    Drill(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return command;
    }

    public static void main(String[] args) {
        System.out.println(ATTENTION);
        System.out.println(AT_EASE);
        System.out.println(EYES_RIGHT);
    }

}
