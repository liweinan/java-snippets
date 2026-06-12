package io.weli.pattern.strategy;

public class WechatPay implements PayStrategy {

    @Override
    public void pay() {
        System.out.println("微信支付");
    }
}
