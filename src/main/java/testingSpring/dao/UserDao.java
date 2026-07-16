package testingSpring.dao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UserDao{
    private final SessionFactory sessionFactory;

    private static final String FIND_BY_LOGIN = """
            SELECT u 
            FROM User u 
            WHERE u.login = :login
            """;
    private static final String FIND_BY_SESSION_ID = """
            SELECT u
            FROM User u
            JOIN WeatherSession w ON u.id = w.userId
            WHERE w.id = :sessionId
            """;

    private static final String FIND_BY_LOGIN_AND_PASSWORD = """
            SELECT u 
            FROM User u 
            WHERE u.login = :login
            AND u.password = :password
            """;
    private static final String LOGIN = "login";
    private static final String SESSION = "sessionId";
    private static final String PASSWORD = "password";

    @Autowired
    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public User save(User user) {

        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();
        currentSession.persist(user);
        currentSession.getTransaction().commit();
        return user;
    }

    public Optional<User> find(Long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();
        User user = currentSession.find(User.class, id);
        currentSession.getTransaction().commit();
        return Optional.ofNullable(user);
    }

    public boolean update(User user) {
        return false;
    }

    public boolean delete(UUID uuid) {
        return false;
    }

    public Optional<User> findByLogin(String loginName){
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.getTransaction().begin();
        Optional<User> user = currentSession
                .createQuery(FIND_BY_LOGIN, User.class)
                .setParameter(LOGIN, loginName)
                .uniqueResultOptional();
        currentSession.getTransaction().commit();
        return user;
    }

    public Optional<User> findBySessionId(UUID userSession) {
        try{
            Session currentSession = sessionFactory.getCurrentSession();
            return currentSession.createQuery(FIND_BY_SESSION_ID, User.class)
                    .setParameter(SESSION, userSession)
                    .uniqueResultOptional();
        }catch (HibernateException exception){
            throw new DataBaseException(exception);
        }
    }

    public Optional<User> findByLoginAndPassword(String login,String password) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.getTransaction().begin();
        Optional<User> user = currentSession
                .createQuery(FIND_BY_LOGIN_AND_PASSWORD, User.class)
                .setParameter(LOGIN, login)
                .setParameter(PASSWORD,password)
                .uniqueResultOptional();
        currentSession.getTransaction().commit();
        return user;
    }

}
