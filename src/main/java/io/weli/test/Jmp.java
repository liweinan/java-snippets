package io.weli.test;

public class Jmp {
    public static int calculateSum(int n, int jumpValue) {
        if(n < 2) return 0;
        if (n == jumpValue) return jumpValue * calculateSum(jumpValue - 1, jumpValue);
        return n + calculateSum(n - 2, jumpValue);
    }

    public static void main(String [] args) throws Exception{
        System.out.println(calculateSum(10, 4));

        String chars = " a b a ";
        String newChars = chars.strip().repeat(4);
        System.out.println(newChars);
        String splitCharacters [] = newChars.split("[ab]");
        int blank = 0;
        for(String t : splitCharacters){
            if(t.isBlank()){
                System.out.println("-> " + t);
                blank ++;
            }
        }
        System.out.println(blank);
    }
}
