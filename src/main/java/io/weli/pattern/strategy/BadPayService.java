package io.weli.pattern.strategy;

/**
 * 反面示例：没有使用策略模式时的典型写法。
 * <p>
 * 问题：
 * <ul>
 *   <li>分支和业务逻辑耦合在一起，类会越来越臃肿</li>
 *   <li>新增一种支付方式必须修改本类，违反开闭原则</li>
 *   <li>每种支付方式的细节无法独立测试和复用</li>
 * </ul>
 * 策略模式的目标，就是把下面这些 if/else 拆出去。
 * </p>
 */
public class BadPayService {

    public void pay(String type) {
        if ("wechat".equals(type)) {
            System.out.println("微信支付");
        } else if ("alipay".equals(type)) {
            System.out.println("支付宝支付");
        } else if ("bank".equals(type)) {
            System.out.println("银行卡支付");
        }
    }
}
