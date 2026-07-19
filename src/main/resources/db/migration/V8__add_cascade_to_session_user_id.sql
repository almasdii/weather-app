ALTER TABLE weather_schema.weather_session
drop constraint weather_session_user_id_fkey;


ALTER TABLE weather_schema.weather_session
ADD CONSTRAINT weather_session_user_id_fkey
    FOREIGN KEY (user_id) REFERENCES weather_user(id)
        ON DELETE CASCADE