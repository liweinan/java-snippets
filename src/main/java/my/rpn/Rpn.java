package my.rpn;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class Rpn {

    public static int TOK_NUM = -2;
    public static int TOK_OP = -3;


    static char Op;
    static String NumVal = "";              // Filled in if TOK_NUM
    public static LinkedList<String> Args = new LinkedList<String>();

    public static String Input = "";
    static int Curr = 0;

    static char LastChar = ' ';

    /// gettok - Return the next token from standard input.
    static int gettok() throws LexerException {
        // Skip any whitespace.
        while (LastChar == ' ' && Curr < Input.length()) {
            LastChar = Input.charAt(Curr++);
        }

        if (Character.isDigit(LastChar)) {   // Number: [0-9]+
            while (Character.isDigit(LastChar) && Curr < Input.length()) {
                NumVal += LastChar - '0';
                LastChar = Input.charAt(Curr++);
            }

            if (Character.isDigit(LastChar) && Curr == Input.length()) {
                NumVal += LastChar - '0';
                Curr++;
            }

            Args.add(NumVal);
            NumVal = "";
            return TOK_NUM;
        }

        if (LastChar == '+' || LastChar == '-' || LastChar == '*' || LastChar == '/') {
//            if (Curr == 0 || Curr == Input.length())
//                throw new LexerException();

            Op = (char) LastChar;
            Args.add(String.valueOf(Op));

            if (Curr < Input.length())
                LastChar = Input.charAt(Curr++);
            else
                Curr++;

            return TOK_OP;
        }

        // Otherwise, just return the character as its ascii value.
        int ThisChar = LastChar;
        LastChar = Input.charAt(Curr++);
        return ThisChar;
    }

    static int CurTok;

    public static int getNextToken() throws LexerException {
        CurTok = gettok();
        return CurTok;
    }

    public static void main(String[] args) throws LexerException, ParserException {

        Scanner scanner = new Scanner(System.in);
        Input = scanner.nextLine();

        while (Curr <= Input.length()) {
            getNextToken();
            printToken();
        }

        //calc();
    }

    static boolean calcStart = false;

    public static void calc() throws ParserException {
        int result = 0;
        String op1 = "";

        while (!Args.isEmpty()) {
            if (calcStart == false) {
                op1 = Args.remove();
            } else {
                op1 = Integer.valueOf(result).toString();
            }

            calcStart = true;

            String op2 = Args.remove();
            String op3 = Args.remove();
            result += Integer.valueOf(_calc(op1, op2, op3));
        }

        System.out.printf("result: %d\n", result);
    }


    private static String _calc(String op1, String op2, String op3) throws ParserException {
        if (isdigit(op1) && isdigit(op2) && isdigit(op3)) {
            op2 = _calc(op2, op3, Args.remove());
            op3 = Args.remove();
        }

        int _op1 = Integer.valueOf(op1);
        int _op2 = Integer.valueOf(op2);

        if (op3 == "*") {
            return Integer.valueOf(_op1 * _op2).toString();
        } else {
            throw new ParserException();
        }


    }

    private static boolean isdigit(String op1) {
        return Character.isDigit(op1.charAt(0));
    }

    static void printToken() {
        if (CurTok == TOK_OP) {
            System.out.printf("<TOK_OP> %s\n", Args.get(Args.size() - 1));
        } else if (CurTok == TOK_NUM) {
            System.out.printf("<TOK_NUM> %s\n", Args.get(Args.size() - 1));
        } else {
            System.out.printf("<Unknown> %d\n", CurTok);
        }
    }
}
