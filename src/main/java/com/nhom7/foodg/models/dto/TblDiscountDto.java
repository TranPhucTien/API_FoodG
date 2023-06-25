package com.nhom7.foodg.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
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

}