package io.weli.pattern.strategy;

/**
 * 具体策略（Concrete Strategy）：支付宝支付。
 * <p>
 * 每一种支付方式单独成类，新增/修改一种方式时只需动这一处，
 * 不用去改业务代码里的大段 if/else。
 * </p>
 */
public class Alipay implements PayStrategy {

    @Override
    public void pay() {
        System.out.println("支付宝支付");
    }
}
