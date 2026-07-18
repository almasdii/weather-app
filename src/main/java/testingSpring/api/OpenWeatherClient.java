package testingSpring.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import testingSpring.dto.LocationDetailsView;
import testingSpring.dto.LocationResponse;
import testingSpring.dto.LocationSearchView;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
@Component
public class OpenWeatherClient {
    private final HttpClient httpClient;
    private final ObjectMapper mapper;
    private  final Properties weatherApiProperties;

    @Autowired
    public OpenWeatherClient(HttpClient httpClient, ObjectMapper mapper, Properties weatherApiProperties) {
        this.httpClient = httpClient;
        this.mapper = mapper;
        this.weatherApiProperties = weatherApiProperties;
    }

    public List<LocationDetailsView> findAll(List<LocationResponse> locationResponses) {

        List<LocationDetailsView> locationDetailsViewsList = new ArrayList<>();
        for(LocationResponse locationResponse: locationResponses){

            String url = String.format(weatherApiProperties.getProperty("api_key_lat_lon")
                    ,locationResponse.lat(),locationResponse.lon());
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> send = null;
            try {
                send = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            LocationDetailsView locationDetailsView = mapJson(send.body());
            locationDetailsViewsList.add(locationDetailsView);
        }
        return locationDetailsViewsList;
    }


    public LocationDetailsView mapJson(String string){
        JsonNode jsonNode = mapper.readTree(string);
        JsonNode main = jsonNode.get("main");
        JsonNode weather = jsonNode.get("weather").get(0);
        JsonNode coord = jsonNode.get("coord");

        Integer humidity = main.get("humidity").asInt();
        double feelsLike = main.get("feels_like").asDouble();
        double temp = main.get("temp").asDouble();

        String name = jsonNode.get("name").asString();
        String description = weather.get("description").asString();
        double lat = coord.get("lat").asDouble();
        double lon = coord.get("lon").asDouble();

        return new LocationDetailsView(temp, feelsLike, name, lat, lon, humidity, description);

    }

    public List<LocationSearchView> search(String name) throws IOException, InterruptedException {
        String format = String.format(weatherApiProperties.getProperty("api_key_filter"), name);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(format))
                .GET()
                .build();
        HttpResponse<String> send = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return mapJsonSearch(send.body());
    }

    public List<LocationSearchView> mapJsonSearch(String json){
        return mapper.readValue(json, new TypeReference<List<LocationSearchView>>() {
        });
    }
}
