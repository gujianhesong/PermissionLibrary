package com.pinery.lib_permission.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 *
 */
public class VersionUtil {
	
	/**
	 * 获取系统版本号
	 * @return
	 */
	public static int getAndroidSDKVersion() {
		int version = 0;
		try {
			version = Integer.valueOf(Build.VERSION.SDK_INT);
			
			//LogUtil.i("当前系统版本号：" + version);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return version;
	}
	
	/**
	 * 是否低版本，针对系统通知
	 * @return
	 */
	public static boolean isLowerVersionForNotification(){
		
		return getAndroidSDKVersion() < Build.VERSION_CODES.JELLY_BEAN_MR2;
		
	}

	/**
	 * 获取当前应用的版本号
	 * @param context
	 * @return
	 */
	public static int getAppVersionCode(Context context){

		return getAppVersionCode(context);
	}

	/**
	 * 获取指定应用的版本号
	 * @param context
	 * @param pkgName
	 * @return
	 */
	public static int getAppVersionCode(Context context, String pkgName){
		int version = 0;
		try {
			version = context.getPackageManager().getPackageInfo(pkgName, 0).versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		return version;
	}

}
