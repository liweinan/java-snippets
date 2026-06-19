package io.weli.pattern.bridge;

/**
 * 扩展抽象化（Refined Abstraction）：在基础遥控器上增加静音等高级功能。
 * <p>
 * 扩展遥控器行为时不必新增 TvRemote、RadioRemote 等类，
 * 仍复用同一套 {@link Device} 实现。
 * </p>
 */
public class AdvancedRemote extends RemoteControl {

    public AdvancedRemote(Device device) {
        super(device);
    }

    public void mute() {
        System.out.println("静音");
        device.setVolume(0);
    }
}
