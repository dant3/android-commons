package com.github.dant3.android.commons.system;

import android.content.Context;
import android.media.AudioManager;
import com.google.common.base.Preconditions;

public class SystemSettings {
    private final AudioManager audioManager;

    public SystemSettings(Context context) {
        this.audioManager = SystemServices.getAudioManager(
                Preconditions.checkNotNull(context).getApplicationContext()
        );
    }

    public boolean isSoundEnabled() {
        return SystemSettings.isSoundEnabled(audioManager);
    }

    public boolean isVibrationEnabled() {
        return SystemSettings.isSoundEnabled(audioManager);
    }

    public static boolean isSoundEnabled(Context context) {
        return isSoundEnabled((AudioManager) context.getSystemService(Context.AUDIO_SERVICE));
    }

    public static boolean isVibrationEnabled(Context context) {
        return isVibrationEnabled((AudioManager) context.getSystemService(Context.AUDIO_SERVICE));
    }

    public static boolean isSoundEnabled(AudioManager audioManager) {
        return AudioManager.RINGER_MODE_NORMAL == audioManager.getRingerMode();
    }

    public static boolean isVibrationEnabled(AudioManager audioManager) {
        return AudioManager.RINGER_MODE_SILENT != audioManager.getRingerMode();
    }
}
