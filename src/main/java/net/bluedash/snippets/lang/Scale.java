package net.bluedash.snippets.lang;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public enum Scale {
    GOOD('C') {
        @Override
        public char getGrade() {
            return 0;
        }
    }, BETTER('B') {
        @Override
        public char getGrade() {
            return 1;
        }
    }, BEST('A') {
        @Override
        public char getGrade() {
            return 2;
        }
    };

    private char grade;

    Scale(char grade) {
        this.grade = grade;
    }

    abstract public char getGrade();

    public static void main(String[] args) {
        System.out.println(GOOD.getGrade());
    }

}
