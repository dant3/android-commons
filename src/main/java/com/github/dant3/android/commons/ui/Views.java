package com.github.dant3.android.commons.ui;
import android.view.View;

public final class Views {
    private Views() {}

    public static boolean isVisible(View view) {
        return view != null && view.getVisibility() == View.VISIBLE;
    }

    public static boolean isHidden(View view) {
        return view != null && view.getVisibility() == View.INVISIBLE;
    }

    public static boolean isGone(View view) {
        return view != null && view.getVisibility() == View.GONE;
    }

    public static void setVisible(View view, boolean visible) {
        if (view != null) {
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    public static void setHidden(View view, boolean hidden) {
        if (view != null) {
            view.setVisibility(hidden ? View.INVISIBLE : View.VISIBLE);
        }
    }
}
