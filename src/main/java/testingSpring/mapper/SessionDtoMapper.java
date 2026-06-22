package testingSpring.mapper;

import org.mapstruct.Mapper;
import testingSpring.dto.SessionDto;
import testingSpring.entity.WeatherSession;
@Mapper(componentModel = "spring")
public interface SessionDtoMapper {
    SessionDto toSessionDto(WeatherSession session);
    WeatherSession toWeatherSession(SessionDto sessionDto);
}
