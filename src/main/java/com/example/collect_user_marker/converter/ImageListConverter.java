package com.example.collect_user_marker.converter;

import com.example.collect_user_marker.model.image.ImageDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Collections;
import java.util.List;

@Converter
public class ImageListConverter implements AttributeConverter<List<ImageDTO>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<ImageDTO> attribute) {
        try {
            if (attribute == null) {
                return null;
            }
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new RuntimeException("Error converting ImageDTO list to JSON", e);
        }
    }

    @Override
    public List<ImageDTO> convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null || dbData.isEmpty()) {
                return Collections.emptyList();
            }
            return objectMapper.readValue(dbData, new TypeReference<List<ImageDTO>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error converting JSON to ImageDTO list", e);
        }
    }
}