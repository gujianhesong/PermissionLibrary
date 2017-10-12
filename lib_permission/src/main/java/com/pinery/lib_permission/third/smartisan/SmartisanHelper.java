package com.pinery.lib_permission.third.smartisan;

import android.content.Context;
import android.content.Intent;
import com.pinery.lib_permission.third.base.ThirdPartyROMHelper;

/**
 * Created by Administrator on 2015-12-24.
 */
public class SmartisanHelper extends ThirdPartyROMHelper {
    @Override
    public void gotoPermissionManage(Context context) {
        try {
            Intent intent = new Intent("com.smartisanos.security.action.PACKAGE_OVERVIEW");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void gotoAutoStartPermission(Context context) {
    }

    @Override
    public void gotoRootPermission(Context context) {
    }

    @Override
    public boolean gotoFloatWindowPermission(Context context) {
        return false;
    }
}
