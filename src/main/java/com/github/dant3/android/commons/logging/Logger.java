package com.github.dant3.android.commons.logging;

import android.util.Log;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(suppressConstructorProperties = true)
public class Logger {
    @Getter private final String tag;

    public Logger log(LogLevel level, String message) {
        Log.println(level.priority, tag, message);
        return this;
    }

    public Logger verbose(String message) {
        Log.v(tag, message);
        return this;
    }

    public Logger info(String message) {
        Log.i(tag, message);
        return this;
    }

    public Logger debug(String message) {
        Log.d(tag, message);
        return this;
    }

    public Logger warning(String message) {
        Log.w(tag, message);
        return this;
    }

    public Logger error(String message) {
        Log.e(tag, message);
        return this;
    }
}
