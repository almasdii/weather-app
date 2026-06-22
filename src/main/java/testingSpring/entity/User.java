package testingSpring.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = {"id"})
@Entity
@Table(name = "weather_user")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login",nullable = false,unique = true)
    private String login;

    @Column(name = "password",nullable = false,unique = true)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Location> locations;

    public User(String login,String password){
        this.login = login;
        this.password = password;
    }
}
