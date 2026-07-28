ALTER TABLE weather_location
ADD CONSTRAINT weather_location_unique_lat_lon UNIQUE (latitube,longitube);