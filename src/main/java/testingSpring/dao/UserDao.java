package testingSpring.dao;

import org.hibernate.SessionFactory;
import testingSpring.entity.User;

import java.util.UUID;

public class UserDao implements Dao<UUID, User>{
    private SessionFactory sessionFactory;
    @Override
    public User save(User user) {
        sessionFactory.getCurrentSession();
        return null;
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
}
