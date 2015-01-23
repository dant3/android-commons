package com.github.dant3.android.commons.ui;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class LooperExecutor implements Executor {
    private final Handler handler;
    private final Thread thread;

    public LooperExecutor(Looper looper) {
        handler = new Handler(looper);
        thread = handler.getLooper().getThread();
    }

    @Override
    public void execute(Runnable command) {
        if (Thread.currentThread() == this.thread) {
            command.run();
        } else {
            handler.post(command);
        }
    }

    public void executeSingle(Runnable runnable) {
        handler.removeCallbacks(runnable);
        execute(runnable);
    }

    public void executeLater(Runnable command) {
        handler.post(command);
    }

    public void executeLater(Runnable command, int time, TimeUnit timeUnit) {
        handler.postDelayed(command, timeUnit.toMillis(time));
    }

    public final Handler getHandler() {
        return handler;
    }

    public final Thread getThread() {
        return thread;
    }
}
