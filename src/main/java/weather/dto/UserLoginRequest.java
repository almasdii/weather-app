package weather.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequest(
        @NotBlank(message = "login cannot be null")
        @Size(message = "login size must be between 4 and 30",min = 4, max = 20)
        String login,
        @NotBlank(message = "Password cannot be null")
        @Size(message = "Password size must be between 5 and 30",min = 5, max = 30)
        String password
) {
}
