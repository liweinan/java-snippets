package io.weli.pattern.template;

/** 具体类（ConcreteClass）：实现咖啡特有的可变步骤。 */
public class CoffeeMaker extends BeverageMaker {

    @Override
    protected void brew() {
        System.out.println("2. 用沸水冲泡咖啡");
    }

    @Override
    protected void addCondiments() {
        System.out.println("4. 加糖和牛奶");
    }
}
