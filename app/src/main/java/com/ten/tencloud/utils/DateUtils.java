package com.ten.tencloud.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lxq on 2017/12/7.
 */

public class DateUtils {

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * CST => 时间戳
     *
     * @param cst
     * @return
     */
    public static Long cstToStamp(String cst) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.US);
        Date parse = null;
        try {
            parse = sdf.parse(cst);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse.getTime();
    }

    /**
     * 获取时间戳
     *
     * @return 获取时间戳
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 时间转换为时间戳
     *
     * @param time:需要转换的时间
     * @return
     */
    public static String dateToStamp(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        return String.valueOf(ts);
    }

    /**
     * 时间戳转成String
     *
     * @param timestamp
     * @param format
     * @return
     */
    public static String timestampToString(long timestamp, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date = sdf.format(timestamp * 1000);
        return date;
    }

    /**
     * String转Calendar
     *
     * @param format
     * @return
     */
    public static Calendar strToCalendar(String str, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date();
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String dateToDefault(String date) {
        return date.substring(0, 10);
    }

    public static String between(long start, long end) {
        long between = (end - start) / 1000;
        long day = between / (24 * 3600);
        long hour = between % (24 * 3600) / 3600;
        long minute = between % 3600 / 60;
        long second = between % 60 / 60;
        if (day == 0) {
            return hour + "h";
        } else {
            return day + "d";
        }
    }
}
