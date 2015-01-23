package com.github.dant3.android.commons.ui;

import android.os.Looper;

public class Gui {
    private static final LooperExecutor executor = new LooperExecutor(Looper.getMainLooper());

    public static Thread getThread() {
        return Looper.getMainLooper().getThread();
    }

    public static LooperExecutor getExecutor() {
        return executor;
    }
}
