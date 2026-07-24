package testingSpring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import testingSpring.dto.LocationApiResponse;
import testingSpring.dto.LocationResponse;
import testingSpring.entity.Location;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    @Mapping(target = "lon",source = "longitube")
    @Mapping(target = "lat",source = "latitube")
    LocationResponse locationToLocationResponse(Location location);
    List<LocationResponse> locationListToLocationResponseList(List<Location> locations);

    LocationApiResponse locationToLocationDetailsView(Location location);

}
