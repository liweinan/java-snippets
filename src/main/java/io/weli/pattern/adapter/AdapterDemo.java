package io.weli.pattern.adapter;

/**
 * 适配器模式演示。
 * <p>
 * 角色对照：
 * <ul>
 *   <li>{@link MediaPlayer} — 目标接口，客户端依赖它</li>
 *   <li>{@link Mp3Player} — 已兼容的实现，直接使用</li>
 *   <li>{@link LegacyWavPlayer} — 被适配者，接口不兼容</li>
 *   <li>{@link WavPlayerAdapter} — 适配器，桥接两者</li>
 * </ul>
 * 客户端 {@code playMedia} 只认 {@link MediaPlayer}，
 * MP3 和 WAV 通过同一入口播放，差异被适配器隐藏。
 * </p>
 */
public class AdapterDemo {

    public static void main(String[] args) {
        // 原生支持的目标实现
        playMedia(new Mp3Player(), "song.mp3");

        // 遗留组件无法直接当作 MediaPlayer 使用，套上适配器即可
        playMedia(new WavPlayerAdapter(new LegacyWavPlayer()), "sound.wav");
    }

    /**
     * 客户端代码：只面向 Target 编程，不关心底层是新组件还是适配后的旧组件。
     */
    static void playMedia(MediaPlayer player, String fileName) {
        player.play(fileName);
    }
}
