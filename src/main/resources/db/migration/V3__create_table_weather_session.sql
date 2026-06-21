CREATE TABLE weather_session(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY ,
    user_id BIGINT REFERENCES weather_schema.weather_user(id),
    expires_at TIMESTAMP
)