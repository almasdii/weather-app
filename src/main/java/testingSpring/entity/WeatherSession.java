package testingSpring.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"})
@Table(name = "weather_session")
@Entity
public class WeatherSession {
    @Id
    private UUID id;
    @Column(name = "user_id",nullable = false,updatable = false)
    private Long userId;
    @Column(name = "expires_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;

    public WeatherSession(UUID id,Long userId){
        this.id = id;
        this.userId = userId;
        createdAt = LocalDateTime.now();
    }
}
