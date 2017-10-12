package com.pinery.lib_permission.third.base;

import android.content.Context;

/**
 * Created by Administrator on 2015-11-23.
 */
public interface RomAction {
    /**
     * 判断是否第三方ROM
     *
     * @return
     */
    public boolean isThridPartyROM();

    /**
     * Rom版本号，原生系统为19，20，21等api level；小米等为v5,v6,v7等
     *
     * @param context
     * @return
     */
    public String getRomVersionName(Context context);

    /**
     * 跳转至App详情
     *
     * @param context
     * @return
     */
    public void gotoAppDetails(Context context);

    /**
     * 是否有权限管理
     *
     * @return
     */
    public boolean hasPermissionManager();

    /**
     * 跳转至权限管理界面
     *
     * @param context
     * @return
     */
    public void gotoPermissionManage(Context context);

    /**
     * whether grant the auto-start permission
     *
     * @param context
     * @return
     */
    public boolean isAutoStart(Context context);

    /**
     * 跳转至自启动权限设置界面
     *
     * @param context
     * @return
     */
    public void gotoAutoStartPermission(Context context);

    public boolean isRooted(Context context);

    /**
     * 跳转至ROOT权限设置界面
     *
     * @param context
     * @return
     */
    public void gotoRootPermission(Context context);

    /**
     * whether grant the floating-window permission, as the permission "SYSTEM_ALERT_WINDOW"
     * declared in AndroidManifest.xml
     *
     * @param context
     * @return
     */
    public boolean hasFloatWindowPermission(Context context);

    /**
     * 跳转至浮窗权限设置界面
     *
     * @param context
     * @return
     */
    public boolean gotoFloatWindowPermission(Context context);


}
