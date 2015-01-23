package com.github.dant3.android.commons.system;

import android.content.Context;
import android.media.AudioManager;
import android.os.Vibrator;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import com.google.common.base.Preconditions;

public final class SystemServices {
    private final Context context;

    public static SystemServices of(Context context) {
        return new SystemServices(context);
    }

    private SystemServices(Context context) {
        this.context = Preconditions.checkNotNull(context);
    }

    public AudioManager getAudioManager() {
        return SystemServices.getAudioManager(context);
    }

    public InputMethodManager getInputMethodManager() {
        return SystemServices.getInputMethodManager(context);
    }

    public Vibrator getVibrator() {
        return SystemServices.getVibrator(context);
    }

    public WindowManager getWindowManager() { return SystemServices.getWindowManager(context); }

    public static AudioManager getAudioManager(Context context) {
        return (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public static InputMethodManager getInputMethodManager(Context context) {
        return  (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public static Vibrator getVibrator(Context context) {
        return (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public static WindowManager getWindowManager(Context context) {
        return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }
}
