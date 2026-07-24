package testingSpring.dto;

import testingSpring.mapper.CustomLocationDeserializer;
import tools.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = CustomLocationDeserializer.class)
public record LocationApiResponse(
        Double temp,
        Double feelsLike,
        Integer humidity,
        String description,
        String icon
) {}
