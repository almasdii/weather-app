package testingSpring.entity;

import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = {"id"})
public class User{
    private UUID id;
    private String login;
    private String password;
    public User(String login,String password){
        this.login = login;
        this.password = password;
    }
}
