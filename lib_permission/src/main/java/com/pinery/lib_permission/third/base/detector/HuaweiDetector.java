package com.pinery.lib_permission.third.base.detector;

import android.text.TextUtils;
import com.pinery.lib_permission.third.CdBuildProperties;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2015-12-25.
 */
public class HuaweiDetector {

    private static final String KEY_EMUI_VERSION_CODE = "ro.build.version.emui";

    public static boolean isEMUI() {
        return !TextUtils.isEmpty(getEmuiVersion());
    }

    public static String getEmuiVersion()
    {
        String buildVersion = "";
        int ver = 0;
        try
        {
            Class<?> classType = Class.forName("android.os.SystemProperties");
            Method getMethod = classType.getDeclaredMethod("get", new Class<?>[] {String.class});
            buildVersion = (String)getMethod.invoke(classType, new Object[] {"ro.build.version.emui"});
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
        System.out.println("buildVersion----------------"+buildVersion);
        return buildVersion;
    }

    private static boolean isPropertiesExist(String... keys) {
        try {
            CdBuildProperties prop = CdBuildProperties.newInstance();
            for (String key : keys) {
                String str = prop.getProperty(key);
                if (str == null) {
                    return false;
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
