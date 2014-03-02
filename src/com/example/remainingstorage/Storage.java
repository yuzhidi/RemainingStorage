package com.example.remainingstorage;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import android.os.Environment;
import android.util.Log;

public class Storage {
    private static final String TAG = "CameraStorage";

    public static final String DCIM = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            .toString();

    public static final String DIRECTORY = DCIM + "/Camera";

    // Match the code in MediaProvider.computeBucketValues().
    public static final String BUCKET_ID = String.valueOf(DIRECTORY
            .toLowerCase().hashCode());

    public static final long UNAVAILABLE = -1L;
    public static final long PREPARING = -2L;
    public static final long UNKNOWN_SIZE = -3L;
    public static final long FULL_SDCARD = -4L;
    public static final long LOW_STORAGE_THRESHOLD = 50000000;
    public static final long RECORD_LOW_STORAGE_THRESHOLD = 48000000;

    // / M: for more file type and picture type @{
    public static final int CANNOT_STAT_ERROR = -2;
    public static final int PICTURE_TYPE_JPG = 0;
    public static final int PICTURE_TYPE_MPO = 1;
    public static final int PICTURE_TYPE_JPS = 2;
    public static final int PICTURE_TYPE_MPO_3D = 3;

    public static int getSize(String key) {
        return PICTURE_SIZE_TABLE.get(key);
    }

    /* use estimated values for picture size (in Bytes) */
    static final DefaultHashMap<String, Integer> PICTURE_SIZE_TABLE = new DefaultHashMap<String, Integer>();

    static {
        PICTURE_SIZE_TABLE.put("1280x720-normal", 122880);
        PICTURE_SIZE_TABLE.put("1280x720-fine", 147456);
        PICTURE_SIZE_TABLE.put("1280x720-superfine", 196608);

        PICTURE_SIZE_TABLE.put("2560x1440-normal", 245760);
        PICTURE_SIZE_TABLE.put("2560x1440-fine", 368640);
        PICTURE_SIZE_TABLE.put("2560x1440-superfine", 460830);

        PICTURE_SIZE_TABLE.put("3328x1872-normal", 542921);
        PICTURE_SIZE_TABLE.put("3328x1872-fine", 542921);
        PICTURE_SIZE_TABLE.put("3328x1872-superfine", 678651);

        PICTURE_SIZE_TABLE.put("1280x768-normal", 131072);
        PICTURE_SIZE_TABLE.put("1280x768-fine", 157286);
        PICTURE_SIZE_TABLE.put("1280x768-superfine", 209715);

        PICTURE_SIZE_TABLE.put("2880x1728-normal", 331776);
        PICTURE_SIZE_TABLE.put("2880x1728-fine", 497664);
        PICTURE_SIZE_TABLE.put("2880x1728-superfine", 622080);

        PICTURE_SIZE_TABLE.put("3600x2160-normal", 677647);
        PICTURE_SIZE_TABLE.put("3600x2160-fine", 677647);
        PICTURE_SIZE_TABLE.put("3600x2160-superfine", 847059);

        PICTURE_SIZE_TABLE.put("4096x3072-normal", 1096550);
        PICTURE_SIZE_TABLE.put("4096x3072-fine", 1096550);
        PICTURE_SIZE_TABLE.put("4096x3072-superfine", 1370688);

        PICTURE_SIZE_TABLE.put("3264x2448-normal", 696320);
        PICTURE_SIZE_TABLE.put("3264x2448-fine", 696320);
        PICTURE_SIZE_TABLE.put("3264x2448-superfine", 870400);

        PICTURE_SIZE_TABLE.put("2592x1944-normal", 327680);
        PICTURE_SIZE_TABLE.put("2592x1944-fine", 491520);
        PICTURE_SIZE_TABLE.put("2592x1944-superfine", 614400);

        PICTURE_SIZE_TABLE.put("2560x1920-normal", 327680);
        PICTURE_SIZE_TABLE.put("2560x1920-fine", 491520);
        PICTURE_SIZE_TABLE.put("2560x1920-superfine", 614400);

        PICTURE_SIZE_TABLE.put("2048x1536-normal", 262144);
        PICTURE_SIZE_TABLE.put("2048x1536-fine", 327680);
        PICTURE_SIZE_TABLE.put("2048x1536-superfine", 491520);

        PICTURE_SIZE_TABLE.put("1600x1200-normal", 204800);
        PICTURE_SIZE_TABLE.put("1600x1200-fine", 245760);
        PICTURE_SIZE_TABLE.put("1600x1200-superfine", 368640);

        PICTURE_SIZE_TABLE.put("1280x960-normal", 163840);
        PICTURE_SIZE_TABLE.put("1280x960-fine", 196608);
        PICTURE_SIZE_TABLE.put("1280x960-superfine", 262144);

        PICTURE_SIZE_TABLE.put("1024x768-normal", 102400);
        PICTURE_SIZE_TABLE.put("1024x768-fine", 122880);
        PICTURE_SIZE_TABLE.put("1024x768-superfine", 163840);

        PICTURE_SIZE_TABLE.put("640x480-normal", 30720);
        PICTURE_SIZE_TABLE.put("640x480-fine", 30720);
        PICTURE_SIZE_TABLE.put("640x480-superfine", 30720);

        PICTURE_SIZE_TABLE.put("320x240-normal", 13312);
        PICTURE_SIZE_TABLE.put("320x240-fine", 13312);
        PICTURE_SIZE_TABLE.put("320x240-superfine", 13312);

        PICTURE_SIZE_TABLE.put("mav", 1036288);
        PICTURE_SIZE_TABLE.put("autorama", 163840);

        PICTURE_SIZE_TABLE.putDefault(1500000);
    }
    private static final AtomicLong LEFT_SPACE = new AtomicLong(0);

    public static long getLeftSpace() {
        long left = LEFT_SPACE.get();
        Log.v(TAG, "getLeftSpace() return " + left);
        return LEFT_SPACE.get();
    }

    public static void setLeftSpace(long left) {
        LEFT_SPACE.set(left);
        Log.v(TAG, "setLeftSpace(" + left + ")");
    }
}

class DefaultHashMap<K, V> extends HashMap<K, V> {
    private V mDefaultValue;

    public void putDefault(V defaultValue) {
        mDefaultValue = defaultValue;
    }

    @Override
    public V get(Object key) {
        V value = super.get(key);
        return (value == null) ? mDefaultValue : value;
    }
}
