package com.common.core.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.core.resolver.LocalDateDeserializer;
import com.common.core.resolver.LocalDateSerializer;
import com.common.core.resolver.LocalDateTimeDeserializer;
import com.common.core.resolver.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @author rends
 * @date 2019/11/2
 **/
public class JsonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static Map<String, JavaType> javaListTypeMap = new HashMap<>(32);
    private static final JavaType MAP_JAVA_TYPE;
    private static final JavaType LIST_MAP_TYPE;

    static {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        simpleModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        MAPPER.registerModule(simpleModule);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.FALSE);
        MAPPER.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, Boolean.TRUE);
        MAP_JAVA_TYPE = MAPPER.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class);
        LIST_MAP_TYPE = MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, MAP_JAVA_TYPE);
    }

    public static ObjectMapper get() {
        return MAPPER;
    }

    public static byte[] toBytes(Object ob) {
        if (ob == null) {
            return null;
        }
        try {
            return MAPPER.writeValueAsBytes(ob);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static String toJsonString(Object ob) {
        if (ob == null) {
            return null;
        }
        try {
            return MAPPER.writeValueAsString(ob);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static <T> T parse(String json, Class<T> type) {
        if (Util.isBlank(json)) {
            return null;
        }
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static <T> T parse(String json, JavaType javaType) {
        if (Util.isBlank(json)) {
            return null;
        }
        try {
            return MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            ;
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static Map<String, Object> parse(String json) {
        return parse(json, MAP_JAVA_TYPE);
    }

    public static <E> List<E> parseAsList(String json, Class<E> type) {
        if (Util.isBlank(json)) {
            return null;
        }
        if (javaListTypeMap.get(type.getName()) == null) {
            javaListTypeMap.put(type.getName(), MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, type));
        }
        try {
            return MAPPER.readValue(json, javaListTypeMap.get(type.getName()));
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static List<Map<String, Object>> parseAsList(String json) {
        if (Util.isBlank(json)) {
            return null;
        }
        try {
            return MAPPER.readValue(json, LIST_MAP_TYPE);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
