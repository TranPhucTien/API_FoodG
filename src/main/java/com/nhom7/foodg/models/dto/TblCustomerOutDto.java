package com.nhom7.foodg.models.dto;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor

@AllArgsConstructor(staticName = "create")
public class TblCustomerOutDto {
    private String username;
    private String fullName;
    private String email;



}