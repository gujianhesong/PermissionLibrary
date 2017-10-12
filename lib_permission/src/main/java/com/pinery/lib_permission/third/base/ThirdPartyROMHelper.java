package com.pinery.lib_permission.third.base;

/**
 * 第三方ROM
 * Created by Administrator on 2015-11-23.
 */
public abstract class ThirdPartyROMHelper extends AndroidHelper {
    @Override
    public boolean isThridPartyROM() {
        return true;
    }

    @Override
    public boolean hasPermissionManager() {
        return true;
    }
}
