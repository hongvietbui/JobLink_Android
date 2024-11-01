package com.SE1730.Group3.JobLink.src.data.models.all;

import com.SE1730.Group3.JobLink.src.domain.entities.baseEntities.BaseEntity;

import org.threeten.bp.LocalDateTime;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class UserDTO {
    private UUID id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String dateOfBirth;
    private String address;
    private Integer lat;
    private Integer lon;
    private String avatar;
    private List<RoleDTO> roleList;
    private String refreshToken;
    private LocalDateTime refreshTokenExpiryTime;
    private String status;
}
