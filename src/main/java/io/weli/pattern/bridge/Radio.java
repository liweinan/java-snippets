package io.weli.pattern.bridge;

/**
 * 具体实现者（Concrete Implementor）：收音机。
 */
public class Radio implements Device {

    private boolean enabled;
    private int volume = 20;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void enable() {
        enabled = true;
        System.out.println("收音机已开机");
    }

    @Override
    public void disable() {
        enabled = false;
        System.out.println("收音机已关机");
    }

    @Override
    public int getVolume() {
        return volume;
    }

    @Override
    public void setVolume(int percent) {
        volume = Math.clamp(percent, 0, 100);
        System.out.println("收音机音量设为 " + volume);
    }
}
