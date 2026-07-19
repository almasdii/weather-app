package testingSpring.mapper;

import testingSpring.dto.LocationDetailsView;
import testingSpring.util.LocationParameters;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ValueDeserializer;


public class CustomLocationDeserializer extends ValueDeserializer<LocationDetailsView> {

    @Override
    public LocationDetailsView deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {


        JsonNode jsonNode = ctxt.readTree(p);
        JsonNode main = jsonNode.get(LocationParameters.MAIN);
        JsonNode weather = jsonNode.get(LocationParameters.WEATHER).get(0);
        String country = jsonNode.get(LocationParameters.SYS).get(LocationParameters.COUNTRY).asString();

        String icon = weather.get(LocationParameters.ICON).asString();
        Integer humidity = main.get(LocationParameters.HUMIDITY).asInt();
        double feelsLike = main.get(LocationParameters.FEELS_LIKE).asDouble();
        double temp = main.get(LocationParameters.TEMP).asDouble();

        String name = jsonNode.get(LocationParameters.NAME).asString();
        String description = weather.get(LocationParameters.DESCRIPTION).asString();

        return new LocationDetailsView(temp, feelsLike, name, humidity, description, country, icon);
    }
}
