package com.pinery.lib_permission.manager;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.view.accessibility.AccessibilityManagerCompat;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import com.pinery.lib_permission.R;
import com.pinery.lib_permission.callback.OnPermissionListener;
import com.pinery.lib_permission.dialog.CustomAlertDialog;
import com.pinery.lib_permission.dialog.GuideDialog;
import java.util.List;

/**
 * 悬浮窗权限管理
 *
 * Created by hesong on 2017/10/10.
 */

public class AccessibilityPermissionMgr {

  private static boolean mIsSettingFloatDialogShowing;

  /**
   * 检测是否开启权限
   * @return
   */
  public static <T extends AccessibilityService> void checkOpenPermission(Activity context, Class<T> clazz, OnPermissionListener listener){

    if(!isFuzhuServiceEnabled(context, clazz)){

      showOpenPermissionDialog(context, clazz, listener);

      return;
    }

    if(listener != null){
      listener.onFetchPermission(true);
    }
  }

  /**
   *
   * @param context
   * @param listener
   */
  public static <T extends AccessibilityService> void showOpenPermissionDialog(final Activity context, final Class<T> clazz, final OnPermissionListener listener) {

    if(mIsSettingFloatDialogShowing){
      return;
    }

    mIsSettingFloatDialogShowing = true;

    new CustomAlertDialog(context)
        .setMessage(context.getString(R.string.grant_permission, context.getString(R.string.tip_open_accessability)))
        .setCancelable(false)
        .setPositiveButton(R.string.grant,
            new View.OnClickListener() {

              @Override
              public void onClick(View view) {
                mIsSettingFloatDialogShowing = false;

                requestAccessabilityPermission(context, clazz, listener);

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
   * 请求辅助功能权限
   * @param context
   * @param listener
   */
  private static <T extends AccessibilityService> void requestAccessabilityPermission(final Context context, Class<T> clazz, OnPermissionListener listener){
    if(!isFuzhuServiceEnabled(context, clazz)){
      openFuzhuService(context);

      new Handler(Looper.getMainLooper()).post(new Runnable() {
        @Override public void run() {
          new GuideDialog(context).show();
        }
      });

      listenPermissionOpened(context, clazz, listener);
    }
  }

  /**
   * 检测辅助功能是否开启
   * @param context
   * @param clazz
   * @param <T>
   * @return
   */
  public static <T extends AccessibilityService> boolean isFuzhuServiceEnabled(Context context, Class<T> clazz){
    boolean isEnabled = false;

    AccessibilityManager manager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
    List<AccessibilityServiceInfo> list = AccessibilityManagerCompat.getEnabledAccessibilityServiceList(manager, AccessibilityServiceInfo.FEEDBACK_ALL_MASK);
    for (int i = 0; i < list.size(); i++) {
      String name = context.getPackageName() + "/" + clazz.getName();
      if (name.equals(list.get(i).getId())) {
        isEnabled = true;
        break;
      }
    }

    return isEnabled;
  }

  /**
   * 打开辅助功能
   */
  public static void openFuzhuService(Context context){
    try {
      //打开系统设置中辅助功能
      Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(intent);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 监听权限是否开启
   * @param context
   * @param clazz
   * @param listener
   */
  private static <T extends AccessibilityService> void listenPermissionOpened(Context context, final Class<T> clazz, final OnPermissionListener listener) {
    final long endTime = System.currentTimeMillis() + 120 * 1000;

    innerListenPermissionOpened(context, clazz, endTime, listener);
  }

  /**
   * 监听权限是否开启
   * @param context
   * @param clazz
   * @param endTime
   * @param listener
   */
  private static <T extends AccessibilityService> void innerListenPermissionOpened(final Context context, final Class<T> clazz, final long endTime, final OnPermissionListener listener) {
    if(System.currentTimeMillis() >= endTime) return;

    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
      @Override public void run() {

        if (isFuzhuServiceEnabled(context, clazz)) {
          if(listener != null){
            listener.onFetchPermission(true);
          }
          return;
        }

        innerListenPermissionOpened(context, clazz, endTime, listener);
      }
    }, 1000);

  }


}
