package io.weli.pattern.adapter;

/**
 * 目标接口（Target）：客户端期望的统一播放方式。
 * <p>
 * 业务代码只依赖这个接口，调用 {@code play(String fileName)} 即可，
 * 不需要知道底层是 MP3 播放器还是老旧 WAV 组件。
 * </p>
 */
public interface MediaPlayer {

    void play(String fileName);
}
