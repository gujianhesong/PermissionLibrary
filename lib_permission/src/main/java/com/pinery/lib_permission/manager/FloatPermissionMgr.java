package com.pinery.lib_permission.manager;

import android.app.Activity;
import android.content.Context;
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
import com.pinery.lib_permission.third.base.RomHelper;
import com.pinery.lib_permission.third.base.detector.RomDetector;
import com.pinery.lib_permission.util.PermissionUtil;
import com.pinery.lib_permission.util.VersionUtil;

/**
 * 悬浮窗权限管理
 *
 * Created by hesong on 2017/10/10.
 */

public class FloatPermissionMgr {

  private final static int REQUEST_CODE_FLOAT = 1101;//悬浮窗权限

  private static boolean mIsSettingFloatDialogShowing;

  /**
   * 检测是否开启悬浮窗权限
   * @return
   */
  public static void checkOpenPermission(Activity context, OnPermissionListener listener){

    //if(needRequestFloatPermission(context)){

      //检测国产机是否有悬浮窗权限
      if (/*Build.VERSION.SDK_INT < Build.VERSION_CODES.M && */!RomHelper.hasFloatWindowPermission(context)) {

        showOpenPermissionDialog(context, false, listener);

        return;
      }

      //请求6.0以上悬浮窗权限
      if(!PermissionUtil.checkHasPermissionFloatForVersion6(context)){

        showOpenPermissionDialog(context, true, listener);

        return;
      }

      if(listener != null){
        listener.onFetchPermission(true);
      }

    //}

  }

  /**
   * 是否需要申请悬浮窗权限
   * @return
   */
  public static boolean needRequestFloatPermission(Context context){

    if(VersionUtil.getAndroidSDKVersion() >= 19){

      //4.4版本及以上,不主动申请悬浮窗权限,默认用Toast
      return false;

    }else{

      //4.4版本以下,没有悬浮窗权限, 必须申请悬浮窗权限
      return !PermissionUtil.hasFloatPermission(context);
    }

  }


  /**
   * 悬浮框开启提示框
   * @param isV6Upper
   */
  private static void showOpenPermissionDialog(final Activity context, final boolean isV6Upper, final OnPermissionListener listener) {

    if(mIsSettingFloatDialogShowing){
      return;
    }

    mIsSettingFloatDialogShowing = true;

    new CustomAlertDialog(context)
        .setTitle(R.string.tip_permission_title)
        .setMessage(context.getString(R.string.grant_permission, context.getString(R.string.tip_float_permission)))
        .setCancelable(false)
        .setPositiveButton(R.string.grant,
            new View.OnClickListener() {

              @Override
              public void onClick(View view) {
                mIsSettingFloatDialogShowing = false;

                if(isV6Upper){

                  requestPermissionFloatForVersion6(context, listener);

                  new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override public void run() {
                      new GuideDialog(context).setOnlyShowText(R.string.tip_open).show();
                    }
                  });

                }else{

                  requestGuochanFloat(context, listener);

                }

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
   * 是否有6.0以上悬浮窗权限
   * @return
   */
  public static boolean checkHasPermissionFloatForVersion6(Context context){
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      return true;
    }else{
      return Settings.canDrawOverlays(context);
    }
  }

  /**
   * 请求6.0以上悬浮窗权限
   * @param activity
   */
  public static void requestPermissionFloatForVersion6(final Activity activity, OnPermissionListener listener){
    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
        Uri.parse("package:" + activity.getPackageName()));
    activity.startActivityForResult(intent, REQUEST_CODE_FLOAT);

    listenFloatPermissionOpened(activity, listener);
  }

  /**
   * 请求国产机悬浮窗权限
   * @param context
   * @param listener
   */
  public static void requestGuochanFloat(Activity context, OnPermissionListener listener){

    boolean openFloat = false;

    if(RomDetector.isOppo()){
      openFloat = ProtectSelfManager.openOppoBoot(context);

      if(openFloat){
        ProtectSelfManager.showFloatGuideDialog(context, context.getString(R.string.tip_open_oppo_float));
      }

    }else if(RomHelper.gotoFloatWindowPermission(context)){

      openFloat = true;

      if(RomDetector.isFlyme()){
        ProtectSelfManager.showFloatGuideDialog(context, context.getString(R.string.tip_open_meizu_float));
      }else{
        ProtectSelfManager.showFloatGuideDialog(context, context.getString(R.string.tip_open_float));
      }

    }

    if(!openFloat){
      //String appname = context.getString(R.string.app_name);
      String content = context.getString(R.string.tip_open_float_error_content, "");

      new CustomAlertDialog(context)
          .setTitle(R.string.tip_permission_not_open)
          .setMessage(content)
          .setPositiveButton(android.R.string.ok, null)
          .show();
    }

    listenFloatPermissionOpened(context, listener);
  }

  /**
   * 监听权限是否开启
   * @param context
   * @param listener
   */
  private static void listenFloatPermissionOpened(Context context, final OnPermissionListener listener) {

    final long endTime = System.currentTimeMillis() + 120 * 1000;

    innerListenPermissionOpened(context, endTime, listener);
  }

  /**
   * 监听权限是否开启
   * @param context
   * @param endTime
   * @param listener
   */
  private static void innerListenPermissionOpened(final Context context, final long endTime, final OnPermissionListener listener) {
    if(System.currentTimeMillis() >= endTime) {
      return;
    }

    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
      @Override public void run() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M && RomHelper.hasFloatWindowPermission(context)){
          if(listener != null){
            listener.onFetchPermission(true);
          }
          return;
        }

        if (checkHasPermissionFloatForVersion6(context)) {
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
