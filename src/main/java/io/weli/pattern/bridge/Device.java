package io.weli.pattern.bridge;

/**
 * 实现者接口（Implementor）：设备端的抽象 API。
 * <p>
 * 桥接模式把「遥控器怎么操作」与「电视/收音机怎么响应」拆开，
 * 两者通过组合而非继承关联，可以独立扩展。
 * </p>
 */
public interface Device {

    boolean isEnabled();

    void enable();

    void disable();

    int getVolume();

    void setVolume(int percent);
}
