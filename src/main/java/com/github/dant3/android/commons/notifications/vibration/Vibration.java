package com.github.dant3.android.commons.notifications.vibration;

public interface Vibration {
    void vibrate();
    void cancel();
    boolean isEnabled();
    boolean isRepeated();
}
