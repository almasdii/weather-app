package weather.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import weather.entity.Location;
import weather.entity.WeatherSession;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
public class LocationDao implements Dao<Location, Long> {
    private static final String FIND_BY_USER_ID_QUERY = """
            SELECT l
            FROM Location l
            JOIN l.user u
            WHERE u.id = :user_id
            """;
    private static final String DELETE_BY_LAT_AND_LON_QUERY = """
            DELETE 
            FROM Location l 
            WHERE l.latitube = :latitube
            AND l.longitube = :longitube
            """;
    private static final String USER_ID_PLACEHOLDER = "user_id";
    private static final String NAME_PLACEHOLDER = "name";
    private static final String LATITUBE_PLACEHOLDER = "latitube";
    private static final String LONGITUBE_PLACEHOLDER = "longitube";
    private final SessionFactory factory;

    @Autowired
    public LocationDao(SessionFactory factory) {
        this.factory = factory;
    }

    public List<Location> findByUserId(Long id) {
        Session currentSession = factory.getCurrentSession();
        return currentSession.createQuery(FIND_BY_USER_ID_QUERY, Location.class)
                .setParameter(USER_ID_PLACEHOLDER, id)
                .list();
    }

    @Override
    public Optional<Location> findById(Long id) {
        Session currentSession = factory.getCurrentSession();
        return Optional.ofNullable(currentSession.find(Location.class, id));
    }

    @Override
    public Location save(Location entity) {
        Session currentSession = factory.getCurrentSession();
        currentSession.persist(entity);
        return entity;
    }


    @Override
    public void remove(Long id) {
        Session currentSession = factory.getCurrentSession();
        Location location = currentSession.find(Location.class, id);
        log.debug("location to delete : {} ",location.getName());
        currentSession.remove(location);
    }


    public void removeByLatAndLon(Double lat,Double lon) {
        Session currentSession = factory.getCurrentSession();
        currentSession.createMutationQuery(DELETE_BY_LAT_AND_LON_QUERY)
                .setParameter(LATITUBE_PLACEHOLDER, lat)
                .setParameter(LONGITUBE_PLACEHOLDER,lon)
                .executeUpdate();
        currentSession.clear();
    }
}
