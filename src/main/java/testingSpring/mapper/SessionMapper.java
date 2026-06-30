package testingSpring.mapper;

import org.mapstruct.Mapper;
import testingSpring.dto.SessionResponseDto;
import testingSpring.entity.WeatherSession;
@Mapper(componentModel = "spring")
public interface SessionMapper {
    SessionResponseDto toSessionDto(WeatherSession session);
    WeatherSession toWeatherSession(SessionResponseDto sessionResponseDto);
}

