package testingSpring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import testingSpring.entity.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserDao implements Dao<UUID, User>{
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
    @Override
    public User save(User user) {

        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();
        currentSession.persist(user);
        currentSession.getTransaction().commit();
        return user;
    }

    @Override
    public User find(UUID uuid) {
        return null;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
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
