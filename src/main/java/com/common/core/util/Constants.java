package com.common.core.util;


import java.time.format.DateTimeFormatter;

/**
 * @author rends
 * @date 2019/11/2
 **/
public class Constants {
    public static final DateTimeFormatter YMD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter YMDHMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final String EMPTY_STR = "";
}
