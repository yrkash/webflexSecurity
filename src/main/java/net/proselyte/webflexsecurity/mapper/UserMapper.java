package net.proselyte.webflexsecurity.mapper;

import net.proselyte.webflexsecurity.dto.UserDto;
import net.proselyte.webflexsecurity.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto map(UserEntity userEntity);

    @InheritInverseConfiguration
    UserEntity map(UserDto userDto);
}
