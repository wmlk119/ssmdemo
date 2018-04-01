package com.archer.ssm.utils.common;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author Administrator
 * @create 2018-03-23 16:59
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
     *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null){
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取过去的天数
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime()-date.getTime();
        return t/(24*60*60*1000);
    }

    /**
     * 获取过去的小时
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime()-date.getTime();
        return t/(60*60*1000);
    }

    /**
     * 获取过去的分钟
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime()-date.getTime();
        return t/(60*1000);
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis){
        StringBuffer buffer = new StringBuffer();
        long day = timeMillis/(24*60*60*1000);
        long hour = (timeMillis/(60*60*1000)-day*24);
        long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
        long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
        long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
        if(day>0){
            buffer.append(day+",");
        }
        if(hour>0){
            buffer.append(hour+":");
        }
        if(min>0){
            buffer.append(min+":");
        }
        if(s>0){
            buffer.append(s+".");
        }
        if(sss>0){
            buffer.append(sss);
        }
        return buffer.toString();
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    /**
     *根据日期获取该月第一天日期
     */
    public static String getMonthBegin(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String[] dateArr = date.split("-");
        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(dateArr[0]),Integer.parseInt(dateArr[1])-1,1);
        return format.format(c.getTime());
    }
    /**
     *根据日期获取该月最后一天日期
     */
    public static String getMonthEnd(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String[] dateArr = date.split("-");
        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(dateArr[0]),Integer.parseInt(dateArr[1])-1,1);
        c.set(Calendar.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return format.format(c.getTime());
    }

    /**
     *根据日期获取昨天日期
     */
    public static String getYesterday(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            Date dates=format.parse(date);
            c.setTime(dates);
            c.add(c.DATE,-1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format.format(c.getTime());
    }
    /**
     *根据日期获取明天日期
     */
    public static String getTomorrow(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            Date dates=format.parse(date);
            c.setTime(dates);
            c.add(c.DATE,1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format.format(c.getTime());
    }
    /**
     *根据日期获取去年日期
     */
    public static String getYesteryear(String date){
        Calendar c = Calendar.getInstance();
        c.setTime(parseDate(date));
        c.add(Calendar.YEAR,-1);
        return formatDate(c.getTime(),"yyyy-MM-dd");
    }

    public static String getYesteryearDay(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            Date dates=format.parse(date);
            c.setTime(dates);
            c.add(c.YEAR,-1);
            c.add(c.DATE,+1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format.format(c.getTime());
    }
    /**
     *根据日期获取报表周期日期
     */
    public static String getYester(String date,long day){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            Date dates=format.parse(date);
            c.setTime(dates);
            long ll = day;
            int ii= new Long(ll).intValue()+1;
            c.add(c.DATE,-ii);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format.format(c.getTime());
    }
    /**
     *根据日期获取上月日期
     */
    public static String getYesterMonth(String date){
        Calendar c = Calendar.getInstance();
        c.setTime(parseDate(date));
        c.add(c.MONTH,-1);
        return formatDate(c.getTime(),"yyyy-MM-dd");
    }

    /**
     *获取昨天日期
     */
    public static String getYesterday(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(c.DATE,-1);
        return formatDate(c.getTime(),"yyyy-MM-dd");
    }

    /**
     * 根据传入月份返回该月始末和上月始末
     */
    public static String[] getStartEndByMonth(String month){
        String[] monthStartEnd = new String[8];
        Date d = parseDate(month);
        Calendar  c = Calendar.getInstance();
        c.setTime(d);
        //本期始末
        c.add(Calendar.DAY_OF_MONTH,24);
        d = c.getTime();
        monthStartEnd[0] = formatDate(d,"yyyy-MM-dd");
        c.add(Calendar.MONTH,-1);
        c.add(Calendar.DAY_OF_MONTH,1);
        d = c.getTime();
        monthStartEnd[1] = formatDate(d,"yyyy-MM-dd");
        //上期始末
        c.add(Calendar.DAY_OF_MONTH,-1);
        d = c.getTime();
        monthStartEnd[2] = formatDate(d,"yyyy-MM-dd");
        c.add(Calendar.MONTH,-1);
        c.add(Calendar.DAY_OF_MONTH,1);
        d = c.getTime();
        monthStartEnd[3] = formatDate(d,"yyyy-MM-dd");
        //上上期始末
        c.add(Calendar.DAY_OF_MONTH,-1);
        d = c.getTime();
        monthStartEnd[4] = formatDate(d,"yyyy-MM-dd");
        c.add(Calendar.MONTH,-1);
        c.add(Calendar.DAY_OF_MONTH,1);
        d = c.getTime();
        monthStartEnd[5] = formatDate(d,"yyyy-MM-dd");
        //上上上期始末
        c.add(Calendar.DAY_OF_MONTH,-1);
        d = c.getTime();
        monthStartEnd[6] = formatDate(d,"yyyy-MM-dd");
        c.add(Calendar.MONTH,-1);
        c.add(Calendar.DAY_OF_MONTH,1);
        d = c.getTime();
        monthStartEnd[7] = formatDate(d,"yyyy-MM-dd");
        return monthStartEnd;
    }

    /**
     *根据日期获取上月26号日期字符串
     */
    public static String getCurrentPeriodStart(String date){
        Calendar c = Calendar.getInstance();
        c.setTime(parseDate(date));
        c.add(Calendar.MONTH,-1);
        c.set(Calendar.DAY_OF_MONTH,26);
        return formatDate(c.getTime(),"yyyy-MM-dd");
    }

    /**
     *根据日期获取去年同天的后一天日期字符串
     */
    public static String getLastYearNextDate(String date){
        Calendar c = Calendar.getInstance();
        c.setTime(parseDate(date));
        c.add(Calendar.YEAR,-1);
        c.add(Calendar.DAY_OF_MONTH,1);
        return formatDate(c.getTime(),"yyyy-MM-dd");
    }

    /**
     * 获取该月最后一天字符串
     * @param date
     * @return
     */
    public static String getLastDateOfMonth(String date){
        Calendar c = Calendar.getInstance();
        c.setTime(parseDate(date));
        //获取该月最大天数
        int dayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        return formatDate(c.getTime(),"yyyy-MM-dd");
    }

    /**
     * 获取该月所有日期字符串数组
     */
    public static String[] getDateArrOfMonth(String date){
        Calendar c = Calendar.getInstance();
        c.setTime(parseDate(date));
        //获取该月最大天数
        int dayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        String[] dateArr = new String[dayOfMonth];
        for(int i = 0 ;i<dayOfMonth;i++){
            c.set(Calendar.DATE,i+1);
            dateArr[i] = formatDate(c.getTime(),"yyyy-MM-dd");;
        }
        return dateArr;
    }

    /**
     * 根据日期获取i年后的同一日期字符串
     * @param date
     * @param i
     * @return
     */
    public static String getYearsAfterDate(String date,int i){
        Calendar c = Calendar.getInstance();
        c.setTime(parseDate(date));
        c.add(Calendar.YEAR,i);
        return formatDate(c.getTime(),"yyyy-MM-dd");
    }

    /**
     *根据日期获取几年后的日期
     */
    public static Date getNyearDate(Date date,int num){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR,num);
        return c.getTime();
    }
}
