package com.github.dant3.android.commons.devices;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

public final class Camera {
    private Camera() {}


    public static enum Request {
        PHOTO(100),
        VIDEO(101);

        public final int CALLBACK;

        private Request(int callbackID) {
            this.CALLBACK = callbackID;
        }
    }


    public static boolean isPresent(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager != null && packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }


    public static boolean takePicture(Activity activity, File whereToSave) {
        Intent takePictureIntent = takePictureIntent(whereToSave);
        PackageManager packageManager = activity.getPackageManager();
        if (packageManager != null && takePictureIntent.resolveActivity(packageManager) != null) {
            activity.startActivityForResult(takePictureIntent, Request.PHOTO.CALLBACK);
            return true;
        } else {
            return false;
        }
    }


    public static Intent takePictureIntent(File whereToSave) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri pictureUri = Uri.fromFile(whereToSave);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        return takePictureIntent;
    }
}
