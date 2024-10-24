package com.SE1730.Group3.JobLink.src.domain.entities;

import androidx.room.Entity;

import com.SE1730.Group3.JobLink.src.domain.entities.baseEntities.BaseEntity;
import com.SE1730.Group3.JobLink.src.domain.enums.UserStatus;

import org.threeten.bp.LocalDateTime;

import java.util.UUID;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity(tableName = "Users")
public class User extends BaseEntity<UUID> {
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
    private UUID roleId;
    private String refreshToken;
    private LocalDateTime refreshTokenExpiryTime;
    private UserStatus status;
}
