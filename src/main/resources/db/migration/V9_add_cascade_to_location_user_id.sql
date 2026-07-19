ALTER TABLE weather_schema.weather_location
DROP CONSTRAINT weather_location_user_id_fkey;

ALTER TABLE weather_schema.weather_location
ADD CONSTRAINT weather_location_user_id_fkey
    FOREIGN KEY(user_id) REFERENCES weather_user(id)
        ON DELETE CASCADE;