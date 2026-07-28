package weather.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import weather.dto.UserLoginRequest;
import weather.dto.UserRegisterRequest;
import weather.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserLoginRequest userToUserLoginRequest(User user);
    User userLoginRequestToUser(UserLoginRequest userLoginRequest);

    @Mapping(target = "rePassword",source = "user.password")
    UserRegisterRequest userToUserRegisterRequest(User user);
    User userRegisterRequestToUser(UserRegisterRequest userRegisterRequest);
}
