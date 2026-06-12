package io.weli.pattern.strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Map 注册表：工厂层也可以去掉 if/else，新增策略只需 put 一行。
 */
public class PayRegistry {

    private static final Map<String, PayStrategy> STRATEGY_MAP = new HashMap<>();

    static {
        STRATEGY_MAP.put("wechat", new WechatPay());
        STRATEGY_MAP.put("alipay", new Alipay());
        STRATEGY_MAP.put("bank", new BankPay());
    }

    public static PayStrategy getStrategy(String type) {
        PayStrategy strategy = STRATEGY_MAP.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("不支持的支付类型: " + type);
        }
        return strategy;
    }
}
