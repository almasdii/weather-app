package weather.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import weather.dto.LocationApiResponse;
import weather.dto.LocationSearchView;
import weather.exception.WeatherApiConnectionException;
import weather.exception.WeatherApiParseException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Slf4j
@Component
public class OpenWeatherClient {
    private final HttpClient httpClient;
    private final ObjectMapper mapper;
    private final Properties weatherApiProperties;

    @Autowired
    public OpenWeatherClient(HttpClient httpClient, ObjectMapper mapper,@Qualifier("weatherApiProperties") Properties weatherApiProperties) {
        this.httpClient = httpClient;
        this.mapper = mapper;
        this.weatherApiProperties = weatherApiProperties;
    }

    public List<LocationSearchView> searchByName(String name) {
        String format = String.format(weatherApiProperties.getProperty("api_key_filter"), name);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(format))
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            List<LocationSearchView> locationSearchViews = mapToLocationSearchView(response.body());
            Optional<String> allLocationsName = locationSearchViews.stream().map(LocationSearchView::name).reduce((a, b) -> {
                return a + " " + b;
            });
            log.debug("All searched locations : {} ",allLocationsName);
            return locationSearchViews;
        } catch (IOException e) {
            throw new WeatherApiParseException("Error occurred while parsing json from",e);
        }catch (InterruptedException e){
            throw new WeatherApiConnectionException("Error while sending request to search locations with name : " + name,e);
        }
    }

    public List<LocationSearchView> mapToLocationSearchView(String json){
        return mapper.readValue(json, new TypeReference<List<LocationSearchView>>() {
        });
    }

    public LocationApiResponse searchByLatAndLon(Double lat, Double lon) {
        String url = String
                .format(weatherApiProperties.getProperty("api_key_lat_lon"),lat,lon);
        log.debug("url to search by lat and lon : {}",url);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            LocationApiResponse locationApiResponse = mapper.readValue(response.body(), LocationApiResponse.class);

            return locationApiResponse;
        } catch (IOException e) {
            throw new WeatherApiParseException("ERROR occurred while parsing json",e);
        }catch (InterruptedException e){
            log.warn("ERROR occurred while sending request failed to connect to find All users locations using lat and lot",e);
            throw new WeatherApiConnectionException("failed to send request ",e);
        }
    }
}
