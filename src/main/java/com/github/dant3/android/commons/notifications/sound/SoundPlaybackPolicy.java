package com.github.dant3.android.commons.notifications.sound;

public interface SoundPlaybackPolicy {
    boolean isPlaybackAllowed(int streamType);
}
