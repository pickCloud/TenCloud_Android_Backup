package com.ten.tencloud.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lxq on 2017/12/7.
 */

public class DateUtils {

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
     * @param date
     * @return
     */
    public static String dateToDefault(String date){
        return date.substring(0,10);
    }
}
