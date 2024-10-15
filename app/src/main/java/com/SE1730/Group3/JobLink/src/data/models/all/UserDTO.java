package com.SE1730.Group3.JobLink.src.data.models.all;

import com.SE1730.Group3.JobLink.src.domain.entities.baseEntities.BaseEntity;

import java.util.UUID;

import lombok.Data;

@Data
public class UserDTO extends BaseEntity<UUID> {
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
    private String roleId;
    private String refreshToken;
    private String status;
}
