package com.tiagoborja.mescala_ai.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class DtoUtils {
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    public static <T, U> U genericMapper(T source, Class<U> targetClass){
        if(source == null) return null;
        try {
            String json = mapper.writeValueAsString(source);
            return mapper.readValue(json, targetClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

