package io.weli.pattern.strategy;

public class Alipay implements PayStrategy {

    @Override
    public void pay() {
        System.out.println("支付宝支付");
    }
}
