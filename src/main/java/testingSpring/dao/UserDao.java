package testingSpring.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import testingSpring.entity.User;
import testingSpring.exception.DataBaseException;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserDao implements Dao<User, Long> {
    private final SessionFactory sessionFactory;

    private static final String FIND_BY_LOGIN_QUERY = """
            SELECT u 
            FROM User u 
            WHERE u.login = :login
            """;
    private static final String FIND_BY_SESSION_ID_QUERY = """
            SELECT u
            FROM User u
            JOIN WeatherSession w ON u.id = w.user.id
            WHERE w.id = :sessionId
            """;

    private static final String FIND_BY_LOGIN_AND_PASSWORD_QUERY = """
            SELECT u 
            FROM User u 
            WHERE u.login = :login
            AND u.password = :password
            """;
    private static final String LOGIN_PLACEHOLDER = "login";
    private static final String SESSION_PLACEHOLDER = "sessionId";
    private static final String PASSWORD_PLACEHOLDER = "password";

    @Autowired
    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User save(User entity) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(entity);
        return entity;
    }

    @Override
    public Optional<User> findById(Long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        User user = currentSession.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public void remove(Long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        User user = currentSession.find(User.class, id);
        currentSession.remove(user);
    }

    public Optional<User> findByLogin(String loginName) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession
                .createQuery(FIND_BY_LOGIN_QUERY, User.class)
                .setParameter(LOGIN_PLACEHOLDER, loginName)
                .uniqueResultOptional();
    }
    public Optional<User> findBySessionId(UUID userSession) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            return currentSession.createQuery(FIND_BY_SESSION_ID_QUERY, User.class)
                    .setParameter(SESSION_PLACEHOLDER, userSession)
                    .uniqueResultOptional();
        } catch (HibernateException exception) {
            throw new DataBaseException(exception);
        }
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        Session currentSession = sessionFactory.getCurrentSession();
        Optional<User> user = currentSession
                .createQuery(FIND_BY_LOGIN_AND_PASSWORD_QUERY, User.class)
                .setParameter(LOGIN_PLACEHOLDER, login)
                .setParameter(PASSWORD_PLACEHOLDER, password)
                .uniqueResultOptional();
        return user;
    }

}
