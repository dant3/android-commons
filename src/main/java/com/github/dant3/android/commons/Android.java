package com.github.dant3.android.commons;

import android.content.Context;
import android.net.Uri;

public class Android {
    public static final String RESOURCES_SCHEMA = "android.resource";


    public static int apiLevel() {
        return android.os.Build.VERSION.SDK_INT;
    }

    public static Uri resourceUri(Context context, int resourceID) {
        return Uri.parse(RESOURCES_SCHEMA + "://" + context.getPackageName() + "/" + resourceID);
    }

    public static Uri resourceUri(Context context, String resourceDirectory, String resourceName) {
        return Uri.parse(RESOURCES_SCHEMA + "://" + context.getPackageName() + "/" + resourceDirectory + "/" + resourceName);
    }
}
