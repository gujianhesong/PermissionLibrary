package com.pinery.lib_permission.third.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import java.io.File;

/**
 * 原生Android
 * Created by Administrator on 2015-11-23.
 */
public abstract class AndroidHelper implements RomAction {
    @Override
    public boolean isThridPartyROM() {
        return false;
    }

    @Override
    public String getRomVersionName(Context context) {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    @Override
    public boolean hasPermissionManager() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    @Override
    public void gotoAppDetails(Context context) {
        try {
            Uri packageUri = Uri.parse("package:" + context.getPackageName());
            Intent intent =  new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,packageUri);
            intent.addCategory(Intent.CATEGORY_DEFAULT);

            context.startActivity(intent);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * assume you already declare permission from AndroidManifest.xml
     * @param context
     * @return
     */
    @Override
    public boolean hasFloatWindowPermission(Context context) {
        return true;
    }


    @Override
    public boolean isRooted(Context context) {
        return rooted();
    }

    @Override
    public boolean isAutoStart(Context context) {
        return true;
    }

    /**
     * whether a device is rooted <br><br/>
     * some third party device may not work properly
     * @return true rooted, false not
     */
    private static boolean rooted() {
        String binaryName = "su";
        boolean found = false;
        if (!found) {
            String[] places = {"/sbin/", "/system/bin/", "/system/xbin/", "/data/local/xbin/",
                    "/data/local/bin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/"};
            for (String where : places) {
                if ( new File( where + binaryName ).exists() ) {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }
}
