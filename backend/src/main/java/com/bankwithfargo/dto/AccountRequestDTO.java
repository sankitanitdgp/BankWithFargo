package com.bankwithfargo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDTO {
    private String title;
    @NotNull(message = "first name cannot be null")
    @NotEmpty(message = "first name cannot be empty")
    private String firstName;
    private String lastName;
    private Date dob;
    private String fatherName;
    @NotNull(message = "phone number cannot be null")
    @Pattern(regexp="[0-9]{10}", message="Invalid phone number")
    private String phone;
    private String presentAddress;
    private String permanentAddress;
    @NotNull(message = "PAN number cannot be null")
    @Pattern(regexp="[A-Z]{5}[0-9]{4}[A-Z]$", message="Invalid PAN number")
    private String pan;
    private String occupationType;
    private Long income;
    private String email;
    @NotNull(message = "MPIN cannot be null")
    private int mpin;

}
