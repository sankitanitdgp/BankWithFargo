package com.bankwithfargo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDTO {
    private String title;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String fatherName;
    private String phone;
    private String presentAddress;
    private String permanentAddress;
    private String pan;
    private String occupationType;
    private Long income;
    private String email;
    private int mpin;

}
