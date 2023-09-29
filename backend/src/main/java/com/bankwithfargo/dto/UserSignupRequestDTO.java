package com.bankwithfargo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupRequestDTO {
    private String email;

    @NotNull(message = "Password should not be null")
    @Pattern(regexp = "[A-Za-z0-9]{8,}", message="password should have min 8 alphanumeric characters")
    private String password;
}
