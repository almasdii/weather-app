package testingSpring.dto;

public record LocationSearchView(
        Double lat,
        Double lon,
        String name,
        String country,
        String state
) {
}
