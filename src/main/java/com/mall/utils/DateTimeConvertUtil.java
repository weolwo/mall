package com.mall.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * 日期转换工具类
 */
public class DateTimeConvertUtil {

    private final static String FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * @param
     * @param format
     * @return
     */
    public static Date strToDate(String dateStr, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(format);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateStr);
        return dateTime.toDate();
    }

    public static String dateToStr(Date date, String format) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(format);
    }

    //方法重载
    public static Date strToDate(String dateStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateStr);
        return dateTime.toDate();
    }

    //方法重载
    public static String dateToStr(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(FORMAT);
    }
}
