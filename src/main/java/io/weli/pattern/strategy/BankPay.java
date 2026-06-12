package io.weli.pattern.strategy;

public class BankPay implements PayStrategy {

    @Override
    public void pay() {
        System.out.println("银行卡支付");
    }
}
