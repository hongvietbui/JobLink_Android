package com.SE1730.Group3.JobLink.src.domain.entities;

import com.SE1730.Group3.JobLink.src.domain.entities.baseEntities.BaseEntity;
import com.SE1730.Group3.JobLink.src.domain.enums.UserStatus;

import org.threeten.bp.LocalDate;

import java.util.UUID;

import lombok.Data;

@Data
public class User extends BaseEntity<UUID> {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String address;
    private Integer lat;
    private Integer lon;
    private String avatar;
    private UUID roleId;
    private String refreshToken;
    private UserStatus status;
}
