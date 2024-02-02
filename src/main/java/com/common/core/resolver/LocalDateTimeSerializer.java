package com.common.core.resolver;

import java.io.IOException;
import java.time.LocalDateTime;

import com.common.core.util.Constants;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author rends
 * @date 2019/11/2
 **/
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        if (dateTime == null) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeObject(Constants.YMDHMS.format(dateTime));
        }
    }
}
