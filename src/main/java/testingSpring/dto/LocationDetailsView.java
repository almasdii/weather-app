package testingSpring.dto;

import testingSpring.mapper.CustomLocationDeserializer;
import tools.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = CustomLocationDeserializer.class)
public record LocationDetailsView(
        Double temp,
        Double feelsLike,
        String name,
        Integer humidity,
        String description,
        String country,
        String icon
) {
}
