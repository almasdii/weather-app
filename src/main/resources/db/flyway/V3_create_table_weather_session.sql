CREATE TABLE weather_session(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY ,
    user_id BIGINT REFERENCES weather_users(id),
    expires_at TIMESTAMP
)