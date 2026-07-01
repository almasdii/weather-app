package testingSpring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterRequest(
        @NotBlank(message = "login cannot be null")
        @Size(message = "login size must be between 8 and 30",min = 4, max = 20)
        @Pattern(regexp = "^[A-Z][a-z]{5,20}$", message = "format = ^[A-Z][a-z]{5,20}")
        String login,
        @NotBlank
        @Size(message = "Password size must be between 8 and 30",min = 7, max = 30)
        String password,
        @NotBlank
        @Size(message = "Password size must be between 8 and 30",min =8, max = 30)
        String rePassword
)
{}
