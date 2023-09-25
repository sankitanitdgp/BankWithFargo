package com.bankwithfargo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Email
    private String email;

    @Column(name = "pwd", nullable = false)
    @NotNull(message = "Password should not be null")
    @Pattern(regexp = "[A-Za-z0-9]{8,}", message="password should have min 8 alphanumeric characters")
    private String password;

}