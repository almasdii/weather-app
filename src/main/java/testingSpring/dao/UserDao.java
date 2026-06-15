package testingSpring.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import testingSpring.DataSource;
import testingSpring.entity.User;

import java.util.UUID;

@Repository
public class UserDao implements Dao<UUID, User>{
    private final DataSource dataSource;

    @Autowired
    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //    public static final String FIND_USER_BY_NAME = """
//            SELECT
//            """;
    @Override
    public User save(User user) {
        Session currentSession = dataSource.getCurrentSession();
        currentSession.persist(user);
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

//    public User findByLogin(String name){
//        Session currentSession = sessionFactory.getCurrentSession();
//        List<User> list = currentSession.createQuery(FIND_USER_BY_NAME, User.class).list();
//
//    }
}
