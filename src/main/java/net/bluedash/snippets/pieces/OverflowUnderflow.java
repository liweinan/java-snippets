package net.bluedash.snippets.pieces;

/**
 * @author Weinan Li
 * @date 08 05 2012
 */
public class OverflowUnderflow {
    public static void main(String args[]) {

        //roll over effect to lower limit in overflow
        int overflowExample = 2147483647;
        System.out.println("Overflow: " + (overflowExample + 1));

        //roll over effect to upper limit in underflow
        int underflowExample = -2147483648;
        System.out.println("Underflow: " + (underflowExample - 1));

        byte b = 127;
        // following line uncommented results in compilation error
        // constants are checked at compile time for size
        // b = b*b;

        double d = 1e308;
        System.out.println(d + "*10= " + d * 10);
        //gradual underflow
        d = 1e-305 * Math.PI;
        System.out.print("gradual underflow: " + d + "\n      ");
        for (int i = 0; i < 4; i++)
            System.out.print(" " + (d /= 100000));

    }

}
