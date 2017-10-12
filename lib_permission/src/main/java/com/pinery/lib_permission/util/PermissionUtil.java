package com.pinery.lib_permission.util;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import com.pinery.lib_permission.third.base.RomHelper;

/**
 * Created by hesong on 2017/10/12.
 */

public class PermissionUtil {

  /**
   * 检测是否有悬浮窗权限
   * @return
   */
  public static boolean hasFloatPermission(Context context){
    if(!checkHasPermissionFloatForVersion6(context)
        || !RomHelper.hasFloatWindowPermission(context)){

      //4.4版本以下,没有悬浮窗权限, 必须申请悬浮窗权限
      return false;
    }else{

      return true;
    }
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


}
