package testingSpring.dto;

import java.util.UUID;

public record SessionDto(
        UUID uuid,
        Long userId
) {
}
