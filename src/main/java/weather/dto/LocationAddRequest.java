package weather.dto;

import jakarta.validation.constraints.NotBlank;

public record LocationAddRequest(
        @NotBlank(message = "latitube cant be null")
        Double lat,
        @NotBlank(message = "latitube cant be null")
        Double lon,
        @NotBlank(message = "latitube cant be null")
        String name,
        @NotBlank(message = "latitube cant be null")
        String country
) {
}
