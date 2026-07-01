package testingSpring.mapper;

import org.mapstruct.Mapper;
import testingSpring.dto.SessionLoginResponse;
import testingSpring.entity.WeatherSession;
@Mapper(componentModel = "spring")
public interface SessionMapper {
    SessionLoginResponse sessionToSessionLoginResponse(WeatherSession session);
    WeatherSession sessionLoginResponseToSession(SessionLoginResponse sessionLoginResponse);
}

