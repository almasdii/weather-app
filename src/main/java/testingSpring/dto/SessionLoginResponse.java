package testingSpring.dto;

import java.util.UUID;

public record SessionLoginResponse(
        UUID id,
        Long userId
) {
}
