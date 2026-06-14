package io.weli.pattern.strategy;

/** 具体策略：微信支付。 */
public class WechatPay implements PayStrategy {

    @Override
    public void pay() {
        System.out.println("微信支付");
    }
}
