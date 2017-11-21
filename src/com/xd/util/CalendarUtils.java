package com.xd.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日历工具类
 * Created by liyong on 2015-08-06.
 */
public class CalendarUtils {
    private static final Logger logger = LoggerFactory.getLogger(CalendarUtils.class);
    public static final String ENG_DATE_FROMAT = "EEE, d MMM yyyy HH:mm:ss z";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY = "yyyy";
    public static final String MM = "MM";
    public static final String DD = "dd";
    /**
     * 测试
     * @param args args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
        /*
        int year = 2013;
        int month = 6;
        int mondaysNum = getMondayNumByYearMonth(year, month);
        for (int i = 0; i < mondaysNum; i++) {
            Date date = getFirstDateByYearMonthWeek(year, month, (i + 1));
            logger.info(year
                    + "年"
                    + month
                    + "月第"
                    + (i + 1)
                    + "周开始日期为："
                    + CommonDateParseUtil.date2string(date,
                    CommonDateParseUtil.YYYY_MM_DD));
        }


        Date currentDate = new Date();
        Date firstDayOfWeek = getFirstDayOfWeek(currentDate);
        Date lastDayOfWeek = getLastDayOfWeek(currentDate);
        Date firsDateOfMonth= getFirstDayOfMonth(currentDate);
        Date lastDayOfMonth = getLastDayOfMonth(currentDate);
        Date firstDayOfQuarter = getFirstDayOfQuarter(currentDate);
        Date lastDayOfQuarter = getLastDayOfQuarter(currentDate);
        Date firstDayOfHalfYear = getFirstDayOfHalfYear(currentDate);
        Date lastDayOfHalfYear = getLastDayOfHalfYear(currentDate);
        logger.info("当前日期所在周的开始日期和结束日期:"+ CommonDateParseUtil.date2string( firstDayOfWeek,CommonDateParseUtil.YYYY_MM_DD_HH_MM_SS) +" "+
                        CommonDateParseUtil.date2string( lastDayOfWeek,CommonDateParseUtil.YYYY_MM_DD_HH_MM_SS)
        );
        logger.info("当前日期所在月的开始日期和结束日期:"+ CommonDateParseUtil.date2string( firsDateOfMonth,CommonDateParseUtil.YYYY_MM_DD_HH_MM_SS) +" "+
                        CommonDateParseUtil.date2string( lastDayOfMonth,CommonDateParseUtil.YYYY_MM_DD_HH_MM_SS)
        );
        logger.info("当前日期所在季度的开始日期和结束日期:"+ CommonDateParseUtil.date2string( firstDayOfQuarter,CommonDateParseUtil.YYYY_MM_DD_HH_MM_SS) +" "+
                        CommonDateParseUtil.date2string( lastDayOfQuarter,CommonDateParseUtil.YYYY_MM_DD_HH_MM_SS)
        );
        logger.info("当前日期所在半年的开始日期和结束日期:"+ CommonDateParseUtil.date2string( firstDayOfHalfYear,CommonDateParseUtil.YYYY_MM_DD_HH_MM_SS) +" "+
                        CommonDateParseUtil.date2string( lastDayOfHalfYear,CommonDateParseUtil.YYYY_MM_DD_HH_MM_SS)
        );
          */
    }
    /**
     * 获取某年某月中周一的总数
     * @param year 年
     * @param month 月
     * @throws ParseException
     * 获取某年某月中周一的总数【返回-1表示传入参数有误！！！】
     */
    private static int getMondayNumByYearMonth(int year, int month)
            throws ParseException {
        if (month >= 13 || month <= 0) {
            logger.info("月份不存在！");
        } else {
            Date date = getLastDayOfYearMonth(year, month);
            // 当前月份的总天数
            int daysOfMonth = Integer.parseInt(new SimpleDateFormat("dd")
                    .format(date));
            int tongji = 0;
            date = getFirstDayOfYearMonth(year, month);
            for (int i = 0; i < daysOfMonth; i++) {
                Date temp = getDateFromSourceDate(date, i);
                if (getWeekNumByDate(temp) == 1) {
                    do {
                        tongji++;
                        i += 7;
                    } while (i < daysOfMonth);
                }
            }
            return tongji;
        }
        return -1;
    }

    /**
     * 获取指定年月周的周一日期【返回null表示入参数有误！！！】
     * @param year 年
     * @param month 月
     * @param week 周
     * @return 指定年月周的周一日期
     */
    public static Date getFirstDateByYearMonthWeek(int year, int month, int week) {
        Assert.isTrue(month<13&&month>0,"月份参数错误");
            Date date = getLastDayOfYearMonth(year, month);
            // 当前月份的总天数
            int daysOfMonth = Integer.parseInt(new SimpleDateFormat("dd")
                    .format(date));
            int tongji = 0;
            date = getFirstDayOfYearMonth(year, month);
            for (int i = 0; i < daysOfMonth; i++) {
                // 从当月1号开始到当月最后一天遍历数据
                Date temp = getDateFromSourceDate(date, i);
                if (getWeekNumByDate(temp) == 1) {
                    // 一旦发现有周一直接加上7
                    do {
                        tongji++;
                        if (tongji == week) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, month - 1);
                            calendar.set(Calendar.DAY_OF_MONTH, i + 1);
                            return calendar.getTime();
                        }
                        i += 7;
                    } while (i < daysOfMonth);
                }
            }
        return null;
    }

    /**
     * 获取日期所在周的周一日期
     * @param date 日期
     * @return Date
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
            int weekday = c.get(Calendar.DAY_OF_WEEK) ;
            if(weekday==1){
                weekday=6;
            }else {
                weekday=weekday-2;
            }
            c.add(Calendar.DATE, -weekday);
        return c.getTime();
    }

    /**
     * 获取日期所在周的最后一天的日期
     * @param date 日期
     * @return Date
     */
    public static   Date getLastDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
            int weekday = c.get(Calendar.DAY_OF_WEEK);
        if(weekday==1){
            weekday=8;
        }
            c.add(Calendar.DATE, 8 - weekday);
        return c.getTime();
    }

    /**
     * 获取某年某月的第一天
     * @param year 年
     * @param month 月
     * @return 某年某月的第一天
     */
    public static Date getFirstDayOfYearMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, 1);
        cal.roll(Calendar.DATE, -1);
        // 当前月的第一天
        cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 获取指定时间所在的月的第一天日期
     * @param date 日期
     * @return 指定时间所在的月的第一天日期
     */
    public static Date getFirstDayOfMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
       return getFirstDayOfYearMonth(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);

    }

    /**
     * 获取某年某月的最后一天
     * @param year 年
     * @param month 月
     * @return 某年某月的最后一天
     */
    public static Date getLastDayOfYearMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        // 当前月的最后一天
        cal.set(Calendar.DATE, 1);
        cal.roll(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * 获取指定日期所在月份的最后一天的日期
     * @param date 日期
     * @return 指定日期所在月份的最后一天的日期
     */
    public static Date getLastDayOfMonth(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        return getLastDayOfYearMonth(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
    }

    /**
     * 获取指定时间所在季度开始日期
     * @param date 日期
     * @return 指定时间所在季度开始日期
     */
    public static Date getFirstDayOfQuarter(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        if (currentMonth >= 1 && currentMonth <= 3)
            calendar.set(Calendar.MONTH, 0);
        else if (currentMonth >= 4 && currentMonth <= 6)
            calendar.set(Calendar.MONTH, 3);
        else if (currentMonth >= 7 && currentMonth <= 9)
            calendar.set(Calendar.MONTH, 6);
        else if (currentMonth >= 10 && currentMonth <= 12)
            calendar.set(Calendar.MONTH, 9);
        calendar.set(Calendar.DATE, 1);
        return calendar.getTime();

    }
    /**
     * 获取指定时间所在季度结束日期
     * @param date 日期
     * @return 指定时间所在季度结束日期
     */
    public static Date getLastDayOfQuarter(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        if (currentMonth >= 1 && currentMonth <= 3) {
            calendar.set(Calendar.MONTH, 2);
            calendar.set(Calendar.DATE, 31);
        } else if (currentMonth >= 4 && currentMonth <= 6) {
            calendar.set(Calendar.MONTH, 5);
            calendar.set(Calendar.DATE, 30);
        } else if (currentMonth >= 7 && currentMonth <= 9) {
            calendar.set(Calendar.MONTH, 8);
            calendar.set(Calendar.DATE, 30);
        } else if (currentMonth >= 10 && currentMonth <= 12) {
            calendar.set(Calendar.MONTH, 11);
            calendar.set(Calendar.DATE, 31);
        }
        return calendar.getTime();

    }

    /**
     * 获取指定时间所在半年的开始日期
     * @param date 日期
     */
    public static Date getFirstDayOfHalfYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        if (currentMonth >= 1 && currentMonth <= 6){
            calendar.set(Calendar.MONTH, 0);
        }else if (currentMonth >= 7 && currentMonth <= 12){
            calendar.set(Calendar.MONTH, 6);
        }
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR,12);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();

    }
    /**
     * 获取指定时间所在半年的结束日期
     * @param date 日期
     */
    public static Date getLastDayOfHalfYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        if (currentMonth >= 1 && currentMonth <= 6){
            calendar.set(Calendar.MONTH, 5);
            calendar.set(Calendar.DATE, 30);
        }else if (currentMonth >= 7 && currentMonth <= 12){
            calendar.set(Calendar.MONTH, 11);
            calendar.set(Calendar.DATE, 31);
        }
        calendar.set(Calendar.HOUR,11);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();

    }

    /**
     * 获取指定时间所在年第一天
     * @param date 日期
     * @return
     */
   public static Date getFirstDayOfYear(Date date){
       Calendar calendar=Calendar.getInstance();
       calendar.setTime(date);
       calendar.set(Calendar.MONTH, 0);
       calendar.set(Calendar.DATE, 1);
       return  calendar.getTime();
   }

    /**
     * 获取指定时间所在年最后一天
     * @param date 日期
     * @return
     */
    public static Date getLastDayOfYear(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DATE, 31);
        return  calendar.getTime();
    }


    /**
     * 获取当前星期数字【周一~周日分别为1~7表示】
     * @param date 日期
     * @return
     */
    public static int getWeekNumByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        return weekDay == 1 ? 7 : weekDay - 1;
    }

    /**
     * 获取与指定日期距离num天的日期
     * @param currentDate 日期
     * @param num 天数
     * @return
     */
    public static Date getDateFromSourceDate(Date currentDate, int num) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(currentDate);
        cal.add(GregorianCalendar.DATE, num);
        return cal.getTime();
    }
    /**
     * 格式化日期对象
     * @param date 日期
     * @param formatStr 目标格式
     * @return
     */
    public static Date date2date(Date date, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        String str = sdf.format(date);
        try {
            date = sdf.parse(str);
        } catch (Exception e) {
            return null;
        }
        return date;
    }

    /**
     * 时间对象转换成字符串
     * @param date 日期
     * @param formatStr 目标格式
     * @return
     */
    public static String date2string(Date date, String formatStr) {
        String strDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        strDate = sdf.format(date);
        return strDate;
    }

    /**
     * sql时间对象转换成字符串
     * @param timestamp 日期时间
     * @param formatStr 目标格式
     * @return
     */
    public static String timestamp2string(Timestamp timestamp, String formatStr) {
        String strDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        strDate = sdf.format(timestamp);
        return strDate;
    }

    /**
     * 字符串转换成时间对象
     * @param dateString 字符串日期
     * @param formatStr 格式
     * @return
     */
    public static Date string2date(String dateString, String formatStr) {
        Date formateDate = null;
        DateFormat format = new SimpleDateFormat(formatStr);
        try {
            formateDate = format.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
        return formateDate;
    }

    /**
     * @param date 日期
     * @return
     * Date类型转换为Timestamp类型
     */
    public static Timestamp date2timestamp(Date date) {
        if (date == null)
            return null;
        return new Timestamp(date.getTime());
    }

    /**
     * 获得当前年份
     * @return 当前年份
     */
    public static String getNowYear() {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY);
        return sdf.format(new Date());
    }

    /**
     * 获得当前月份
     * @return 当前月份
     */
    public static String getNowMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat(MM);
        return sdf.format(new Date());
    }

    /**
     * 获得当前日期中的日
     * @return 当前日期中的日
     */
    public static String getNowDay() {
        SimpleDateFormat sdf = new SimpleDateFormat(DD);
        return sdf.format(new Date());
    }

    /**
     * 指定时间距离当前时间的中文信息
     * @param time 时间
     * @return 距离当前时间的中文信息
     */
    public static String getLnow(long time) {
        Calendar cal = Calendar.getInstance();
        long now = cal.getTimeInMillis() - time;
        if (now / 1000 < 60) {
            return "1分钟以内";
        } else if (now / 1000 / 60 < 60) {
            return now / 1000 / 60 + "分钟前";
        } else if (now / 1000 / 60 / 60 < 24) {
            return now / 1000 / 60 / 60 + "小时前";
        } else {
            return now / 1000 / 60 / 60 / 24 + "天前";
        }
    }
}
