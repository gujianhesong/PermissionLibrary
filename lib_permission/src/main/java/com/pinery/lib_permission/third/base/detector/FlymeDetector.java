package com.pinery.lib_permission.third.base.detector;

import com.pinery.lib_permission.third.CdBuildProperties;
import java.io.IOException;

/**
 * Flyme Detector
 */
public class FlymeDetector {

    private static final String KEY_FLYME_ICON = "persist.sys.use.flyme.icon";
    private static final String KEY_FLYME_PUBLISHED = "ro.flyme.published";
    private static final String KEY_FLYME_FLYME = "ro.meizu.setupwizard.flyme";

    public static boolean isFlyme() {
        try {
            final CdBuildProperties prop = CdBuildProperties.newInstance();
            return prop.containsKey(KEY_FLYME_ICON)
                    || prop.containsKey(KEY_FLYME_PUBLISHED)
                    || prop.containsKey(KEY_FLYME_FLYME);
        } catch (final IOException e) {
            return false;
        }

    }

    public static String getFlymeVersionName() {
        return null;
    }
}