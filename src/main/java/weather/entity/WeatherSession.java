package weather.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weather.util.SessionParameters;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "weather_session")
@Entity
public class WeatherSession {
    @Id
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @Column(name = "expires_at",nullable = false,updatable = false)
    private LocalDateTime expires_at;

    public WeatherSession(UUID id,User user){
        this.id = id;
        this.user = user;
        expires_at = LocalDateTime.now().plusMinutes(SessionParameters.MAX_SESSION_MINUTES);
    }
}
