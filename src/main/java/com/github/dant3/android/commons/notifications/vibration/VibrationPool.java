package com.github.dant3.android.commons.notifications.vibration;

import android.content.Context;
import com.github.dant3.android.commons.system.SystemServices;
import com.google.common.base.Preconditions;
import lombok.Getter;

public class VibrationPool {
    private final SystemServices systemServices;
    private final VibrationPolicy vibrationPolicy;

    public VibrationPool(Context context) {
        this(SystemServices.of(context));
    }

    public VibrationPool(Context context, VibrationPolicy policy) {
        this(SystemServices.of(context), Preconditions.checkNotNull(policy));
    }

    public VibrationPool(SystemServices services) {
        this(Preconditions.checkNotNull(services), new DefaultVibrationPolicy(services));
    }

    private VibrationPool(SystemServices services, VibrationPolicy vibrationPolicy) {
        this.systemServices = services;
        this.vibrationPolicy = vibrationPolicy;
    }

    public Vibration getVibration(long[] pattern, boolean repeated) {
        return new VibrationImpl(pattern, repeated);
    }

    private void vibrate(long[] pattern, boolean repeat) {
        int repeatForAndroid = repeat ? 0 : -1;
        systemServices.getVibrator().vibrate(pattern, repeatForAndroid);
    }

    private void stopVibration() {
        systemServices.getVibrator().cancel();
    }

    private boolean isVibrationEnabled() {
        return vibrationPolicy.isVibrationAllowed();
    }


    private class VibrationImpl implements Vibration {
        private final long[] pattern;
        @Getter private final boolean repeated;

        private VibrationImpl(long[] pattern, boolean repeated) {
            this.pattern = pattern.clone();
            this.repeated = repeated;
        }

        @Override
        public void vibrate() {
            VibrationPool.this.vibrate(pattern, repeated);
        }

        public void cancel() {
            stopVibration();
        }

        public boolean isEnabled() {
            return isVibrationEnabled();
        }
    }
}
