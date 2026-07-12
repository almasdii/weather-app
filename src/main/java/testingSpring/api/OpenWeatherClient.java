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

@Slf4j
@Component
public class OpenWeatherClient {
    private final HttpClient httpClient;
    private final ObjectMapper mapper;

    @Autowired
    public OpenWeatherClient(HttpClient httpClient, ObjectMapper mapper) {
        this.httpClient = httpClient;
        this.mapper = mapper;
    }

    public List<LocationDetailsView> findAll(List<LocationResponse> locationResponses) throws IOException, InterruptedException {

        List<LocationDetailsView> locationDetailsViewsList = new ArrayList<>();
        for(LocationResponse locationResponse: locationResponses){

            String url = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%.5f&lon=%.5f&appid=d5c6861bc694bf12e0d19183e6e07195"
                    ,locationResponse.lat(),locationResponse.lon());
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> send = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            LocationDetailsView locationDetailsView = mapJson(send.body());
            locationDetailsViewsList.add(locationDetailsView);
        }
        return locationDetailsViewsList;
    }


    public LocationDetailsView mapJson(String string){
        JsonNode jsonNode = mapper.readTree(string);
        JsonNode main = jsonNode.get("main");
        JsonNode weather = jsonNode.get("weather");
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
        String format = String.format("https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=10&appid=d5c6861bc694bf12e0d19183e6e07195", name);
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
