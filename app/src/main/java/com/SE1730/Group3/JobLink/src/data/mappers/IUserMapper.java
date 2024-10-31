package com.SE1730.Group3.JobLink.src.data.mappers;

import com.SE1730.Group3.JobLink.src.data.models.all.UserDTO;
import com.SE1730.Group3.JobLink.src.domain.entities.User;
import com.SE1730.Group3.JobLink.src.domain.enums.UserStatus;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    @Mapping(target = "id", source = "id")
    UserDTO toUserDTO(User user);

    @Mapping(target = "id", source = "id")
    User toUser(UserDTO userDTO);
}
