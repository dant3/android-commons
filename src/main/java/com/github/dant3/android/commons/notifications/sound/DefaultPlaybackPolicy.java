package com.github.dant3.android.commons.notifications.sound;

import android.content.Context;
import com.github.dant3.android.commons.system.SystemServices;
import com.github.dant3.android.commons.system.SystemSettings;
import com.google.common.base.Preconditions;

public class DefaultPlaybackPolicy implements SoundPlaybackPolicy {
    private final SystemServices systemServices;

    public DefaultPlaybackPolicy(Context context) {
        this(SystemServices.of(context.getApplicationContext()));
    }

    public DefaultPlaybackPolicy(SystemServices systemServices) {
        this.systemServices = Preconditions.checkNotNull(systemServices);
    }

    @Override
    public boolean isPlaybackAllowed(int streamType) {
        return SystemSettings.isSoundEnabled(systemServices.getAudioManager());
    }
}
