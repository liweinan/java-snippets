package net.bluedash.snippets.lang;

import java.util.Arrays;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public enum Priority {
    ONE(1) {
        public String toString() {
            return "LOW";
        }
    }, TWO(2),
    THREE(3) {
        public String toString() {
            return "NORMAL";
        }
    },
    FOUR(4),
    FIVE(5) {
        public String toString() {
            return "HIGH";
        }
    };

    private int pVal;

    Priority(int pVal) {
        this.pVal = pVal;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(Priority.values()));

    }
}
