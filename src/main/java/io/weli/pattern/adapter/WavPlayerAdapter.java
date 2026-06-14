package io.weli.pattern.adapter;

/**
 * 适配器（Adapter）：把 {@link LegacyWavPlayer} 包装成 {@link MediaPlayer}。
 * <p>
 * 设计思想（对象适配器）：
 * <ul>
 *   <li>实现目标接口 {@link MediaPlayer}，让客户端无感知地调用</li>
 *   <li>内部持有被适配者 {@link LegacyWavPlayer}，在 {@code play} 里做参数/调用转换</li>
 *   <li>不修改遗留代码，也不改客户端，中间加一层即可复用</li>
 * </ul>
 * 典型场景：对接第三方 SDK、兼容旧系统接口、统一多种数据格式。
 * </p>
 */
public class WavPlayerAdapter implements MediaPlayer {

    /** 被适配的老组件，接口与 Target 不一致 */
    private final LegacyWavPlayer legacyWavPlayer;

    public WavPlayerAdapter(LegacyWavPlayer legacyWavPlayer) {
        this.legacyWavPlayer = legacyWavPlayer;
    }

    /**
     * 对外暴露统一接口；对内翻译成 legacy 组件能理解的调用。
     */
    @Override
    public void play(String fileName) {
        legacyWavPlayer.playWav(fileName);
    }
}
