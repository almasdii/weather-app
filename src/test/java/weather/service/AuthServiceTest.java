package weather.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import weather.config.TestConfig;
import weather.dao.SessionDao;
import weather.dao.UserDao;
import weather.dto.UserLoginRequest;
import weather.dto.UserRegisterRequest;
import weather.entity.User;
import weather.entity.WeatherSession;
import weather.exception.UserAlreadyExistException;
import weather.exception.UserNotFoundException;
import weather.util.SessionParameters;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SessionDao sessionDao;

    private UserRegisterRequest user;

    @BeforeEach
    void setAuthService() {
        user = new UserRegisterRequest("Almas", "Almas0224", "Almas0224");
    }

    @Test
    void shouldSaveUserWhenRegister() {
        authService.register(user);
        Optional<User> optionalUser = userDao.findByLogin(user.login());

        assertThat(optionalUser).isPresent();
    }

    @Test
    void shouldCreateSessionWhenSignIn() {
        authService.register(user);
        UserLoginRequest userLoginRequest = new UserLoginRequest(user.login(), user.password());
        UUID uuid = authService.signIn(userLoginRequest);

        Optional<WeatherSession> optionalWeatherSession = sessionDao.findById(uuid);

        assertThat(optionalWeatherSession).isPresent();
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenSignInWithIncorrectLogin() {
        UserLoginRequest userLoginRequest = new UserLoginRequest("mock", "mock");
        assertThatThrownBy(()-> authService.signIn(userLoginRequest)).isInstanceOf(UserNotFoundException.class).hasMessage("No user found with this login : " + userLoginRequest.login());
    }

    @Test
    void shouldRemoveSessionWhenLogout(){
        authService.register(user);
        UserLoginRequest userLoginRequest = new UserLoginRequest(user.login(), user.password());
        UUID uuid = authService.signIn(userLoginRequest);

        authService.logout(uuid);
        Optional<WeatherSession> optionalWeatherSession = sessionDao.findById(uuid);
        assertThat(optionalWeatherSession).isEmpty();
    }

    @Test
    void isAuthenticatedShouldReturnTrueAfterSignIn(){
        authService.register(user);
        UserLoginRequest userLoginRequest = new UserLoginRequest(user.login(), user.password());
        UUID uuid = authService.signIn(userLoginRequest);

        boolean authenticated = authService.isAuthenticated(uuid);
        assertThat(authenticated).isTrue();
    }

    @Test
    void isAuthenticatedShouldReturnFalseAfterLogout(){
        authService.register(user);
        UserLoginRequest userLoginRequest = new UserLoginRequest(user.login(), user.password());
        UUID uuid = authService.signIn(userLoginRequest);
        authService.logout(uuid);

        boolean authenticated = authService.isAuthenticated(uuid);
        assertThat(authenticated).isFalse();
    }

    @Test
    void shouldThrowExceptionWhenRegisterUserWithExistingName(){
        authService.register(user);

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest(user.login(), user.password(), user.rePassword());

        assertThatThrownBy(()-> authService.register(userRegisterRequest)).isInstanceOf(UserAlreadyExistException.class).hasMessage("User with " + userRegisterRequest.login() + " already exist");
    }

    @Test
    void sessionShouldExpiresAfterTime(){
        authService.register(user);

        UserLoginRequest userLoginRequest = new UserLoginRequest(user.login(),user.password());
        UUID uuid = authService.signIn(userLoginRequest);

        WeatherSession weatherSession = sessionDao.findById(uuid).get();

        weatherSession.setExpires_at(weatherSession.getExpires_at().minusMinutes(SessionParameters.MAX_SESSION_MINUTES));

        sessionDao.save(weatherSession);
        assertThat(authService.isAuthenticated(uuid)).isFalse();

    }


}
