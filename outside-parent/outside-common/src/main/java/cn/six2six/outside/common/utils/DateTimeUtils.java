/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.common.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间辅助方法集合.
 *
 * @author lmz on 2020/12/24
 */
public final class DateTimeUtils {

    public static final DateTimeFormatter FORMAT_AS_TIME_WITH_12 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter FORMAT_AS_TIME_WITH_10 = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    public static final DateTimeFormatter FORMAT_AS_TIME_WITH_8 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter FORMAT_AS_TIME_WITH_14 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter FORMAT_AS_TIME_WITH_6 = DateTimeFormatter.ofPattern("yyyy年MM月");

    private DateTimeUtils() {
        super();
    }

    /**
     * 格式化的当前时间.
     *
     * @see #FORMAT_AS_TIME_WITH_12
     */
    public static String nowAsDateTime12() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(FORMAT_AS_TIME_WITH_12);
    }

    /**
     * 格式化的当前时间.
     *
     * @see #FORMAT_AS_TIME_WITH_10
     */
    public static String nowAsDateTime10() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(FORMAT_AS_TIME_WITH_10);
    }


    /**
     * 格式化的当前时间.
     *
     * @param formatter 时间格式.
     */
    public static String getNowAsFormatter(DateTimeFormatter formatter) {
        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }

    /**
     * 格式化的当前时间.
     *
     * @see #FORMAT_AS_TIME_WITH_10
     */
    public static String nowAsDateTime6() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(FORMAT_AS_TIME_WITH_6);
    }

    /**
     * 格式化的当前时间.
     *
     * @see #FORMAT_AS_TIME_WITH_8
     */
    public static String nowAsDateTime8() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(FORMAT_AS_TIME_WITH_8);
    }

    /**
     * 格式化时间为字符串.
     *
     * @see #FORMAT_AS_TIME_WITH_8
     */
    public static String localDateAsDateTime8(LocalDate localDate) {
        return localDate.format(FORMAT_AS_TIME_WITH_8);
    }

    /**
     * 格式化时间为字符串.
     *
     * @see #FORMAT_AS_TIME_WITH_14
     */
    public static String localDateAsDateTime14(LocalDateTime localDateTime) {
        return localDateTime.format(FORMAT_AS_TIME_WITH_14);
    }

    /**
     * {@link LocalDate} 格式化字符串日期为日期格式.
     *
     * @param localDate 传入需要格式化的日期.
     * @return @see #FORMAT_AS_TIME_WITH_8
     */
    public static LocalDate parseStringToLocalDate(String localDate) {
        return LocalDate.parse(localDate, FORMAT_AS_TIME_WITH_8);
    }

    /**
     * {@link LocalDateTime} 格式化字符串日期为日期格式.
     *
     * @param localDate 传入需要格式化的日期.
     * @return @see #FORMAT_AS_TIME_WITH_8
     */
    public static LocalDateTime parseStringToLocalDateTime(String localDate) {
        return LocalDateTime.parse(localDate, FORMAT_AS_TIME_WITH_14);
    }

    /**
     * {@link LocalDateTime} 格式化字符串日期为日期格式.
     *
     * @param localDate 传入需要格式化的日期.
     * @return @see #FORMAT_AS_TIME_WITH_12
     */
    public static LocalDateTime parseStringToLocalDateTimeWith12(String localDate) {
        return LocalDateTime.parse(localDate, FORMAT_AS_TIME_WITH_12);
    }

    /**
     * LocalDateTime转换为Date.
     *
     * @param localDateTime 传入需要转化的日期.
     * @return {@link Date}.
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * LocalDate转换为Date.
     *
     * @param localDate 传入需要转化的日期.
     * @return {@link Date}.
     */
    public static Date localDate2Date(LocalDate localDate) {

        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * Date转换为LocalDateTime.
     *
     * @param date 传入需要转化的日期.
     * @return {@link LocalDateTime}.
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * Date转换为LocalDate.
     *
     * @param date 传入需要转化的日期.
     * @return {@link LocalDate}.
     */
    public static LocalDate date2LocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
    }

    /**
     * 计算两个日期之间的天数.
     *
     * @param startDay 起始日期.
     * @param endDay   结束日期.
     * @return {@link int}.
     */
    public static int getPeriodDaysBetweenDate(LocalDate startDay, LocalDate endDay) {
        Period period = Period.between(startDay, endDay);
        return period.getDays();
    }


    /**
     * 计算两个日期之间的天数.  Period.between有问题
     *
     * @param startDay 起始日期.
     * @param endDay   结束日期.
     * @return {@link int}.
     */
    public static int getNewPeriodDaysBetweenDate(LocalDate startDay, LocalDate endDay) {
        int days = (int) (endDay.toEpochDay() - startDay.toEpochDay());
        return days;
    }


    /**
     * 日期增加.
     *
     * @param localDateTime 需要处理的日期.
     * @param unit          {@link TemporalUnit} 传入需要增加的类型参数，例如： ChronoUnit.DAYS.
     * @param amountToAdd   添加数量.
     * @return {@link LocalDateTime}.
     */
    public static LocalDateTime plusDate(LocalDateTime localDateTime, TemporalUnit unit, long amountToAdd) {
        return localDateTime.plus(amountToAdd, unit);
    }

    /**
     * 格式化时间.
     *
     * @see #FORMAT_AS_TIME_WITH_6
     */
    public static String date2Str6(Date date) {
        LocalDate localDate = date2LocalDate(date);
        return localDate.format(FORMAT_AS_TIME_WITH_6);
    }

    /**
     * 格式化时间.
     *
     * @see #FORMAT_AS_TIME_WITH_14
     */
    public static String date2Str14(Date date) {
        LocalDateTime localDateTime = date2LocalDateTime(date);
        return localDateTime.format(FORMAT_AS_TIME_WITH_14);
    }

    /**
     * LocalDateTime转时间戳
     */
    public static Long parseLocalDateTimeToLong(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * LocalDateTime转时间戳
     */
    public static LocalDateTime parseLongToLocalDateTime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

//    /**
//     * mongo 日期查询isodate.
//     *
//     * @param date 时间.
//     * @return
//     */
//    public static Date dateToISODate(Date date){
//        SimpleDateFormat format =
//                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));
//        String isoDate = format.format(date);
//        try {
//            return format.parse(isoDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    /**
     * 清空日期中时,分,秒,毫秒 以得到整天.
     */
    public static Date resetDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
