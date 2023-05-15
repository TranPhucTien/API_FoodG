package com.nhom7.foodg.models.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class TblCustomerDto {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private Integer age;
    private boolean gender;
    private String avatar;
    private Integer idProvince;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;



}