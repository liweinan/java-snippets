package io.weli.pattern.adapter;

/**
 * 被适配者（Adaptee）：第三方/历史遗留组件。
 * <p>
 * 它能播放 WAV，但接口是 {@code playWav(String path)}，
 * 与客户端期望的 {@link MediaPlayer#play(String)} 不兼容，
 * 因此不能直接替换进现有业务代码。
 * </p>
 */
public class LegacyWavPlayer {

    public void playWav(String path) {
        System.out.println("老旧 WAV 组件播放: " + path);
    }
}
