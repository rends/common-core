package com.common.core.resolver;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.common.core.util.Util;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * @author rends
 * @date 2019/11/2
 **/
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    private static final String YMD = "yyyy-MM-dd";


    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String source = p.getText().trim();
        if (Util.isBlank(source)) {
            return null;
        }
        return LocalDate.parse(source, DateTimeFormatter.ofPattern(YMD));
    }
}
