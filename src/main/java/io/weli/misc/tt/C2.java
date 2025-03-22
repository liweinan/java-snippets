package io.weli.misc.tt;

public class C2 implements I2 {
    public static void main(String[] args) {
        System.out.print(I2.name + ", ");
        System.out.print(I2.s1 + ",");
        System.out.println(((I1) new C2()).name);

        int x = 2;
        x = ~(x << 1);
        System.out.println(x);
    }
}
