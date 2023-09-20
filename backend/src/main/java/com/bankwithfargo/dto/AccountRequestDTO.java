package com.bankwithfargo.dto;

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
    private String firstName;
    private String lastName;
    private Date dob;
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
