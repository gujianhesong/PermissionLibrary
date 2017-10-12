package com.pinery.lib_permission.third.miui;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.util.Log;
import com.pinery.lib_permission.third.base.ThirdPartyROMHelper;
import com.pinery.lib_permission.third.base.detector.MIUIDetector;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2015-11-23.
 */
public class MIUIHelper extends ThirdPartyROMHelper {
    @Override
    public String getRomVersionName(Context context) {
        return MIUIDetector.getMIUIVersionName();
    }

    @Override
    public void gotoPermissionManage(Context context) {
        try {
            String s = context.getPackageName();
            boolean hasAppPermissionEditor = MIUIDetector.isMIUIV6() || MIUIDetector.isMIUIV7();
            if (hasAppPermissionEditor) {
                Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                intent.putExtra("extra_pkgname", s);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            } else {
                gotoAppDetails(context);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void gotoAutoStartPermission(Context context) {
        try {
            Intent intent = new Intent();
            intent.setAction("miui.intent.action.OP_AUTO_START");
            intent.addCategory(Intent.CATEGORY_DEFAULT);

            context.startActivity(intent);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void gotoRootPermission(Context context) {
        try {
            Intent intent = new Intent();
            intent.setAction("miui.intent.action.ROOT_MANAGER");
            intent.addCategory(Intent.CATEGORY_DEFAULT);

            context.startActivity(intent);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public boolean gotoFloatWindowPermission(Context context) {

        if(!MIUIDetector.isMIUIV5()){

            try {
                Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                intent.putExtra("extra_pkgname", context.getPackageName());

                context.startActivity(intent);

            }catch (Exception ex){
                ex.printStackTrace();

                return false;
            }

        }else{
            gotoAppDetails(context);
        }

        return true;
    }

    @Override
    public boolean hasFloatWindowPermission(Context context) {
        return checkOp(context);
    }

    /**
     * 判断悬浮窗开启标记是否打开
     * @param context
     * @return
     */
    private boolean checkOp(Context context) {
        try {

            if (Build.VERSION.SDK_INT >= 19) {
                if (AppOpsManager.MODE_ALLOWED == op(context)) {
                    return true;
                }
            }else{
                if ((context.getApplicationInfo().flags & 1 << 27) == 1 << 27) {
                    return true;
                } else {
                    return false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 获取系统设置中的悬浮窗是否开启标记
     * @param context
     * @return
     */
    private int op(Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            Log.i("", "aaaaaa : 00000  ");
            try {
                Object localObject = context.getSystemService("appops");
                Class localClass = localObject.getClass();
                Class[] arrayOfClass = new Class[3];
                arrayOfClass[0] = Integer.TYPE;
                arrayOfClass[1] = Integer.TYPE;
                arrayOfClass[2] = String.class;
                Method localMethod = localClass.getMethod("checkOp", arrayOfClass);
                Object[] arrayOfObject = new Object[3];
                arrayOfObject[0] = Integer.valueOf(24);
                arrayOfObject[1] = Integer.valueOf(Binder.getCallingUid());
                arrayOfObject[2] = context.getPackageName();
                int j = ((Integer) localMethod.invoke(localObject, arrayOfObject)).intValue();
                Log.i("", "aaaaaa :return  " + j);
                if (j == 0){
                    Log.i("", "aaaaaa :bbb 11111  ");
                    return 0;
                }
                Log.i("", "aaaaaa :bbb 2222  ");
                return 1;
            } catch (Exception ex) {
                return -1;
            }
        } else {// 19以下的版本的特殊处理
            /*ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;

            String pkgName = context.getPackageName();
            Log.i("OppoHelper", "aaaaaa : 11111  ");
            if (pkgName.equalsIgnoreCase(cn.getPackageName())) {
                try {
                    ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
                    Log.i("OppoHelper", "aaaaaa : 2222  " + Integer.toHexString(appInfo.flags));
                    if (appInfo != null) {
                        if ((0x8000000 & appInfo.flags) != 0){

                            Log.i("OppoHelper", "aaaaaa : 3333  ");
                            return 0;
                        }

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }*/
            Log.i("", "aaaaaa : 4444  ");
            return -1;
        }
    }
}
