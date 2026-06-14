package testingSpring.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = {"id"})
public class Location{
    private UUID id;
    private String name;
    private UUID userId;
    private BigDecimal latitube;
    private BigDecimal longitube;

    public Location(String name, UUID userId, BigDecimal latitube, BigDecimal longitube) {
        this.name = name;
        this.userId = userId;
        this.latitube = latitube;
        this.longitube = longitube;
    }
}
