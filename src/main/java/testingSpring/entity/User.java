package testingSpring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = {"id"})
@Entity
@Table(name = "weather_user")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String login;
    private String password;
    public User(String login,String password){
        this.login = login;
        this.password = password;
    }
}
