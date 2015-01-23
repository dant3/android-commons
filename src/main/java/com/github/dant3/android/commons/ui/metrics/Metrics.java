package com.github.dant3.android.commons.ui.metrics;

import android.content.Context;
import android.util.DisplayMetrics;
import com.github.dant3.android.commons.system.SystemServices;

public final class Metrics {
    private static final float MDPI_DENSITY = 160f;

    private Metrics() { throw new UnsupportedOperationException(); }

    public static int getDisplayDensity(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        SystemServices.getWindowManager(context).getDefaultDisplay().getMetrics(dm);
        return dm.densityDpi;
    }

    public static float getDisplayAspectRatio(Context context) {
        float actualDPI = getDisplayDensity(context);
        return actualDPI / MDPI_DENSITY;
    }
}
