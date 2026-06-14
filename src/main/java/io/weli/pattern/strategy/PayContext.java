package io.weli.pattern.strategy;

/**
 * 上下文（Context）：负责根据条件选出合适的策略。
 * <p>
 * 设计思想：
 * <ul>
 *   <li>「选哪个策略」的判断集中在这里，业务层不再写 if/else</li>
 *   <li>业务层拿到 {@link PayStrategy} 后直接调用 {@code pay()}，与具体实现解耦</li>
 * </ul>
 * 生产环境里，这里也可以换成 Map 注册表或 Spring 注入，思路相同。
 * </p>
 */
public class PayContext {

    /**
     * 工厂方法：根据类型字符串创建对应策略对象。
     */
    public static PayStrategy getStrategy(String type) {
        if ("wechat".equals(type)) {
            return new WechatPay();
        } else if ("alipay".equals(type)) {
            return new Alipay();
        } else if ("bank".equals(type)) {
            return new BankPay();
        }
        throw new IllegalArgumentException("不支持的支付类型: " + type);
    }
}
