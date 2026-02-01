package com.james.LMS.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.HashMap;
import java.util.Map;
import lombok.SneakyThrows;

@Converter(autoApply = true)
public class TestConverter implements AttributeConverter<Map<String, Object>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {
        return objectMapper.writeValueAsString(attribute);
    }

    @SneakyThrows
    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        return objectMapper.readValue(dbData, HashMap.class);
    }
}
