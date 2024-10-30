package com.SE1730.Group3.JobLink.src.data.mappers;

import com.SE1730.Group3.JobLink.src.data.models.request.ResetPassDTO;
import com.SE1730.Group3.JobLink.src.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IUserForgotMapper {

    IUserForgotMapper INSTANCE = Mappers.getMapper(IUserForgotMapper.class);

    ResetPassDTO toResetPassDTO(User user);

    User toUser(ResetPassDTO resetPassDTO);
}
