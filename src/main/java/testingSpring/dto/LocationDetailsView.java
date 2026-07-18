package testingSpring.dto;

import testingSpring.mapper.CustomLocationDeserializer;
import tools.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = CustomLocationDeserializer.class)
public record LocationDetailsView(
        Double temp,
        Double feelsLike,
        String name,
        Double lat,
        Double lon,
        Integer humidity,
        String description
) {
}
