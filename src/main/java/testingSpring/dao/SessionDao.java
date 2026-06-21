package testingSpring.dao;

import testingSpring.entity.WeatherSession;

import java.util.UUID;

public class SessionDao implements Dao<UUID, WeatherSession>{
    @Override
    public WeatherSession save(WeatherSession user) {
        return null;
    }

    @Override
    public WeatherSession find(UUID uuid) {
        return null;
    }

    @Override
    public boolean update(WeatherSession user) {
        return false;
    }

    @Override
    public boolean delete(UUID uuid) {
        return false;
    }
}
