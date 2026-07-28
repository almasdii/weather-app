package weather.dto;

public record LocationDetailsView(
        Double temp,
        Double feelsLike,
        Integer humidity,
        String description,
        String country,
        String icon,
        String name,
        Double lat,
        Double lon
) {
}
