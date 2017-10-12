package com.pinery.lib_permission.third;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import com.pinery.lib_permission.third.base.AndroidHelper;

/**
 * Helper for Android M <br>
 * only works above Android M {@link Build.VERSION_CODES#M}
 * Created by Administrator on 2015-12-4.
 */
@TargetApi(Build.VERSION_CODES.M)
public class AndroidMarshmallowHelper extends AndroidHelper {
    @Override
    public void gotoPermissionManage(Context context) {

    }

    @Override
    public void gotoAutoStartPermission(Context context) {

    }

    @Override
    public void gotoRootPermission(Context context) {

    }

    @Override
    public boolean gotoFloatWindowPermission(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + context.getPackageName()));

            context.startActivity(intent);
        }catch (Exception ex){
            ex.printStackTrace();

            return false;
        }

        return true;
    }

    @Override
    public boolean hasFloatWindowPermission(Context context) {
        //return Settings.canDrawOverlays(context);
        return super.hasFloatWindowPermission(context);
    }
}
