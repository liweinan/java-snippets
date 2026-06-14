package io.weli.pattern.template;

/**
 * 抽象类（AbstractClass）：定义算法骨架，把固定步骤写死在模板方法里。
 * <p>
 * 设计思想：
 * <ul>
 *   <li>{@link #make()} 是模板方法（Template Method），声明 {@code final}，子类不能改流程顺序</li>
 *   <li>各子类相同的步骤（{@link #boilWater}、{@link #pourInCup}）在父类实现，避免重复</li>
 *   <li>会变化的步骤（{@link #brew}、{@link #addCondiments}）留给子类实现</li>
 * </ul>
 * 与策略模式的区别：模板方法用继承固定「流程」，策略模式用组合替换「整段算法」。
 * </p>
 */
public abstract class BeverageMaker {

    /**
     * 模板方法：冲泡饮品的固定流程，子类不能覆盖。
     */
    public final void make() {
        boilWater();
        brew();
        pourInCup();
        addCondiments();
    }

    /** 固定步骤：烧水 */
    private void boilWater() {
        System.out.println("1. 把水烧开");
    }

    /** 固定步骤：倒入杯中 */
    private void pourInCup() {
        System.out.println("3. 倒入杯中");
    }

    /** 可变步骤：冲泡，由子类决定是泡咖啡还是泡茶 */
    protected abstract void brew();

    /** 可变步骤：加调料，由子类决定加奶还是加柠檬 */
    protected abstract void addCondiments();
}
