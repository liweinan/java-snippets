package io.weli.pattern.bridge;

/**
 * 桥接模式演示。
 * <p>
 * 角色对照：
 * <ul>
 *   <li>{@link RemoteControl} / {@link AdvancedRemote} — 抽象化，定义客户端使用的控制接口</li>
 *   <li>{@link Device} — 实现者接口，定义设备端能力</li>
 *   <li>{@link Tv} / {@link Radio} — 具体实现者</li>
 * </ul>
 * 同一套遥控器逻辑可以控制不同设备；新增设备或遥控器时，两侧各自扩展，互不影响。
 * 与适配器模式的区别：桥接是设计阶段主动拆分维度；适配器是事后兼容旧接口。
 * </p>
 */
public class BridgeDemo {

    public static void main(String[] args) {
        System.out.println("--- 基础遥控器 + 电视 ---");
        RemoteControl tvRemote = new RemoteControl(new Tv());
        tvRemote.togglePower();
        tvRemote.volumeUp();

        System.out.println("--- 高级遥控器 + 收音机 ---");
        AdvancedRemote radioRemote = new AdvancedRemote(new Radio());
        radioRemote.togglePower();
        radioRemote.volumeUp();
        radioRemote.mute();
        radioRemote.togglePower();
    }
}
