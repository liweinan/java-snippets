package io.weli.pattern.adapter;

/**
 * 已有的 MP3 播放器，原生就实现了 {@link MediaPlayer}，无需适配。
 */
public class Mp3Player implements MediaPlayer {

    @Override
    public void play(String fileName) {
        System.out.println("MP3 播放器播放: " + fileName);
    }
}
