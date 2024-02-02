package com.common.core.resolver;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Objects;

import com.common.core.util.Constants;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * @author rends
 * @date 2019/11/2
 **/
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    private static final String TIME_ZONE_TAG = "T";
    private static final String NOT_TIMESTAMP_TAG = "-";
    /**
     * 日期时间字节长度
     */
    private static final int DATE_TIME_BYTE_LENGTH = 14;


    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String source = p.getText().trim();
        if (Objects.equals(Constants.EMPTY_STR, source)) {
            return null;
        }
        if (source.contains(TIME_ZONE_TAG)) {
            return LocalDateTime.parse(source);
        }
        if (!source.contains(NOT_TIMESTAMP_TAG)) {
            long timestamp = Long.parseLong(source);
            return LocalDateTime.ofEpochSecond(timestamp / 1000, (int) (timestamp % 1000), ZoneOffset.ofHours(8));
        }
        if (source.length() > DATE_TIME_BYTE_LENGTH) {
            return LocalDateTime.parse(source, Constants.YMDHMS);
        } else {
            return LocalDateTime.of(LocalDate.parse(source, Constants.YMD), LocalTime.MIN);
        }
    }
}
