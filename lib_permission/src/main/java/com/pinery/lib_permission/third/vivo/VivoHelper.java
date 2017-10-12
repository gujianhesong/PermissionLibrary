package com.pinery.lib_permission.third.vivo;

import android.content.Context;
import android.os.Build;
import com.pinery.lib_permission.third.base.ThirdPartyROMHelper;
import com.pinery.lib_permission.third.base.detector.RomDetector;

/**
 * Created by Administrator on 2015-12-24.
 */
public class VivoHelper extends ThirdPartyROMHelper {

    @Override
    public void gotoPermissionManage(Context context) {

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
