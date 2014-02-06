package net.bluedash.snippets.algorithm;

import java.util.Stack;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class StackTriangle {

    private Stack<Integer> stack = new Stack<Integer>();

    public int calc(int n) {
        for (int i = 0; i < n; i++) {
            stack.push(i);
        }
        int answer = 0;
        for(int j = 0; j < n; j++) {
            answer += stack.pop();
        }
        return answer;
    }

    public static void main(String[] args) {
        StackTriangle st = new StackTriangle();
        System.out.println(st.calc(3));
    }
}
