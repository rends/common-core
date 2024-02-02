package com.common.core.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


/**
 * @author rends
 * @date 2019/11/2
 **/
public class TimeUtil {

    /**
     * 一天的毫秒数
     */
    private static final int DAY_MILLISECOND = 1000 * 3600 * 24;

    public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 指定时分秒
     *
     * @param hour   时间
     * @param minute 分钟
     * @param second 秒
     * @return 指定的时间
     */
    public static Date time(int hour, int minute, int second) {
        return time(new Date(), hour, minute, second);
    }

    public static SimpleDateFormat timeFormat() {
        return new SimpleDateFormat(FORMAT);
    }


    /**
     * 指定时分秒
     *
     * @param date   操作对象
     * @param hour   时间
     * @param minute 分钟
     * @param second 秒
     * @return 指定的时间
     */
    public static Date time(Date date, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 计算日期偏移
     *
     * @param field  偏移类型
     * @param amount 偏移量
     * @return 便宜后的时间
     */
    public static Date add(int field, int amount) {
        Calendar can = Calendar.getInstance();
        can.add(field, amount);
        return can.getTime();
    }

    public static Date add(Date flag, int field, int amount) {
        Objects.requireNonNull(flag);
        Calendar can = Calendar.getInstance();
        can.setTime(flag);
        can.add(field, amount);
        return can.getTime();
    }

    /**
     * 指定日期的最后一刻
     *
     * @param time 不传表示当天
     * @return
     */
    public static Date lastestTime(Date time) {
        Calendar can = Calendar.getInstance();
        can.setTime((time == null) ? new Date() : time);
        can.set(Calendar.HOUR_OF_DAY, 23);
        can.set(Calendar.MINUTE, 59);
        can.set(Calendar.SECOND, 59);
        can.set(Calendar.MILLISECOND, 999);
        return can.getTime();
    }

    /**
     * 一天的最早时间
     *
     * @param time 不传表示当前
     * @return
     */
    public static Date earliestTime(Date time) {
        Calendar can = Calendar.getInstance();
        can.setTime((time == null) ? new Date() : time);
        can.set(Calendar.HOUR_OF_DAY, 0);
        can.set(Calendar.MINUTE, 0);
        can.set(Calendar.SECOND, 0);
        can.set(Calendar.MILLISECOND, 0);
        return can.getTime();
    }

    /**
     * 获取指定的时间所属月份的第一天的零点
     *
     * @param date 指定的时间
     * @return 月份的第一天不传表示当月第一天
     */
    public static Date firstDay(Date date) {
        Calendar can = Calendar.getInstance();
        can.setTime((date == null) ? new Date() : date);
        can.set(Calendar.DAY_OF_MONTH, 1);
        can.set(Calendar.SECOND, 0);
        can.set(Calendar.MINUTE, 0);
        can.set(Calendar.HOUR_OF_DAY, 0);
        can.set(Calendar.MILLISECOND, 0);
        return can.getTime();
    }

    /**
     * 获取所传月份的最后一天的最后一秒
     *
     * @param date 指定的时间
     * @return 月份的最后一天的最后一秒
     */
    public static Date lastDay(Date date) {
        Calendar can = Calendar.getInstance();
        can.setTime((date == null) ? new Date() : date);
        can.set(Calendar.DAY_OF_MONTH, can.getActualMaximum(Calendar.DAY_OF_MONTH));
        can.set(Calendar.HOUR_OF_DAY, 23);
        can.set(Calendar.SECOND, 59);
        can.set(Calendar.MINUTE, 59);
        can.set(Calendar.MILLISECOND, 999);
        return can.getTime();
    }

    public static Date date(String date) {
        return toDate(toLocalDateTime(date));
    }

    public static Date toDate(LocalDate date) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, date.getYear());
        c.set(Calendar.MONTH, date.getMonthValue() - 1);
        c.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date toDate(LocalDateTime date) {
        return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate toLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(FORMAT));
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalTime toLocalTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }

    public static LocalTime getTime(Date date) {
        date = date == null ? new Date() : date;
        return toLocalTime(date);
    }

    public static LocalTime getTime(String date) {
        return toLocalDateTime(date).toLocalTime();
    }

    public static LocalDate getDate(Date date) {
        date = date == null ? new Date() : date;
        return toLocalDate(date);
    }

    public static LocalDate getDate(String date) {
        return toLocalDateTime(date).toLocalDate();
    }

    /**
     * 获取两个时间之间的差值
     *
     * @param end   结束时间
     * @param start 开始时间
     * @return 返回值
     */
    public static int diffDay(Date end, Date start) {
        Objects.requireNonNull(end);
        Objects.requireNonNull(start);
        end = earliestTime(end);
        start = earliestTime(start);
        return Long.valueOf((end.getTime() - start.getTime()) / DAY_MILLISECOND).intValue();
    }

    /**
     * 判断两个时间相差多少个月
     *
     * @param end   结束时间
     * @param start 开始时间
     * @return 相差的月份
     */
    public static int diffMonth(Date end, Date start) {
        Objects.requireNonNull(end);
        Objects.requireNonNull(start);
        Calendar can = Calendar.getInstance();
        can.setTime(end);
        int ey = can.get(Calendar.YEAR);
        int em = can.get(Calendar.MONTH);

        can.setTime(start);
        return (ey - can.get(Calendar.YEAR)) * 12 + (em - can.get(Calendar.MONTH));
    }

    /**
     * 生成操作对象
     *
     * @param date 指定的时间
     * @return 返回一个操作对象
     */
    public static TimeOperation init(Date date) {
        date = date == null ? new Date() : date;
        return TimeOperation.init(date);
    }

    public static class TimeOperation {
        private Calendar calendar;

        private TimeOperation() {
        }

        public Calendar getCalendar() {
            return this.calendar;
        }

        public Date getDate() {
            Objects.requireNonNull(calendar);
            return this.getCalendar().getTime();
        }

        private static TimeOperation init(Date date) {
            Objects.requireNonNull(date);
            TimeOperation op = new TimeOperation();
            op.calendar = Calendar.getInstance();
            op.calendar.setTime(date);
            return op;
        }

        private static TimeOperation init() {
            return init(new Date());
        }

        public TimeOperation firstDay() {
            return add(Calendar.DAY_OF_MONTH, 1);
        }

        public TimeOperation lastDay() {
            return add(Calendar.DAY_OF_MONTH, this.calendar.getActualMaximum(Calendar.DATE));
        }

        public TimeOperation add(int field, int amount) {
            Objects.requireNonNull(calendar);
            this.calendar.add(field, amount);
            return this;
        }

        public TimeOperation earlyO() {
            return time(0, 0, 0, 0);
        }

        public TimeOperation lastO() {
            return time(23, 59, 59, 999);
        }

        public TimeOperation time(int hour, int minute, int second) {
            return time(hour, minute, second, 0);
        }

        public TimeOperation time(LocalTime time) {
            Objects.requireNonNull(time);
            return this.time(time.getHour(), time.getMinute(), time.getSecond(), 0);
        }

        public TimeOperation time(int hour, int minute, int second, int millisecond) {
            Objects.requireNonNull(calendar);
            this.calendar.set(Calendar.SECOND, second);
            this.calendar.set(Calendar.MINUTE, minute);
            this.calendar.set(Calendar.HOUR_OF_DAY, hour);
            this.calendar.set(Calendar.MILLISECOND, millisecond);
            return this;
        }
    }
}
