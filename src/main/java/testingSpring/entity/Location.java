package testingSpring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.lang.annotation.Repeatable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = {"id"})
@Table(name = "weather_location")
@Entity
public class Location{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String name;
    private UUID userId;
    private BigDecimal latitube;
    private BigDecimal longitube;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Location(String name, UUID userId, BigDecimal latitube, BigDecimal longitube) {
        this.name = name;
        this.userId = userId;
        this.latitube = latitube;
        this.longitube = longitube;
    }
}
