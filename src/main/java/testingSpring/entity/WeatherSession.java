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
@Table(name = "weather_session",schema = "weather_schema")
@Entity
public class WeatherSession {
    @Id
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @Column(name = "expires_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;

    public WeatherSession(UUID id,User user){
        this.id = id;
        this.user = user;
        createdAt = LocalDateTime.now();
    }
}
