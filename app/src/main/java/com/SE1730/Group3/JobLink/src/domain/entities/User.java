package com.SE1730.Group3.JobLink.src.domain.entities;

import androidx.room.Entity;

import com.SE1730.Group3.JobLink.src.domain.entities.baseEntities.BaseEntity;
import com.SE1730.Group3.JobLink.src.domain.enums.UserStatus;

import org.threeten.bp.LocalDateTime;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Builder
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

    // Full constructor
    public User(String username, String password, String email, String firstName, String lastName,
                String phoneNumber, String dateOfBirth, String address, Integer lat, Integer lon,
                String avatar, UUID roleId, String refreshToken, LocalDateTime refreshTokenExpiryTime,
                UserStatus status) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.avatar = avatar;
        this.roleId = roleId;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiryTime = refreshTokenExpiryTime;
        this.status = status;
    }
}
