package testingSpring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import testingSpring.entity.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserDao{
    private final SessionFactory sessionFactory;

    public static final String FIND_BY_LOGIN = """
            SELECT u 
            FROM User u 
            WHERE u.login = :login
            """;
    private static final String LOGIN = "login";

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
}
