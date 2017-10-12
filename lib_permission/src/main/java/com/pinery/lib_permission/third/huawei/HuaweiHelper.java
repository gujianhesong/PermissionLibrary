package com.pinery.lib_permission.third.huawei;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.pinery.lib_permission.third.base.ThirdPartyROMHelper;
import com.pinery.lib_permission.third.base.detector.RomDetector;

/**
 * Created by Administrator on 2015-12-24.
 */
public class HuaweiHelper extends ThirdPartyROMHelper {
    @Override
    public void gotoPermissionManage(Context context) {
        try {
            Intent intent = new Intent("huawei.intent.action.NOTIFICATIONMANAGER");
            intent.putExtra("showTabsNumber", 1);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void gotoAutoStartPermission(Context context) {
        gotoPermissionManage(context);
    }

    @Override
    public void gotoRootPermission(Context context) {
        gotoPermissionManage(context);
    }

    @Override
    public boolean gotoFloatWindowPermission(Context context) {
        return false;
    }

    @Override
    public boolean hasFloatWindowPermission(Context context) {
        if(!RomDetector.aboveVersion(Build.VERSION_CODES.KITKAT)){
            return true;
        }else{
            // TODO: 2015-12-24  
            return false;
        }
    }
}
