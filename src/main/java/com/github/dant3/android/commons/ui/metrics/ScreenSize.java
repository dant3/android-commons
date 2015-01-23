package com.github.dant3.android.commons.ui.metrics;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import lombok.Value;

@Value(staticConstructor = "of")
public final class ScreenSize {
    private final float value;
    private final SizeUnit unit;

    public int apply(Context context) {
        return apply(context.getResources().getDisplayMetrics());
    }

    public float applyPrecisely(Context context) {
        return applyPrecisely(context.getResources().getDisplayMetrics());
    }

    public int apply(DisplayMetrics metrics) {
        return  (int) applyPrecisely(metrics);
    }

    public float applyPrecisely(DisplayMetrics metrics) {
        return applyPrecisely(value, unit, metrics);
    }

    public static int apply(float value, SizeUnit unit, DisplayMetrics metrics) {
        return (int) applyPrecisely(value, unit, metrics);
    }

    public static float applyPrecisely(float value, SizeUnit unit, DisplayMetrics metrics) {
        return TypedValue.applyDimension(unit.index, value, metrics);
    }
}
