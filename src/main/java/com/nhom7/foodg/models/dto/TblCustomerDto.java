package com.nhom7.foodg.models.dto;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor

@AllArgsConstructor(staticName = "create")
public class TblCustomerDto {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private Boolean gender;
    private String avatar;
    private Integer idProvince;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private Date birthday;
    private String otp;
    private Date otpExp;
    private Integer role;

}