package testingSpring.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import testingSpring.entity.WeatherSession;
import testingSpring.exception.DataBaseException;
import testingSpring.serivce.SessionService;

import java.util.UUID;

@Component
public class SessionDao implements Dao<UUID, WeatherSession> {
    private static final String REMOVE_BY_USER_ID_QUERY =
            """
                    DELETE FROM WeatherSession ws
                    WHERE ws.userId = :user_id
                    """;
    private static final String USER_ID_PLACEHOLDER = "user_id";
    private final SessionFactory factory;

    @Autowired
    public SessionDao(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public WeatherSession save(WeatherSession session) {
        try{
            Session currentSession = factory.getCurrentSession();
            currentSession.beginTransaction();
            currentSession.persist(session);
            currentSession.getTransaction().commit();
            return session;
        }catch (HibernateException exception){
            throw new DataBaseException(exception);
        }

    }

    @Override
    public WeatherSession find(UUID uuid) {
        return null;
    }

    @Override
    public boolean update(WeatherSession session) {
        return false;
    }

    @Override
    public boolean delete(UUID uuid) {
        return false;
    }

    public void removeByUserId(Long id) {
        Session currentSession = factory.getCurrentSession();
        currentSession.beginTransaction();
        currentSession.createMutationQuery(REMOVE_BY_USER_ID_QUERY)
                .setParameter(USER_ID_PLACEHOLDER,id)
                .executeUpdate();
        currentSession.getTransaction().commit();
    }
}
