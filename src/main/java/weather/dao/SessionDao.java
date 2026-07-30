package weather.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import weather.entity.WeatherSession;

import java.util.Optional;
import java.util.UUID;

@Repository
public class SessionDao implements Dao<WeatherSession, UUID> {
    private static final String REMOVE_BY_USER_ID_QUERY =
            """
                    DELETE 
                    FROM WeatherSession ws
                    WHERE ws.user.id = :user_id
                    """;
    private static final String USER_ID_PLACEHOLDER = "user_id";
    private final SessionFactory factory;

    @Autowired
    public SessionDao(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public WeatherSession save(WeatherSession entity) {
        Session currentSession = factory.getCurrentSession();
        currentSession.persist(entity);
        return entity;
    }

    @Override
    public Optional<WeatherSession> findById(UUID id) {
        Session currentSession = factory.getCurrentSession();
        WeatherSession session = currentSession.find(WeatherSession.class, id);
        return Optional.ofNullable(session);
    }


    public int removeByUserId(Long id) {
        Session currentSession = factory.getCurrentSession();
        int i = currentSession.createMutationQuery(REMOVE_BY_USER_ID_QUERY)
                .setParameter(USER_ID_PLACEHOLDER, id)
                .executeUpdate();
        currentSession.clear();
        return i;
    }

    @Override
    public void remove(UUID id) {
        Session currentSession = factory.getCurrentSession();
        WeatherSession session = currentSession.find(WeatherSession.class, id);
        currentSession.remove(session);
    }
}
