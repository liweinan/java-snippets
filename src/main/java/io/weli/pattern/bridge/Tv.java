package io.weli.pattern.bridge;

/**
 * 具体实现者（Concrete Implementor）：电视。
 */
public class Tv implements Device {

    private boolean enabled;
    private int volume = 30;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void enable() {
        enabled = true;
        System.out.println("电视已开机");
    }

    @Override
    public void disable() {
        enabled = false;
        System.out.println("电视已关机");
    }

    @Override
    public int getVolume() {
        return volume;
    }

    @Override
    public void setVolume(int percent) {
        volume = Math.clamp(percent, 0, 100);
        System.out.println("电视音量设为 " + volume);
    }
}
