package com.bankwithfargo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(name = "pan", nullable = false)
    @NotNull
//    @Pattern(regexp="[A-Z]{5}[0-9]{4}[A-Z]$", message="Invalid PAN number")
    private String pan;

    @Column(name = "phone", nullable = false)
    @NotNull
//    @Pattern(regexp="[0-9]{10}", message="Invalid phone number. Please provide a 10 digit number.")
    private String phone;

    @Column(name="present_address", nullable = false)
    private String presentAddress;

    @Column(name="permanent_address", nullable = false)
    private String permanentAddress;

    private String email;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @Column(name="date_of_opening", nullable = false)
    private LocalDate dateOfOpening;

    @Column(name="ifsc", nullable = false)
    private String ifsc;

    @Column(name = "account_status", nullable = false)
    private Boolean accountStatus;

    @Column(name="occupation_type", nullable = false)
    private String occupationType;

    @Column(name="income", nullable = false)
    private Long income;

    @Column(name="mpin", nullable = false)
    private int mpin;

    @Column(name="dob", nullable = false)
    private Date dob;

    @Column(name="is_active", nullable = false)
    private Boolean isActive;

    @NotNull
    @ManyToOne
    @JoinColumn(name="user", referencedColumnName = "email")
    private User user;

}