package com.github.dant3.android.commons.notifications.sound;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import com.github.dant3.android.commons.Android;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

public class Music implements Sound {
    protected final MediaPlayer mediaPlayer = new MediaPlayer();
    private final Context context;

    @Getter @Setter
    private boolean repeated;
    @Getter
    private boolean loaded;


    public static Supplier<Music> supplier(final Context context, final int streamType, final String soundAsset) {
        return Suppliers.memoize(new Supplier<Music>() {
            @Override
            public Music get() {
                return new Music(context, streamType, soundAsset);
            }
        });
    }

    public static Supplier<Music> supplier(final Context context, final int streamType, final int soundResource) {
        return Suppliers.memoize(new Supplier<Music>() {
            @Override
            public Music get() {
                return new Music(context, streamType, soundResource);
            }
        });
    }

    public Music(Context context, int streamType, String soundAsset) {
        this.context = context;
        mediaPlayer.setOnErrorListener(new ErrorLogger());
        mediaPlayer.setAudioStreamType(streamType);
        load(soundAsset);
    }

    public Music(Context context, int streamType, int soundResource) {
        this.context = context;
        mediaPlayer.setOnErrorListener(new ErrorLogger());
        mediaPlayer.setAudioStreamType(streamType);
        load(context, soundResource);
    }

    @Override
    public void play() {
        try {
            mediaPlayer.setLooping(repeated);
            mediaPlayer.start();
        } catch (IllegalStateException ex) {
            // noop
        }
    }

    private void load(String soundAsset) {
        loaded = false;
        try {
            AssetFileDescriptor fileDescriptor = context.getAssets().openFd(soundAsset);
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
                    fileDescriptor.getStartOffset(),
                    fileDescriptor.getLength());
            mediaPlayerReset();
            fileDescriptor.close();
            loaded = true;
        } catch (Exception e) {
            // noop
        }
    }

    private void load(Context context, int soundResource) {
        loaded = false;
        try {
            Uri soundResourceUri = Android.resourceUri(context, soundResource);
            mediaPlayer.setDataSource(context, soundResourceUri);
            mediaPlayerReset();
            loaded = true;
        } catch (IOException e) {
            // noop
        }
    }

    @Override
    public void stop() {
        if (mediaPlayer.isPlaying()) {
            try{
                mediaPlayer.stop();
                mediaPlayerReset();
            } catch (Exception e) {
                // noop
            }
        }
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private void mediaPlayerReset() throws IOException {
        mediaPlayer.prepare();
        // NOTE: it looks like a behaviour change in media player
        //       in Android 5: prepare now makes seekTo(0), and
        //       calling it again without actual playback in progress
        //       will produce error (-38, 0)
        if (Android.apiLevel() < 20) {
            mediaPlayer.seekTo(0);
        }
    }

    @Override
    public void close() throws IOException {
        mediaPlayer.stop();
        mediaPlayer.release();
    }


    private class ErrorLogger implements MediaPlayer.OnErrorListener {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            //Log.e("Music", "Playback error - w:{}, ex:{}", what, extra);
            return false;
        }
    }
}
