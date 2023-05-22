package com.nhom7.foodg.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class TblDiscountDto {
    private String code;
    private BigDecimal percentage;
    private BigDecimal minAmount;
    private Date startDate;
    private Date endDate;
    private Boolean isActive;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;


}