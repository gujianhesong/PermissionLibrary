package com.pinery.lib_permission.third;

import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 系统属性
 * Created by hesong on 16/9/18.
 */
public class CdBuildProperties {

    private final Properties properties;

    private CdBuildProperties() throws IOException {
        properties = new Properties();
        FileInputStream fis = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
        properties.load(fis);
        if(fis != null){
            fis.close();
        }
    }

    public boolean containsKey(final Object key) {
        return properties.containsKey(key);
    }

    public boolean containsValue(final Object value) {
        return properties.containsValue(value);
    }

    public Set<Map.Entry<Object, Object>> entrySet() {
        return properties.entrySet();
    }

    public String getProperty(final String name) {
        return properties.getProperty(name);
    }

    public String getProperty(final String name, final String defaultValue) {
        return properties.getProperty(name, defaultValue);
    }

    public boolean isEmpty() {
        return properties.isEmpty();
    }

    public Enumeration<Object> keys() {
        return properties.keys();
    }

    public Set<Object> keySet() {
        return properties.keySet();
    }

    public int size() {
        return properties.size();
    }

    public Collection<Object> values() {
        return properties.values();
    }

    public static CdBuildProperties newInstance() throws IOException {
        return new CdBuildProperties();
    }

}