package testingSpring.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import testingSpring.entity.Location;
import testingSpring.exception.DataBaseException;

import java.util.List;
import java.util.UUID;

@Component
public class LocationDao implements Dao<UUID, Location> {
    private static final String FIND_BY_USER_ID_QUERY = """
            SELECT l
            FROM Location l
            JOIN l.user u
            WHERE u.id = :user_id
            """;
    private static final String USER_ID = "user_id";

    private final SessionFactory factory;

    @Autowired
    public LocationDao(SessionFactory factory) {
        this.factory = factory;
    }

    public List<Location> findByUserId(Long id) {
        try {
            Session currentSession = factory.getCurrentSession();
            currentSession.beginTransaction();
            List<Location> list = currentSession.createQuery(FIND_BY_USER_ID_QUERY, Location.class)
                    .setParameter(USER_ID, id)
                    .list();
            currentSession.getTransaction().commit();
            return list;
        }catch (HibernateException exception){
            throw new DataBaseException(exception);
        }
    }

    @Override
    public Location save(Location user) {
        return null;
    }

    @Override
    public Location find(UUID uuid) {
        return null;
    }


    @Override
    public boolean update(Location user) {
        return false;
    }


    @Override
    public boolean delete(UUID uuid) {
        return false;
    }


}
