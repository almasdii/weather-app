package testingSpring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import testingSpring.dto.UserLoginDto;
import testingSpring.dto.UserRegisterDto;
import testingSpring.entity.User;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    UserLoginDto toUserLoginDto(User user);
    User toUser(UserLoginDto userLoginDto);

    @Mapping(target = "rePassword",source = "user.password")
    UserRegisterDto toUserRegisterDto(User user);
    User toUser(UserRegisterDto userRegisterDto);
}
