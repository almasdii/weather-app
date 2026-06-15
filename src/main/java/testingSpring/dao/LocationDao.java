package testingSpring.dao;

import testingSpring.entity.Location;

import java.util.UUID;

public class LocationDao implements Dao<UUID, Location>{
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
