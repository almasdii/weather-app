package testingSpring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import testingSpring.dto.UserLoginRequest;
import testingSpring.dto.UserRegisterRequest;
import testingSpring.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserLoginRequest toUserLoginDto(User user);
    User toUser(UserLoginRequest userLoginRequest);

    @Mapping(target = "rePassword",source = "user.password")
    UserRegisterRequest toUserRegisterDto(User user);
    User toUser(UserRegisterRequest userRegisterRequest);
}
