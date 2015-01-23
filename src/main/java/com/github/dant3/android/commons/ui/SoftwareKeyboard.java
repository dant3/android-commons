package com.github.dant3.android.commons.ui;

import android.app.Activity;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.github.dant3.android.commons.collections.SparseArrays;
import com.github.dant3.android.commons.system.SystemServices;

public final class SoftwareKeyboard {
    private SoftwareKeyboard() {}

    public static void show(Activity activity, View viewToFocus) {
        InputMethodManager inputMethodManager = SystemServices.getInputMethodManager(activity);
        if (inputMethodManager != null) {
            viewToFocus.requestFocus();
            inputMethodManager.showSoftInput(viewToFocus, 0);
        }
    }

    public static void hide(Activity activity) {
        InputMethodManager inputMethodManager = SystemServices.getInputMethodManager(activity);
        final View currentFocusView = activity.getCurrentFocus();
        if (currentFocusView != null && inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    currentFocusView.getWindowToken(), 0 /*InputMethodManager.HIDE_NOT_ALWAYS*/);
        }
    }


    public abstract static class ActionListener implements TextView.OnEditorActionListener {
        private static final SparseIntArray expectedActions = SparseArrays.newIntArray(
                EditorInfo.IME_ACTION_UNSPECIFIED,
                EditorInfo.IME_ACTION_NONE,
                EditorInfo.IME_ACTION_GO,
                EditorInfo.IME_ACTION_SEARCH,
                EditorInfo.IME_ACTION_SEND,
                EditorInfo.IME_ACTION_NEXT,
                EditorInfo.IME_ACTION_DONE
        );

        @Override
        public boolean onEditorAction(TextView v, int actionID, KeyEvent event) {
            return expectedActions.indexOfValue(actionID) != -1 && onAction(v, actionID);
        }

        protected abstract boolean onAction(TextView textView, int actionID);
    }
}
