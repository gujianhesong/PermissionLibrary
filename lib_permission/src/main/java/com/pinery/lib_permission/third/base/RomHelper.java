package com.pinery.lib_permission.third.base;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;
import com.pinery.lib_permission.third.AndroidMarshmallowHelper;
import com.pinery.lib_permission.third.BelowMHelper;
import com.pinery.lib_permission.third.base.detector.RomDetector;
import com.pinery.lib_permission.third.flyme.FlymeHelper;
import com.pinery.lib_permission.third.miui.MIUIHelper;
import com.pinery.lib_permission.third.oppo.OppoHelper;

/**
 * Created by Administrator on 2015-12-4.
 */
public class RomHelper {
    private static final RomAction action;
    static {
        if(RomDetector.isFlyme()){
            action = new FlymeHelper();
        }else if(RomDetector.isOppo()){
            action = new OppoHelper();
        }else if(RomDetector.isMIUI()){
            action = new MIUIHelper();
        }else if(RomDetector.aboveVersion(Build.VERSION_CODES.M)){
            action = new AndroidMarshmallowHelper();
        }else{
            action = new BelowMHelper();
        }
        //Log.i("", "-----------"+action.getClass().getSimpleName());
    }

    public static boolean isThridPartyROM() {

        if(RomDetector.isMIUI()){
            return true;
        }else if(RomDetector.isHuawei()){
            return true;
        }else if(RomDetector.isFlyme()) {
            return true;
        }else if(RomDetector.isOppo()){
            return true;
        }else if(RomDetector.isVivo()){
            return true;
        }else if(RomDetector.isLeshi()){
            return true;
        }else if(RomDetector.isCoolpad()) {
            return true;
        }else if(RomDetector.isChuizi()){
            return true;
        }else if(RomDetector.isNubia()){
            return true;
        }else if(RomDetector.isYijia()){
            return true;
        }else if(RomDetector.is360()){
            return true;
        }else if(RomDetector.isYunOS()){
            return true;
        }

        return false;
    }

    public static void gotoAppDetails(Context context) {
        action.gotoAppDetails(context);
    }

    public static  boolean hasPermissionManager() {
        return action.hasPermissionManager();
    }

    public static  void gotoPermissionManage(Context context) {
        action.gotoPermissionManage(context);
    }

    public static  boolean isAutoStart(Context context) {
        return action.isAutoStart(context);
    }

    public static  void gotoAutoStartPermission(Context context) {
        action.gotoAutoStartPermission(context);
    }

    public static  boolean isRooted(Context context) {
        return action.isRooted(context);
    }

    public static  void gotoRootPermission(Context context) {
        action.gotoRootPermission(context);
    }

    public static  boolean hasFloatWindowPermission(Context context) {
        return action.hasFloatWindowPermission(context);
    }

    public static boolean gotoFloatWindowPermission(Context context) {
        return action.gotoFloatWindowPermission(context);
    }

    public static void toast(Context context){
        Toast.makeText(context,action.getClass().getSimpleName() +  " # " + RomDetector.getRomName(), Toast.LENGTH_SHORT).show();
    }
}
