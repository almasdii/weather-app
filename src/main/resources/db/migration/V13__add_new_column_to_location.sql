ALTER TABLE weather_location
ADD COLUMN country varchar NOT NULL;

ALTER TABLE weather_location
    ADD COLUMN state varchar NOT NULL;
