package com.common.core.resolver;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author rends
 * @date 2019/11/2
 **/
public class LocalDateSerializer extends JsonSerializer<LocalDate> {
    private static final String YMD = "yyyy-MM-dd";

    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        if (localDate == null) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeObject(DateTimeFormatter.ofPattern(YMD).format(localDate));
        }
    }
}
