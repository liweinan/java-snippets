package io.weli.pattern.strategy;

/**
 * 工厂/上下文：判断只集中在这里，业务层不再写分支。
 */
public class PayContext {

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
