package testingSpring.mapper;

import lombok.extern.slf4j.Slf4j;
import testingSpring.dto.LocationApiResponse;
import testingSpring.util.LocationParameters;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ValueDeserializer;


@Slf4j
public class CustomLocationDeserializer extends ValueDeserializer<LocationApiResponse> {

    @Override
    public LocationApiResponse deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {


        JsonNode jsonNode = ctxt.readTree(p);
        JsonNode main = jsonNode.get(LocationParameters.MAIN);
        JsonNode weather = jsonNode.get(LocationParameters.WEATHER).get(0);

        String icon = weather.get(LocationParameters.ICON).asString();
        Integer humidity = main.get(LocationParameters.HUMIDITY).asInt();
        double feelsLike = main.get(LocationParameters.FEELS_LIKE).asDouble();
        double temp = main.get(LocationParameters.TEMP).asDouble();
//
//        String name = jsonNode.get(LocationParameters.NAME).asString();
        String description = weather.get(LocationParameters.DESCRIPTION).asString();

        return new LocationApiResponse(temp, feelsLike, humidity, description, icon);
    }
}
