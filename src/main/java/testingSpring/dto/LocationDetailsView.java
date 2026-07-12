package testingSpring.dto;

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
