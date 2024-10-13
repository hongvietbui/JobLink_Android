package com.SE1730.Group3.JobLink.src.data.mappers;

import com.SE1730.Group3.JobLink.src.data.models.user.UserDTO;
import com.SE1730.Group3.JobLink.src.domain.entities.User;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    UserDTO toUserDTO(User user);

    User toUser(UserDTO userDTO);
}
