package testingSpring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserLoginRequest(
        @NotBlank(message = "login cannot be null")
        @Size(message = "login size must be between 8 and 30",min = 5, max = 20)
        @Pattern(regexp = "^A-Z[a-z]{5,20}")
        String login,
        @NotBlank(message = "Password cannot be null")
        @Size(message = "Password size must be between 8 and 30",min = 8, max = 30)
        String password
) {
}
