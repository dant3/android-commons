package com.github.dant3.android.commons.notifications.sound;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import com.google.common.base.Preconditions;
import lombok.Getter;

import java.io.IOException;

public class SoundBackend {
    private static final int SOUND_POOL_SOUND_QUALITY = 0;
    private static final int SOUND_POOL_MAX_STREAMS = 1;

    @Getter private final int streamType;
    private final Context context;
    private final AudioManager audioManager;
    private final android.media.SoundPool soundPool;
    private final SoundPlaybackPolicy playbackPolicy;

    public SoundBackend(Context context, AudioManager audioManager,
                        SoundPlaybackPolicy soundPlaybackPolicy, int streamType) {
        this.streamType = streamType;
        this.context = Preconditions.checkNotNull(context);
        this.audioManager = Preconditions.checkNotNull(audioManager);
        this.playbackPolicy = Preconditions.checkNotNull(soundPlaybackPolicy);
        this.soundPool = createSoundPool(streamType);
    }

    public float getStreamVolume() {
        float actualVolume = (float) audioManager.getStreamVolume(streamType);
        float maxVolume = (float) audioManager.getStreamMaxVolume(streamType);
        return actualVolume / maxVolume;
    }

    public boolean isSoundEnabled() {
        return playbackPolicy.isPlaybackAllowed(streamType);
    }

    public Sound loadSound(int soundResID, final long[] vibrationPattern, boolean repeat) {
        int soundID = loadSound(soundPool, context, soundResID);
        return SoundImplementation.of(this, soundID, vibrationPattern, repeat);
    }

    public Sound loadSound(String soundAsset, final long[] vibrationPattern, boolean repeat) {
        int soundID = loadSound(soundPool, context, soundAsset);
        return SoundImplementation.of(this, soundID, vibrationPattern, repeat);
    }

    private int playSound(int soundID, boolean repeat) {
        float volume = getStreamVolume();
        return soundPool.play(soundID, volume, volume, 1, repeatValue(repeat), 1f);
    }

    private void stopPlayback(int playbackID) {
        soundPool.stop(playbackID);
    }

    private static int repeatValue(boolean repeat) {
        return repeat ? -1 : 0;
    }

    private static int loadSound(android.media.SoundPool soundPool, Context context, int resourceID) {
        return soundPool.load(context, resourceID, 1);
    }

    private static int loadSound(android.media.SoundPool soundPool, Context context, String soundAsset) {
        AssetFileDescriptor soundFd = null;
        try {
            soundFd = context.getAssets().openFd(soundAsset);
            return soundPool.load(soundFd, 1);
        } catch (IOException e) {
            //log.error("Failed to load sounds: ", e);
            return -1;
        } finally {
            if (soundFd != null) {
                try {
                    soundFd.close();
                } catch (IOException ex) {
                    //log.error("Failed to close FD: ", ex);
                }
            }
        }
    }

    private void unloadSound(int soundID) {
        soundPool.unload(soundID);
    }


    private static android.media.SoundPool createSoundPool(int streamType) {
        return new android.media.SoundPool(SOUND_POOL_MAX_STREAMS, streamType, SOUND_POOL_SOUND_QUALITY);
    }


    private static class SoundImplementation implements Sound {
        private static final int NO_PLAYBACK = -1;
        private static final int NO_SOUND = -1;

        private final SoundBackend backend;
        private int soundID;
        private final boolean repeat;
        private int playbackID = NO_PLAYBACK;

        public static SoundImplementation of(SoundBackend backend, int soundID,
                                             long[] vibrationPattern, boolean repeat) {
            return new SoundImplementation(backend, soundID, repeat);
        }

        public SoundImplementation(SoundBackend backend, int soundID, boolean repeat) {
            this.backend = Preconditions.checkNotNull(backend);
            this.soundID = soundID;
            this.repeat = repeat;
        }

        @Override
        public void play() {
            if (isEnabled() && !isDisposed()) {
                this.playbackID = backend.playSound(soundID, repeat);
            }
        }

        @Override
        public void stop() {
            if (isPlaying()) {
                backend.stopPlayback(playbackID);
                playbackID = NO_PLAYBACK;
            }
        }

        @Override
        public boolean isPlaying() {
            return playbackID == NO_PLAYBACK;
        }

        @Override
        public boolean isEnabled() {
            return backend.isSoundEnabled();
        }

        @Override
        public boolean isRepeated() {
            return repeat;
        }

        @Override
        public void close() throws IOException {
            backend.unloadSound(soundID);
            soundID = NO_SOUND;
        }

        private boolean isDisposed() {
            return soundID == NO_SOUND;
        }
    }
}
