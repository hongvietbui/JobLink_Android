package com.SE1730.Group3.JobLink.src.domain.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.SE1730.Group3.JobLink.src.domain.entities.baseEntities.BaseEntity;
import com.SE1730.Group3.JobLink.src.domain.enums.UserStatus;

import org.threeten.bp.LocalDateTime;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
@Entity(tableName = "Users")
public class User{
    @PrimaryKey
    @NonNull
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
    private UUID roleId;
    private String refreshToken;
    private LocalDateTime refreshTokenExpiryTime;
    private UserStatus status;
    private Boolean isDeleted;
    private String deletedAt;
    private String deletedBy;
    private String createdAt;
    private String createdBy;
    private String updatedAt;
    private String updatedBy;
}
