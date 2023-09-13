package com.bankwithfargo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupRequestDTO {
    private String email;
    private String password;
    private String confirmPassword;
}
