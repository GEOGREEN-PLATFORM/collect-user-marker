package com.example.collect_user_marker.converter;

import com.example.collect_user_marker.model.image.ImageDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class ImageListConverterTest {

    private final ImageListConverter converter = new ImageListConverter();

    @Test
    @DisplayName("convertToDatabaseColumn should return valid JSON")
    void testConvertToDatabaseColumn() {
        ImageDTO image = new ImageDTO(UUID.randomUUID(), UUID.randomUUID());
        List<ImageDTO> images = List.of(image);

        String json = converter.convertToDatabaseColumn(images);

        assertThat(json).isNotBlank();
        assertThat(json).contains(image.getFullImageId().toString());
    }

    @Test
    @DisplayName("convertToEntityAttribute should parse JSON correctly")
    void testConvertToEntityAttribute() {
        UUID imageId = UUID.randomUUID();
        UUID previewId = UUID.randomUUID();
        String json = "[{\"fullImageId\":\"" + imageId + "\",\"previewImageId\":\"" + previewId + "\"}]";

        List<ImageDTO> result = converter.convertToEntityAttribute(json);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFullImageId()).isEqualTo(imageId);
        assertThat(result.get(0).getPreviewImageId()).isEqualTo(previewId);
    }

    @Test
    @DisplayName("convertToDatabaseColumn should return null on null input")
    void testConvertToDatabaseColumnNull() {
        String result = converter.convertToDatabaseColumn(null);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("convertToEntityAttribute should return empty list on null or empty input")
    void testConvertToEntityAttributeNullOrEmpty() {
        assertThat(converter.convertToEntityAttribute(null)).isEmpty();
        assertThat(converter.convertToEntityAttribute("")).isEmpty();
    }

    @Test
    @DisplayName("convertToEntityAttribute should throw on invalid JSON")
    void testConvertToEntityAttributeInvalidJson() {
        assertThatThrownBy(() -> converter.convertToEntityAttribute("invalid json"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error converting JSON to ImageDTO list");
    }
}
