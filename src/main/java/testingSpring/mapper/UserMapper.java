package testingSpring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import testingSpring.dto.UserCredentialsRequest;
import testingSpring.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserCredentialsRequest userToUserRegisterRequest(User user);
    User userRegisterRequestToUser(UserCredentialsRequest userCredentialsRequest);
}
