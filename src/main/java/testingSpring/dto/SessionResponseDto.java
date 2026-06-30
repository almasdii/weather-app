package testingSpring.dto;

import java.util.UUID;

public record SessionResponseDto(
        UUID id,
        Long userId
) {
}
