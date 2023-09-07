package com.bankwithfargo.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "user")
public class User {

    private String email;
    private String password;
    private String username;
    private Long account_no;
    private String pan;
    private String address;
    private Long phone;
    private Date dob;

    public User() {

    }

    public User(String email, String password, String username, Long account_no, String pan, String address, Long phone, Date dob) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.account_no = account_no;
        this.pan = pan;
        this.address = address;
        this.phone = phone;
        this.dob = dob;
    }

    @Id
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email=email;
    }

    @Column(name = "pwd", nullable = false)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "pan", nullable = false)
    public String getPan() {
        return pan;
    }
    public void setPan(String pan) {
        this.pan = pan;
    }

    @Column(name = "phone", nullable = false)
    public Long getPhone() {
        return phone;
    }
    public void setPhone(Long phone) {
        this.phone = phone;
    }

    @Column(name = "dob", nullable = false)
    public Date getDob() {
        return dob;
    }
    public void setDob(Date dob) {
        this.dob = dob;
    }


}