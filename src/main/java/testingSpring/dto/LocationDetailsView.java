package testingSpring.dto;

public record LocationDetailsView(
        Double temp,
        Double feelsLike,
        String name,
        String state,
        Double lat,
        Double lon
) {
}
