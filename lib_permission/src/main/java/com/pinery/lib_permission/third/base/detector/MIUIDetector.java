package com.pinery.lib_permission.third.base.detector;

import android.text.TextUtils;
import android.util.Log;
import com.pinery.lib_permission.third.base.SysUtils;

/**
 * MIUI detector
 */
public class MIUIDetector {
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    public static boolean isMIUI() {
//        Log.i("MIUIDetector", "getMIUIVersionName:"+SysUtils.getSystemProperty(KEY_MIUI_VERSION_NAME));
//        Log.i("MIUIDetector", "getMIUIVersionName2:"+SysUtils.getSystemProperty(KEY_MIUI_VERSION_CODE));
//        Log.i("MIUIDetector", "getMIUIVersionName3:"+SysUtils.getSystemProperty(KEY_MIUI_INTERNAL_STORAGE));
//
//        Log.i("MIUIDetector", "getMIUIVersionName:"+(SysUtils.getSystemProperty(KEY_MIUI_VERSION_NAME) == null));
//        Log.i("MIUIDetector", "getMIUIVersionName2:"+(SysUtils.getSystemProperty(KEY_MIUI_VERSION_CODE) == null));
//        Log.i("MIUIDetector", "getMIUIVersionName3:"+(SysUtils.getSystemProperty(KEY_MIUI_INTERNAL_STORAGE) == null));

        return !TextUtils.isEmpty(SysUtils.getSystemProperty(KEY_MIUI_VERSION_CODE))
                || !TextUtils.isEmpty(SysUtils.getSystemProperty(KEY_MIUI_VERSION_NAME))
                || !TextUtils.isEmpty(SysUtils.getSystemProperty(KEY_MIUI_INTERNAL_STORAGE));
    }

    /**
     * 获取MIUI版本
     *
     * @return
     */
    public static String getMIUIVersionName() {
        Log.i("MIUIDetector", "getMIUIVersionName:"+SysUtils.getSystemProperty(KEY_MIUI_VERSION_NAME));
        return SysUtils.getSystemProperty(KEY_MIUI_VERSION_NAME);
    }

    public static boolean isMIUIV7(){
        return getMIUIVersionName().toLowerCase().equals("v7");
    }

    public static boolean isMIUIV6(){
        return getMIUIVersionName().toLowerCase().equals("v6");
    }

    public static boolean isMIUIV5(){
        return getMIUIVersionName().toLowerCase().equals("v5");
    }
}