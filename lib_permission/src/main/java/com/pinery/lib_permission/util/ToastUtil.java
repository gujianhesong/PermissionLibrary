package com.pinery.lib_permission.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

  /**
   * 系统Toast
   */
  public static void showToast(Context context, String text) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
  }

  /**
   * 系统Toast
   */
  public static void showToast(Context context, int textId) {
    Toast.makeText(context, context.getString(textId), Toast.LENGTH_SHORT).show();
  }
}
