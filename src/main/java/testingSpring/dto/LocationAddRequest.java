package testingSpring.dto;

public record LocationAddRequest(
        Double lat,
        Double lon,
        String name,
        String country
) {
}
