package io.weli.pattern.strategy;

/**
 * 坏写法：分支和业务逻辑混在一起，新增支付方式必须改这里。
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
