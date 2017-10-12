package com.pinery.lib_permission.util;

import android.app.Activity;
import android.view.View;

/**
 * Created by hesong on 2017/10/10.
 */

public class ViewUtil {

  /**
   * 通过id获取视图,不用强制类型转换
   * @param rootView
   * @param id
   * @param <E>
   * @return
   */
  public static  <E extends View> E findViewById(View rootView, int id){
    try {
      return (E) rootView.findViewById(id);
    }catch (Exception ex){
      ex.printStackTrace();
    }

    return null;
  }

  /**
   * 通过id获取视图,不用强制类型转换
   * @param activity
   * @param id
   * @param <E>
   * @return
   */
  public static  <E extends View> E findViewById(Activity activity, int id){
    try {
      return (E) activity.findViewById(id);
    }catch (Exception ex){
      ex.printStackTrace();
    }

    return null;
  }


}
