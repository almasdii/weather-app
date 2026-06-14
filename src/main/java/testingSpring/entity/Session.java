package testingSpring.entity;

import lombok.*;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = {"id"})
public class Session{
    private UUID id;
    private UUID userId;
    private LocalTime localTime;
    public Session(UUID userId){
        this.userId = userId;
        localTime = LocalTime.now();
    }
}
