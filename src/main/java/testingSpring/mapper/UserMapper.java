package testingSpring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import testingSpring.dto.UserLoginRequest;
import testingSpring.dto.UserRegisterRequest;
import testingSpring.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserLoginRequest userToUserLoginRequest(User user);
    User userLoginRequestToUser(UserLoginRequest userLoginRequest);

    @Mapping(target = "rePassword",source = "user.password")
    UserRegisterRequest userToUserRegisterRequest(User user);
    User userRegisterRequestToUser(UserRegisterRequest userRegisterRequest);
}
