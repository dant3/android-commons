package com.github.dant3.android.commons.notifications.sound;

import android.content.Context;
import android.util.SparseArray;
import com.github.dant3.android.commons.system.SystemServices;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

public class SoundPool {
    private final Context context;
    private final SparseArray<SoundBackend> soundBackendPool = new SparseArray<SoundBackend>();
    private final SystemServices systemServices;
    private final SoundPlaybackPolicy soundPlaybackPolicy;

    public SoundPool(Context context) {
        this(context, null);
    }

    public SoundPool(Context context,
                     SoundPlaybackPolicy soundPlaybackPolicy) {
        this.context = Preconditions.checkNotNull(context);
        this.systemServices = SystemServices.of(this.context);
        this.soundPlaybackPolicy = defaultPolicyIfNull(soundPlaybackPolicy, systemServices);
    }

    public Sound loadSound(int streamType, String soundAsset) {
        return loadSound(streamType, soundAsset, new long[0], false);
    }

    public Sound loadSound(int streamType, String soundAsset, final long[] vibrationPattern) {
        return loadSound(streamType, soundAsset, vibrationPattern, false);
    }

    private Sound loadSound(int streamType, String soundAsset, final long[] vibrationPattern, boolean repeat) {
        return getBackend(streamType).loadSound(soundAsset, vibrationPattern, repeat);
    }

    public Sound loadSound(int streamType, int soundResID, final long[] vibrationPattern) {
        return loadSound(streamType, soundResID, vibrationPattern, false);
    }

    private Sound loadSound(int streamType, int soundResID, final long[] vibrationPattern, boolean repeat) {
        return getBackend(streamType).loadSound(soundResID, vibrationPattern, repeat);
    }

    public Supplier<Sound> getSound(final int streamType, final int soundResID) {
        return getSound(streamType, soundResID, new long[0]);
    }

    public Supplier<Sound> getSound(final int streamType, final String soundAsset, final long[] vibrationPattern) {
        return Suppliers.memoize(new Supplier<Sound>() {
            @Override
            public Sound get() {
                return loadSound(streamType, soundAsset, vibrationPattern);
            }
        });
    }

    public Supplier<Sound> getSound(final int streamType, final int soundResID, final long[] vibrationPattern) {
        return Suppliers.memoize(new Supplier<Sound>() {
            @Override
            public Sound get() {
                return loadSound(streamType, soundResID, vibrationPattern);
            }
        });
    }

    private SoundBackend getBackend(int streamType) {
        synchronized (soundBackendPool) {
            SoundBackend soundBackend = soundBackendPool.get(streamType);
            if (soundBackend == null) {
                soundBackend = createSoundBackend(streamType);
                soundBackendPool.put(streamType, soundBackend);
            }
            return soundBackend;
        }
    }

    public static SoundPlaybackPolicy defaultPlaybackPolicy(final SystemServices systemServices) {
        return new DefaultPlaybackPolicy(systemServices);
    }

    private static SoundPlaybackPolicy defaultPolicyIfNull(SoundPlaybackPolicy policy,
                                                           SystemServices services) {
        if (policy == null) {
            return defaultPlaybackPolicy(services);
        } else {
            return policy;
        }
    }

    private SoundBackend createSoundBackend(int streamType) {
        return new SoundBackend(context, systemServices.getAudioManager(), soundPlaybackPolicy, streamType);
    }
}
