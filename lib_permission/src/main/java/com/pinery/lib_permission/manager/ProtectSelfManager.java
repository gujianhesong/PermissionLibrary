package com.pinery.lib_permission.manager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import com.pinery.lib_permission.R;
import com.pinery.lib_permission.dialog.GuideDialog;
import com.pinery.lib_permission.third.base.detector.MIUIDetector;
import com.pinery.lib_permission.third.base.detector.RomDetector;
import java.util.List;

import static android.content.pm.PackageManager.MATCH_ALL;

/**
 * 受保护应用,自启动设置
 *
 * 受保护应用通用的方法是,弹出最近任务,下拉当前应用,可加锁,不被清理
 *
 * Created by hesong on 16/9/16.
 */

public class ProtectSelfManager {

    public static void showFloatGuideDialog(final Context context, String msg){

        GuideDialog dialog = new GuideDialog(context).setGuideContent(msg);

        if(MIUIDetector.isMIUIV5()){
            //MIUI V5 开启悬浮窗权限时,需要关闭自己应用,才能起效
        }

        dialog.show();

        /*MyApplication.getInstance().mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Permission permission = new Permission(Permission.DRAW_OVER_OTHER_APPS, null);
                permission.showGuide = Permission.SHOW_GUIDE_YES;
                permission.showSwitch = true;
                permission.messageGuide = msg;
                PermissionGuideHelper.showGuideWindow(MyApplication.getInstance(), permission);
            }
        }, 500);*/
    }

    public static void openProtect(Context context){

        if(RomDetector.isMIUI()){

            //小米
            if(openCommonProtect()){
                new GuideDialog(context).setGuideContent(context.getString(R.string.tip_open_common_protect)).show();
            }

        }

        if(RomDetector.isHuawei()){

            //华为
            if(openHuaweiProtect(context)){
                new GuideDialog(context).setGuideContent(context.getString(R.string.tip_open)).show();
            }

        }

        if(RomDetector.isSamsung()){

            //三星
            if(openCommonProtect()){
                new GuideDialog(context).setGuideContent(context.getString(R.string.tip_open_samsung_protect)).show();
            }

        }

        if(RomDetector.isFlyme()){

            //魅族
//            openMeizuProtect(context);
//
//            new GuideDialog(context).setGuideContent(context.getString(R.string.tip_open_meizu_protect)).show();
        }

        if(RomDetector.isOppo()){

            //Oppo
            if(openCommonProtect()){
                new GuideDialog(context).setGuideContent(context.getString(R.string.tip_open_common_protect)).show();
            }

        }

    }

    public static void openBoot(Context context){

        if(RomDetector.isMIUI()){

            //小米
            if(openMIUIBootApp(context)){
                if(MIUIDetector.isMIUIV5()){
                    new GuideDialog(context).setGuideContent(context.getString(R.string.tip_open_xiaomi_boot_v5)).show();
                }else{
                    new GuideDialog(context).setGuideContent(context.getString(R.string.tip_open_xiaomi_boot)).show();
                }
            }

        }

        if(RomDetector.isHuawei()){

            //华为
            if(openHuaweiBootApp(context)){
                new GuideDialog(context).setGuideContent(context.getString(R.string.tip_open)).show();
            }

        }

        if(RomDetector.isSamsung()){

            //三星
            if(openSamsung(context)){
                new GuideDialog(context).setGuideContent(context.getString(R.string.tip_open_samsung_boot)).show();
            }

        }

        if(RomDetector.isFlyme()){

            //魅族
            if(openMeizuBoot(context)){
                new GuideDialog(context).setGuideContent(context.getString(R.string.tip_open_meizu_boot)).show();
            }

        }

        if(RomDetector.isOppo()){

            //Oppo
            if(openOppoBoot(context)){
                new GuideDialog(context).setGuideContent(context.getString(R.string.tip_open_oppo_boot)).show();
            }

        }


    }

    public static boolean openMIUIBootApp(Context context){

        try {
            Intent intent = new Intent("miui.intent.action.LICENSE_MANAGER");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.MainAcitivty");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception ex) {
            ex.printStackTrace();

            Object localObject = context.getPackageManager();
            try {
                localObject = ((PackageManager) localObject).getPackageInfo(context.getPackageName(), 0);
                Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                intent.setClassName("com.android.settings", "com.miui.securitycenter.permission.AppPermissionsEditor");
                intent.putExtra("extra_package_uid", ((PackageInfo) localObject).applicationInfo.uid);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (Exception ex2) {

                ex2.printStackTrace();

                try {
                    Intent intent = new Intent();
                    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } catch (Exception ex3) {

                    ex3.printStackTrace();

                    return false;
                }
            }

        }

        return true;
    }

    public static boolean openHuaweiProtect(Context context){

        try {
            Intent localIntent = new Intent();
            localIntent.setClassName("com.huawei.powersavingmode", "com.huawei.powersavingmode.PowerSavingModeActivity");
            localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(localIntent);
        } catch (Exception ex) {
            ex.printStackTrace();

            try {
                Intent localIntent = new Intent("huawei.intent.action.HSM_PROTECTED_APPS");
                localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(localIntent);
            } catch (Exception ex2) {
                ex2.printStackTrace();

                return false;
            }
        }

        return true;
    }

    public static boolean openHuaweiBootApp(Context context){

        try {

            //此处有问题,有的手机没有自启动
            Intent intent = new Intent("huawei.intent.action.HSM_BOOTAPP_MANAGER");
            intent.setPackage("com.huawei.systemmanager");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } catch (Exception ex) {
            ex.printStackTrace();

            try {
                Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.huawei.systemmanager");
                if (intent != null) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }

            } catch (Exception ex2) {
                ex2.printStackTrace();

                return false;
            }
        }

        return true;

    }

    public static boolean openSamsung(Context context){

        try {
            Intent intent = new Intent();
            intent.setClassName("com.samsung.memorymanager", "com.samsung.memorymanager.RamActivity");
            intent.setAction("android.intent.action.MAIN");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception ex) {
            ex.printStackTrace();

            try {
                Intent intent = new Intent();
                intent.setClassName("com.samsung.android.sm", "com.samsung.android.sm.ui.ram.RamActivity");
                intent.setAction("android.intent.action.MAIN");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (Exception ex2) {
                ex2.printStackTrace();

                try {
                    Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.samsung.android.sm");
                    if (intent != null){
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                } catch (Exception ex3) {
                    ex3.printStackTrace();

                    return false;
                }
            }
        }

        return true;
    }

    public static boolean openMeizuProtect(Context context){

        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setClassName("com.meizu.safe", "com.meizu.safe.SecurityCenterActivity");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }catch (Exception ex){
            ex.printStackTrace();

            try {
                Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
                intent.setClassName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity");
                intent.putExtra("packageName", context.getPackageName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }catch (Exception ex3){
                ex3.printStackTrace();

                try {
                    Intent intent = new Intent("android.settings.APP_OPS_SETTINGS");
                    intent.setClassName("com.android.settings", "com.android.settings.Settings$AppControlSettingsActivity");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }catch (Exception ex2){
                    ex2.printStackTrace();

                    return false;
                }
            }

        }

        return true;
    }

    public static boolean openMeizuBoot(Context context){

        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.setClassName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity");
            intent.putExtra("packageName", context.getPackageName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }catch (Exception ex){
            ex.printStackTrace();

            try {
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.setClassName("com.meizu.safe", "com.meizu.safe.SecurityCenterActivity");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }catch (Exception ex3){
                ex3.printStackTrace();

                try {
                    Intent intent = new Intent("android.settings.APP_OPS_SETTINGS");
                    intent.setClassName("com.android.settings", "com.android.settings.Settings$AppControlSettingsActivity");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }catch (Exception ex2){
                    ex2.printStackTrace();

                    return false;
                }
            }

        }

        return true;
    }

    public static boolean openOppoBoot(Context context){

        /*try {
//            Intent intent = new Intent("android.intent.action.MAIN");
//            intent.setClassName("com.oppo.safe", "com.oppo.safe.SecureSafeMainActivity");
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);

            List<AppInfo> list = AppInfoSearcher.searchAppsWithLauncher(context);
            AppInfo appInfo = null;
            if(list != null){
                for(AppInfo info : list){
                    if(info.appName.contains("手机管家") || info.appName.contains("安全中心")){
                        appInfo = info;
                        break;
                    }
                }
            }

            if(appInfo != null){
                AppInfoSearcher.gotoLuancherActivity(appInfo);
            }

            return true;
        }catch (Exception ex){
            ex.printStackTrace();

        }*/

        return false;
    }

    public static boolean hasSamsungProtect(Context context){

        if(Build.VERSION.SDK_INT >= 21){

            boolean hasProtectPage = false;

            Intent intent = new Intent();
            intent.setClassName("com.samsung.memorymanager", "com.samsung.memorymanager.RamActivity");
            intent.setAction("android.intent.action.MAIN");
            hasProtectPage = a(context, intent);

            if(!hasProtectPage){
                intent = new Intent();
                intent.setClassName("com.samsung.android.sm", "com.samsung.android.sm.ui.ram.RamActivity");
                intent.setAction("android.intent.action.MAIN");
                hasProtectPage = a(context, intent);
            }

            return hasProtectPage;
        }

        return false;
    }

    private static boolean a(Context context, Intent paramIntent)
    {
        try {
            List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(paramIntent, MATCH_ALL);
            if (list != null) {
                return list.size() > 0;
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean openCommonProtect(){

        //FunctionUtil.doGodoLastApp();

        return true;
    }

    public static boolean shouldShowProtectPage(Context context){

        if(RomDetector.isMIUI()){
            //小米
            return true;
        }

        if(RomDetector.isHuawei()){
            //华为EMUI
            return true;
        }

        if(RomDetector.isSamsung()) {
            if (ProtectSelfManager.hasSamsungProtect(context)) {
                return true;
            }
        }

        if(RomDetector.isFlyme()){
            //魅族
            return true;
        }

        if(RomDetector.isOppo()){
            //Oppo
            return true;
        }

        return false;
    }




}
