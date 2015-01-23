package com.github.dant3.android.commons.notifications.sound;

import java.io.Closeable;

public interface Sound extends Closeable {
    void play();
    void stop();
    boolean isPlaying();
    boolean isEnabled();
    boolean isRepeated();
}
