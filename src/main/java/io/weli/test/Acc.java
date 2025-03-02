package io.weli.test;

import java.math.BigDecimal;

public class Acc implements Account {


    public Acc(BigDecimal initialValue) {
//        balance = initialValue;
    }
    public String toString() {
        return balance.toString();
    }
    public static void main(String args[]) {
        Acc instance =
                new Acc(new BigDecimal(50.00));
        System.out.println(instance);
    }
}
