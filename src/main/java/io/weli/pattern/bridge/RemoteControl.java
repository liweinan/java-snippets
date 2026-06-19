package io.weli.pattern.bridge;

/**
 * 抽象化（Abstraction）：遥控器只关心「怎么操作设备」，不关心底层是电视还是收音机。
 * <p>
 * 通过持有 {@link Device} 引用，把控制逻辑与设备实现桥接在一起。
 * </p>
 */
public class RemoteControl {

    protected final Device device;

    public RemoteControl(Device device) {
        this.device = device;
    }

    public void togglePower() {
        if (device.isEnabled()) {
            device.disable();
        } else {
            device.enable();
        }
    }

    public void volumeDown() {
        device.setVolume(device.getVolume() - 10);
    }

    public void volumeUp() {
        device.setVolume(device.getVolume() + 10);
    }
}
