package io.weli.pattern.strategy;

public class StrategyDemo {

    public static void main(String[] args) {
        System.out.println("--- 坏写法 ---");
        new BadPayService().pay("wechat");

        System.out.println("--- 策略模式：判断收拢到 PayContext ---");
        doPay("alipay");

        System.out.println("--- Map 注册表：工厂层也无 if/else ---");
        PayRegistry.getStrategy("bank").pay();
    }

    // 业务层零判断
    static void doPay(String type) {
        PayStrategy strategy = PayContext.getStrategy(type);
        strategy.pay();
    }
}
