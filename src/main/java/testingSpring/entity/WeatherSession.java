package testingSpring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = {"id"})
@Table(name = "weather_session")
@Entity
public class WeatherSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private LocalTime localTime;

    public WeatherSession(Long userId){
        this.userId = userId;
        localTime = LocalTime.now();
    }
}
