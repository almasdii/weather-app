package testingSpring.dto;

import java.util.UUID;

public record SessionDto(
        UUID id,
        Long userId
) {
}
