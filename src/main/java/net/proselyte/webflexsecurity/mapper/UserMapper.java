package net.proselyte.webflexsecurity.mapper;

import net.proselyte.webflexsecurity.dto.UserDTO;
import net.proselyte.webflexsecurity.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO map(UserEntity userEntity);

    @InheritInverseConfiguration
    UserEntity map(UserDTO userDTO);
}
