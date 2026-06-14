package testingSpring.dao;

import java.util.UUID;

public class LocationDao implements Dao<UUID,LocationDao>{
    @Override
    public LocationDao save(LocationDao user) {
        return null;
    }

    @Override
    public LocationDao find(UUID uuid) {
        return null;
    }

    @Override
    public boolean update(LocationDao user) {
        return false;
    }

    @Override
    public boolean delete(UUID uuid) {
        return false;
    }
}
