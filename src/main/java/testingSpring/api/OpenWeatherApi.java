package testingSpring.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import testingSpring.dto.LocationDetailsView;
import testingSpring.dto.LocationResponse;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class OpenWeatherApi {
    private final HttpClient httpClient;
    private final ObjectMapper mapper;

    @Autowired
    public OpenWeatherApi(HttpClient httpClient, ObjectMapper mapper) {
        this.httpClient = httpClient;
        this.mapper = mapper;
    }

    public List<LocationDetailsView> findAll(List<LocationResponse> locationResponses) throws IOException, InterruptedException {
        List<LocationDetailsView> locationDetailsViews = new ArrayList<>();
        for(LocationResponse locationResponse: locationResponses){
            String url = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%.5f&lon=%.5f&appid=d5c6861bc694bf12e0d19183e6e07195"
                    ,locationResponse.lat(),locationResponse.lon());
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> send = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            jsonMapper(send.body());
        }
    }
    public LocationDetailsView jsonMapper(String string){
        JsonNode jsonNode = mapper.readTree(string);
        JsonNode main = jsonNode.get("main");
        double humidity = main.get("humidity").asDouble();
        double feelsLike = main.get("feels_like").asDouble();
        double temp = main.get("temp").asDouble();

        String name = jsonNode.get("name").asString();

    }
}
