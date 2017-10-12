package com.pinery.lib_permission.third.flyme;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.util.Log;
import com.pinery.lib_permission.third.base.ThirdPartyROMHelper;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2015-11-23.
 */
public class FlymeHelper extends ThirdPartyROMHelper {
    @Override
    public void gotoPermissionManage(Context context) {
        gotoAppDetails(context);
    }

    @Override
    public void gotoAutoStartPermission(Context context) {
        gotoAppDetails(context);
    }

    @Override
    public void gotoRootPermission(Context context) {

    }

    @Override
    public boolean gotoFloatWindowPermission(Context context) {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.setClassName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity");
            intent.putExtra("packageName", context.getPackageName());

            context.startActivity(intent);
        }catch (Exception ex){
            ex.printStackTrace();

            return false;
        }

        return true;
    }

    @Override
    public boolean hasFloatWindowPermission(Context context) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            return checkOp(context, 24); //OP_SYSTEM_ALERT_WINDOW = 24;
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static boolean checkOp(Context context, int op) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Class clazz = AppOpsManager.class;
                Method method = clazz.getDeclaredMethod("checkOp", int.class, int.class, String.class);
                return AppOpsManager.MODE_ALLOWED == (int)method.invoke(manager, op, Binder.getCallingUid(),
                        context.getPackageName());
            } catch (Exception e) {
                Log.e("", e.getMessage());
            }
        } else {
            Log.e("", "Below API 19 cannot invoke!");
        }
        return false;
    }
}
