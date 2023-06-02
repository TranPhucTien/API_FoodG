
package com.nhom7.foodg.models.dto;


import com.nhom7.foodg.models.entities.TblDiscountEntity;
import com.nhom7.foodg.models.entities.TblProductEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class TblLineOutDto {
    private String idProduct;
    private int quantity;
    private String description;
    private int idDiscount;
}
