package com.pinery.lib_permission.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import com.pinery.lib_permission.R;
import com.pinery.lib_permission.callback.OnPermissionListener;
import com.pinery.lib_permission.dialog.CustomAlertDialog;
import com.pinery.lib_permission.dialog.GuideDialog;
import com.pinery.lib_permission.util.VersionUtil;
import java.util.Set;

import static android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS;

/**
 * 通知权限管理
 *
 * Created by hesong on 2017/10/10.
 */

public class NotificationPermissionMgr {

  private static boolean mIsSettingFloatDialogShowing;

  /**
   * 检测是否开启权限
   * @return
   */
  public static void checkOpenPermission(Activity context, OnPermissionListener listener){

    if(!isNotificationListenerEnabled(context)){

      showOpenPermissionDialog(context, listener);

      return;
    }

    if(listener != null){
      listener.onFetchPermission(true);
    }
  }

  /**
   * 通知监听功能是否开启
   * @param context
   * @return
   */
  public static boolean isNotificationListenerEnabled(Context context) {
    Set<String> packageNames = NotificationManagerCompat.getEnabledListenerPackages(context);
    if (packageNames.contains(context.getPackageName())) {
      return true;
    }
    return false;
  }


  /**
   *
   * @param context
   * @param listener
   */
  public static void showOpenPermissionDialog(final Activity context, final OnPermissionListener listener) {

    if(mIsSettingFloatDialogShowing){
      return;
    }

    mIsSettingFloatDialogShowing = true;

    new CustomAlertDialog(context)
        .setMessage(context.getString(R.string.grant_permission, context.getString(R.string.str_avoid_be_killed)))
        .setCancelable(false)
        .setPositiveButton(R.string.grant,
            new View.OnClickListener() {

              @Override
              public void onClick(View view) {
                mIsSettingFloatDialogShowing = false;

                requestNotificationPermission(context, listener);

              }
            })
        .setNegativeButton(android.R.string.cancel,
            new View.OnClickListener() {

              @Override
              public void onClick(View view) {
                mIsSettingFloatDialogShowing = false;

              }
            }).show();

  }

  /**
   * 请求通知权限
   * @param context
   * @param listener
   */
  private static void requestNotificationPermission(final Context context, OnPermissionListener listener){
    if(!isNotificationListenerEnabled(context)){

      openNotificationAccess(context, listener);

      new Handler(Looper.getMainLooper()).post(new Runnable() {
        @Override public void run() {
          new GuideDialog(context).show();
        }
      });

      listenPermissionOpened(context, listener);
    }
  }

  /**
   * 打开通知监听功能
   */
  public static void openNotificationAccess(Context context, final OnPermissionListener listener) {
    if(VersionUtil.isLowerVersionForNotification()){
      return;
    }

    Intent intent = new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }

  /**
   * 监听权限是否开启
   * @param context
   * @param listener
   */
  private static void listenPermissionOpened(Context context, final OnPermissionListener listener) {

    final long endTime = System.currentTimeMillis() + 120 * 1000;

    innerPermissionOpened(context, endTime, listener);
  }

  /**
   * 监听权限是否开启
   * @param context
   * @param endTime
   * @param listener
   */
  private static void innerPermissionOpened(final Context context, final long endTime, final OnPermissionListener listener) {
    if(System.currentTimeMillis() >= endTime) return;

    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
      @Override public void run() {

        if (isNotificationListenerEnabled(context)) {
          if(listener != null){
            listener.onFetchPermission(true);
          }
          return;
        }

        innerPermissionOpened(context, endTime, listener);
      }
    }, 1000);

  }


}
