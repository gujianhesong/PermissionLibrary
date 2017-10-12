package com.pinery.lib_permission.third.base.detector;

import android.os.Build;
import android.text.TextUtils;
import java.lang.reflect.Method;

import static com.pinery.lib_permission.third.base.SysUtils.getSystemProperty;

/**
 * Rom Detector
 * Created by Administrator on 2015-11-23.
 */
public class RomDetector {
    private static final String FLYME = "Flyme";
    private static final String MIUI = "miui";

    public static String getRomName(){
        return Build.DISPLAY;
    }

    public static boolean aboveVersion(int versionCode){
        return Build.VERSION.SDK_INT >= versionCode;
    }

    public static boolean isSamsung(){
        boolean result = "samsung".equals(Build.MANUFACTURER.toLowerCase()) ? true : false;
        return result;
    }

    public static boolean isMIUI() {
        return MIUIDetector.isMIUI();
    }

    public static boolean isHuawei() {
        return HuaweiDetector.isEMUI();
    }

    public static boolean isFlyme(){
        return FlymeDetector.isFlyme();
    }

    public static boolean isOppo() {
        String key = "ro.build.version.opporom";
        String value = getSystemProperty(key);
        return !TextUtils.isEmpty(value);
    }

    public static boolean isVivo() {
        String key = "ro.vivo.os.version";
        String value = getSystemProperty(key);
        return !TextUtils.isEmpty(value);
    }

    public static boolean isLeshi() {
        String key = "ro.letv.eui";
        String value = getSystemProperty(key);
        return !TextUtils.isEmpty(value);
    }

    public static boolean isCoolpad() {
        String key = "ro.coolpad.ui.theme";
        String value = getSystemProperty(key);
        return !TextUtils.isEmpty(value);
    }

    public static boolean isChuizi() {
        String key = "ro.smartisan.version";
        String value = getSystemProperty(key);
        return !TextUtils.isEmpty(value);
    }

    public static boolean isNubia() {
        String key = "ro.build.nubia.rom.code";
        String value = getSystemProperty(key);
        return !TextUtils.isEmpty(value);
    }

    public static boolean isYijia() {
        String key = "ro.build.ota.versionname";
        String value = getSystemProperty(key);
        return !TextUtils.isEmpty(value);
    }

    public static boolean is360() {
        String key = "ro.build.uiversion";
        String value = getSystemProperty(key);
        return !TextUtils.isEmpty(value) && value.contains("360UI");
    }

    public static boolean isYunOS() {
        String version = null;
        String vmName = null;
        try {
            Method method = Class.forName("android.os.SystemProperties").getMethod("get", String.class);
            version = (String) method.invoke(null, "ro.yunos.version");
            vmName = (String) method.invoke(null, "java.vm.name");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ((vmName != null && vmName.toLowerCase().contains("lemur"))
                || (version != null && version.trim().length() > 0));
    }

}
