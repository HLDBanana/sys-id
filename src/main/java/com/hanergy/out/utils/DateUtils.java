package com.hanergy.out.utils;


import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

/**
 * 日期处理
 */
public class DateUtils {
	
    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_HOUR_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /** 时间格式(yyyy-MM-dd'T'HH:mm:ss) */
    public final static String DATE_T_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    public static String PATTERNYYYYMMDD = "yyyyMMdd";

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @return  返回yyyy-MM-dd格式日期
     */
	
	  /**
     * 控制SimpleDateFormat对象生成
     *
     * @return
     */

    private static SimpleDateFormat getSimpleDateFormat() {
        SimpleDateFormat simpleDateFormat = threadLocal.get();
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat();
            threadLocal.set(simpleDateFormat);
        }
        return simpleDateFormat;
    }
	public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @param pattern  格式，如：DateUtils.DATE_TIME_PATTERN
     * @return  返回yyyy-MM-dd格式日期
     */
    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 字符串转换成日期
     * @param strDate 日期字符串
     * @param pattern 日期的格式，如：DateUtils.DATE_TIME_PATTERN
     */
    public static Date stringToDate(String strDate, String pattern) {
        if (StringUtils.isBlank(strDate)){
            return null;
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.parseLocalDateTime(strDate).toDate();
    }

    /**
     * @Author hld
     * @Description
     * @Date 15:40 2019/5/23
     * @Param date:2019-05-23
     * @Param time:08:00
     * @return 2019-05-23 08:00:00
     **/
    public static String appendTime(String date, String time) {

        if (StringUtils.isBlank(date) || StringUtils.isBlank(time)){
            return null;
        }
        return date+" "+time+":00";
    }
    /**
     * 字符串转换成日期
     * @param strDate 日期字符串
     */
    public static Date stringToDate(String strDate) {
        if (StringUtils.isBlank(strDate)){
            return null;
        }
        Date date = null;
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //如果日期格式 2019-05-22 12:23:00.0 ，截掉.0 再进行转换
            //String[] date = strDate.split(".");
            date = fmt.parse(strDate);
        }catch (ParseException e) {
            e.printStackTrace();
        }

        //DateTimeFormatter fmt = DateTimeFormat.forPattern(DATE_TIME_PATTERN);
        //fmt.parseLocalDateTime(date[0]).toDate()
        return date;
    }
    /**
     * 字符串转换成日期
     * @param date      日期字符串  2019-05-20
     * @param time      日期字符串  08:30:00
     * @param pattern 日期的格式，如：2019-05-20 08:30:00
     */
    public static Date stringToDate(String date,String time, String pattern) {
        String day = tzToDate(date);
        String dateTime = day+" "+time+":00";
        if (StringUtils.isBlank(dateTime)){
            return null;
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.parseLocalDateTime(dateTime).toDate();
    }

    /**
     * 字符串转换成日期
     * @param date      日期字符串  2019-05-20
     * @param time      日期字符串  08:30:00
     */
    public static String appendDate(String date,String time) {
        String day = tzToDate(date);
        String dateTime = day+" "+time+":00";
        return dateTime;
    }

    /*
     * 将 tz格式日期转换成 yyyy-MM-dd格式字符
     */
    public static String tzToDate(String time){
        time = time.replace("Z", " UTC");//UTC是本地时间
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        Date d = null;
        try {
            d = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //此处是将date类型装换为字符串类型，比如：Sat Nov 18 15:12:06 CST 2017转换为2017-11-18 15:12:06
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sf.format(d);
        return date;
    }

    /*
     * @Author hld
     * @Description 2019-5-23 14:48:00转换成 14：48格式
     * @Date 14:48 2019/5/23
     * @Param [time]  日期字符
     * @return java.lang.String
     **/
    public static String hourMinute(String time){
        String hourMinute = "";
        if (time != null && time.length() > 0){
            String[] times = time.split(" ")[1].split(":");
            hourMinute = times[0]+":"+times[1];
        }
        return hourMinute;
    }

    /**
     * 根据周数，获取开始日期、结束日期
     * @param week  周期  0本周，-1上周，-2上上周，1下周，2下下周
     * @return  返回date[0]开始日期、date[1]结束日期
     */
    public static Date[] getWeekStartAndEnd(int week) {
        DateTime dateTime = new DateTime();
        LocalDate date = new LocalDate(dateTime.plusWeeks(week));

        date = date.dayOfWeek().withMinimumValue();
        Date beginDate = date.toDate();
        Date endDate = date.plusDays(6).toDate();
        return new Date[]{beginDate, endDate};
    }

    /**
     * 对日期的【秒】进行加/减
     *
     * @param date 日期
     * @param seconds 秒数，负数为减
     * @return 加/减几秒后的日期
     */
    public static Date addDateSeconds(Date date, int seconds) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusSeconds(seconds).toDate();
    }

    /**
     * 对日期的【分钟】进行加/减
     *
     * @param date 日期
     * @param minutes 分钟数，负数为减
     * @return 加/减几分钟后的日期
     */
    public static Date addDateMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minutes).toDate();
    }

    /**
     * 对日期的【小时】进行加/减
     *
     * @param date 日期
     * @param hours 小时数，负数为减
     * @return 加/减几小时后的日期
     */
    public static Date addDateHours(Date date, int hours) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusHours(hours).toDate();
    }

    /**
     * 对日期的【天】进行加/减
     *
     * @param date 日期
     * @param days 天数，负数为减
     * @return 加/减几天后的日期
     */
    public static Date addDateDays(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }
    /**
     * 对日期的【天】进行加/减
     *
     * @param date 日期
     * @param days 天数，负数为减
     * @return 加/减几天后的日期
     */
    public static String addDateDays(String date, int days) {
        DateTime dateTime = new DateTime(stringToDate(date,DATE_PATTERN));
        return dateTime.plusDays(days).toString();
    }
    /**
     * 对日期的【周】进行加/减
     *
     * @param date 日期
     * @param weeks 周数，负数为减
     * @return 加/减几周后的日期
     */
    public static Date addDateWeeks(Date date, int weeks) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusWeeks(weeks).toDate();
    }

    /**
     * 对日期的【月】进行加/减
     *
     * @param date 日期
     * @param months 月数，负数为减
     * @return 加/减几月后的日期
     */
    public static Date addDateMonths(Date date, int months) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(months).toDate();
    }

    /**
     * 对日期的【年】进行加/减
     *
     * @param date 日期
     * @param years 年数，负数为减
     * @return 加/减几年后的日期
     */
    public static Date addDateYears(Date date, int years) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusYears(years).toDate();
    }
    
    

    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat();
        simpleDateFormat.applyPattern(pattern);
        return simpleDateFormat.format(date);
    }
    public static String dateToString(Date date) {
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat();
        simpleDateFormat.applyPattern(DATE_TIME_PATTERN);
        return simpleDateFormat.format(date);
    }

    //日期字符转换成另一个日期字符
    public static String stringToString(String time,String pattern){
        Date date = stringToDate(time,DATE_TIME_PATTERN);
        String oTime = dateToString(date,pattern);
        return oTime;
    }
    
    // 获得某天最大时间 2017-10-15 23:59:59
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());;
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }


    //获取两个时间之间的时间差（分钟）
    public static long getMinutebetweenDate(String beginTime,String endTime){
        Date beginDate = stringToDate(beginTime);
        Date endDate = stringToDate(endTime);
        //时间差（分钟）
        long min = (endDate.getTime()-beginDate.getTime())/(1000*60);

        return  min;
    }
    //获取两个时间之间的时间差（分钟）
    public static long getMinutebetweenDate(Date beginDate,Date endDate){
        //时间差（分钟）
        long min = (endDate.getTime()-beginDate.getTime())/(1000*60);

        return  min;
    }

    /**
     * @Author hld
     * @Description  将会议室开始结束时间进行拆分，
     *               比如会议室8:00-12：00空闲 现在要预约 9:00-11:00
     *               将会议室拆分为 8:00-9:00空闲  9:00-11:00占用 11:00-12:00空闲
     * @Date 11:33 2019/5/8
     * @Param freeBeginTime:空闲会议室开始时间
     * @Param freeEndTime:空闲会议室结束时间
     * @Param beginTime:预约开始时间
     * @Param endTime:预约结束时间
     * @return java.util.List<java.util.Map<java.lang.String,java.util.Date>>
     **/
    public static List<Map<String,Date>> splitDate(Date freeBeginTime,Date freeEndTime,
                                                   Date beginTime,Date endTime){
        List<Map<String,Date>> dateList = new ArrayList<>();
        if (beginTime.compareTo(freeBeginTime) == 0){     //开始时间相同
            if (endTime.compareTo(freeEndTime) == 0){      //结束时间相同
                Map<String,Date> map = new HashMap<>();
                map.put("beginTime",beginTime);
                map.put("endTime",endTime);
                dateList.add(map);
                return dateList;
            }else if (endTime.compareTo(freeEndTime) < 0){  //预约时间小于结束时间，则拆分为两段时间
                Map<String,Date> map1 = new HashMap<>();
                map1.put("beginTime",beginTime);
                map1.put("endTime",endTime);
                dateList.add(map1);
                Map<String,Date> map2 = new HashMap<>();
                map2.put("beginTime",endTime);
                map2.put("endTime",freeEndTime);
                dateList.add(map2);
                return dateList;
            }else {     //预约结束时间大于空闲结束时间 说明数据错误
                return null;
            }
        }else if (beginTime.compareTo(freeBeginTime) > 0){  //预约开始时间大于空闲开始时间
            if (endTime.compareTo(freeEndTime) == 0) {      //结束时间相同,拆成两段
                Map<String,Date> map1 = new HashMap<>();
                map1.put("beginTime",freeBeginTime);
                map1.put("endTime",beginTime);
                dateList.add(map1);
                Map<String,Date> map2 = new HashMap<>();
                map2.put("beginTime",beginTime);
                map2.put("endTime",endTime);
                dateList.add(map2);
                return dateList;
            }else if (endTime.compareTo(freeEndTime) < 0){ //预约结束时间小于结束时间，拆分为三段
                Map<String,Date> map1 = new HashMap<>();
                map1.put("beginTime",freeBeginTime);
                map1.put("endTime",beginTime);
                dateList.add(map1);
                Map<String,Date> map2 = new HashMap<>();
                map2.put("beginTime",beginTime);
                map2.put("endTime",endTime);
                dateList.add(map2);
                Map<String,Date> map3 = new HashMap<>();
                map3.put("beginTime",endTime);
                map3.put("endTime",freeEndTime);
                dateList.add(map3);
                return dateList;
            }else {     //预约结束时间大于空闲结束时间 说明数据错误
                return null;
            }
        }else{          //预约开始时间小于空闲开始时间 说明数据错误
            return null;
        }
    }
}
