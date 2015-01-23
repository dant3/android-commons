package com.github.dant3.android.commons.notifications.vibration;

import android.content.Context;
import com.github.dant3.android.commons.system.SystemServices;
import com.github.dant3.android.commons.system.SystemSettings;
import com.google.common.base.Preconditions;

public class DefaultVibrationPolicy implements VibrationPolicy {
    private final SystemServices systemServices;

    public DefaultVibrationPolicy(Context context) {
        this(SystemServices.of(context.getApplicationContext()));
    }

    public DefaultVibrationPolicy(SystemServices systemServices) {
        this.systemServices = Preconditions.checkNotNull(systemServices);
    }

    @Override
    public boolean isVibrationAllowed() {
        return SystemSettings.isVibrationEnabled(systemServices.getAudioManager());
    }
}
