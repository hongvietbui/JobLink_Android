package com.SE1730.Group3.JobLink.src.data.models.request;

import org.threeten.bp.LocalDate;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterReqDTO {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private LocalDate dateOfBirth;
}
