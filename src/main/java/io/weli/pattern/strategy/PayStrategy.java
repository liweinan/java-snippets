package io.weli.pattern.strategy;

/**
 * 策略接口（Strategy）
 * <p>
 * 设计思想：把「会变化的行为」抽象成统一接口，让多种算法可以互相替换。
 * 调用方只依赖这个接口，而不关心具体是微信、支付宝还是银行卡。
 * </p>
 */
public interface PayStrategy {

    /**
     * 执行支付；不同策略类各自实现具体逻辑。
     */
    void pay();
}
