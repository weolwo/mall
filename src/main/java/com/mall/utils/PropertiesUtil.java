package com.mall.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 读取properties文件工具类
 */
public class PropertiesUtil {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static Properties prop;

    static {
        String path = "mall.properties";
        try {
            prop = new Properties();
            prop.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(path), "UTF-8"));

        } catch (IOException e) {
            logger.error("mall文件找不到", e);
        }
    }

    public static String getPropertis(String key) {
        String value = prop.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return value.trim();
    }

    public static String getPropertis(String key, String defaultValue) {
        String value = prop.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            value = defaultValue;
        }
        return value.trim();
    }
}

