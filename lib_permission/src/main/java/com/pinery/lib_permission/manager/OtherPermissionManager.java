package com.pinery.lib_permission.manager;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.accessibility.AccessibilityManagerCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import com.pinery.lib_permission.dialog.GuideDialog;
import com.pinery.lib_permission.util.VersionUtil;
import java.util.List;
import java.util.Set;

import static android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS;

/**
 * 权限管理
 * @author hesong
 *
 */
public class OtherPermissionManager {

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
	 * 打开通知监听功能
	 */
	public static void openNotificationAccess(Context context) {
		if(VersionUtil.isLowerVersionForNotification()){
			return;
		}

		Intent intent = new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);

		new GuideDialog(context).show();
	}

	/**
	 * 打开辅助功能
	 */
	public static void openFuzhuService(Context context){
		try {
			//打开系统设置中辅助功能
			Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}

		new GuideDialog(context).show();
	}

}
