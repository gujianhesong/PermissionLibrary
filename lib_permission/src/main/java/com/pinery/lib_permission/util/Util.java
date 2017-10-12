package com.pinery.lib_permission.util;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.WindowManager;

/**
 * Created by hesong on 2017/10/12.
 */

public class Util {

  /**
   * 获取窗口尺寸，不包含状态栏的高度
   * @param context
   * @return
   */
  public static Point getWindowSize(Context context){

    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    int windowWidth = wm.getDefaultDisplay().getWidth();
    int windowHeight = wm.getDefaultDisplay().getHeight();

    return new Point(windowWidth, windowHeight);

  }

  /**
   * 获取可适配横竖屏的,窗口尺寸，不包含状态栏的高度
   * @param context
   * @return
   */
  public static Point getWindowSizeFitOriatation(Context context){

    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    final int wWidth = wm.getDefaultDisplay().getWidth();
    final int wHeight = wm.getDefaultDisplay().getHeight();

    final int windowWidth = wWidth > wHeight ? wHeight : wWidth;
    final int windowHeight = wWidth > wHeight ? wWidth : wHeight;

    Point winSize = new Point();
    if(checkIsLandScape(context)){
      winSize.x = windowHeight;
      winSize.y = windowWidth;
    }else{
      winSize.x = windowWidth;
      winSize.y = windowHeight;
    }

    return winSize;

  }

  /**
   * 是否横屏
   * @param context
   * @return
   */
  public static boolean checkIsLandScape(Context context){
    Configuration configuration = context.getResources().getConfiguration();
    return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE;
  }

}
