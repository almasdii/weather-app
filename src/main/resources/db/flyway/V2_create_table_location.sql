CREATE TABLE weather_location(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR NOT NULL UNIQUE ,
    user_id BIGINT REFERENCES weather_users(id),
    latitube DECIMAL(10,5) CHECK ( latitube > 0 ),
    longitube DECIMAL(10,2) check ( longitube > 0 )
)