package io.weli.pattern.template;

/** 具体类（ConcreteClass）：实现茶特有的可变步骤。 */
public class TeaMaker extends BeverageMaker {

    @Override
    protected void brew() {
        System.out.println("2. 用沸水浸泡茶叶");
    }

    @Override
    protected void addCondiments() {
        System.out.println("4. 加柠檬");
    }
}
