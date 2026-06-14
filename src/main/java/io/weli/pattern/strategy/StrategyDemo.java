package io.weli.pattern.strategy;

/**
 * 策略模式演示。
 * <p>
 * 角色对照：
 * <ul>
 *   <li>{@link PayStrategy} — 策略接口，定义可变行为</li>
 *   <li>{@link Alipay} / {@link WechatPay} / {@link BankPay} — 具体策略</li>
 *   <li>{@link PayContext} — 上下文，负责选择策略</li>
 *   <li>调用方（本类 {@code doPay}）— 只依赖接口，运行时切换算法</li>
 * </ul>
 * </p>
 */
public class StrategyDemo {

    public static void main(String[] args) {
        System.out.println("--- 坏写法：分支散落在业务里 ---");
        new BadPayService().pay("wechat");

        System.out.println("--- 策略模式：行为封装成可替换的策略 ---");
        doPay("alipay");
        doPay("bank");
    }

    /**
     * 业务层示例：没有任何 if/else，只面向 {@link PayStrategy} 编程。
     * 需要换支付方式时，改 {@link PayContext} 或传入不同策略即可。
     */
    static void doPay(String type) {
        PayStrategy strategy = PayContext.getStrategy(type);
        strategy.pay();
    }
}
