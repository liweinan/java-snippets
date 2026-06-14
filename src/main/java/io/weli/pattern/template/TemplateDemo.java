package io.weli.pattern.template;

/**
 * 模板方法模式演示。
 * <p>
 * 角色对照：
 * <ul>
 *   <li>{@link BeverageMaker} — 抽象类，定义模板方法 {@link BeverageMaker#make()}</li>
 *   <li>{@link CoffeeMaker} / {@link TeaMaker} — 具体类，填充可变步骤</li>
 * </ul>
 * 客户端只调用 {@code make()}，流程一致，细节由子类决定。
 * 典型场景：JdbcTemplate 的 execute 流程、Servlet 的 service 生命周期、JUnit 的 test 方法框架。
 * </p>
 */
public class TemplateDemo {

    public static void main(String[] args) {
        System.out.println("--- 制作咖啡 ---");
        new CoffeeMaker().make();

        System.out.println("--- 制作茶 ---");
        new TeaMaker().make();
    }
}
