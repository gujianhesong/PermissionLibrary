package com.pinery.lib_permission.manager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import com.pinery.lib_permission.R;
import com.pinery.lib_permission.callback.OnPermissionListener;
import com.pinery.lib_permission.dialog.CustomAlertDialog;
import com.pinery.lib_permission.dialog.GuideDialog;

/**
 * 修改系统设置权限管理
 *
 * Created by hesong on 2017/10/10.
 */

public class SettingsPermissionMgr {

  private static boolean mIsSettingDialogShowing;

  /**
   * 检测是否开启修改系统设置权限
   * @return
   */
  public static void checkOpenPermission(Activity context, OnPermissionListener listener){

      if(!checkHasPermissionWriteSettings(context)){

        showOpenPermissionDialog(context, listener);

        return;
      }

      if(listener != null){
        listener.onFetchPermission(true);
      }

  }

  public static boolean checkHasPermissionWriteSettings(Activity context){
    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (Settings.System.canWrite(context)) {
          return true;
        } else {
          return false;
        }
      } else {
        return true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * 开启提示框
   * @param context
   * @param listener
   */
  private static void showOpenPermissionDialog(final Activity context, final OnPermissionListener listener) {

    if(mIsSettingDialogShowing){
      return;
    }

    mIsSettingDialogShowing = true;

    new CustomAlertDialog(context)
        .setTitle(R.string.tip_permission_title)
        .setMessage(context.getString(R.string.grant_permission, context.getString(R.string.tip_write_settings_permission)))
        .setCancelable(false)
        .setPositiveButton(R.string.grant,
            new View.OnClickListener() {

              @Override
              public void onClick(View view) {
                mIsSettingDialogShowing = false;

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                  @Override public void run() {
                    new GuideDialog(context).setOnlyShowText(R.string.tip_open).show();
                  }
                });

                requestPermission(context, listener);

              }
            })
        .setNegativeButton(android.R.string.cancel,
            new View.OnClickListener() {

              @Override
              public void onClick(View view) {
                mIsSettingDialogShowing = false;

              }
            }).show();

  }

  /**
   * 请求权限
   * @param activity
   */
  public static void requestPermission(final Activity activity, OnPermissionListener listener){
    Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
    intent.setData(Uri.parse("package:" + activity.getPackageName()));
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    activity.startActivity(intent);

    listenPermissionOpened(activity, listener);
  }

  /**
   * 监听权限是否开启
   * @param context
   * @param listener
   */
  private static void listenPermissionOpened(Activity context, final OnPermissionListener listener) {

    final long endTime = System.currentTimeMillis() + 120 * 1000;

    innerListenPermissionOpened(context, endTime, listener);
  }

  /**
   * 监听权限是否开启
   * @param context
   * @param endTime
   * @param listener
   */
  private static void innerListenPermissionOpened(final Activity context, final long endTime, final OnPermissionListener listener) {
    if(System.currentTimeMillis() >= endTime) {
      return;
    }

    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
      @Override public void run() {

        if(checkHasPermissionWriteSettings(context)){
          if(listener != null){
            listener.onFetchPermission(true);
          }
          return;
        }

        innerListenPermissionOpened(context, endTime, listener);
      }
    }, 1000);

  }


}
