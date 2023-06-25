
package com.nhom7.foodg.models.dto;


import com.nhom7.foodg.exceptions.InactiveDiscountException;
import com.nhom7.foodg.exceptions.NotApplyDiscountExeption;
import com.nhom7.foodg.exceptions.NotFoundException;
import com.nhom7.foodg.models.entities.TblDiscountEntity;
import com.nhom7.foodg.models.entities.TblProductEntity;
import com.nhom7.foodg.repositories.DiscountRepository;
import com.nhom7.foodg.shareds.Constants;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class TblLineOutDto {
    private String idProduct;
    private int quantity;
    private String description;



    //kiểm tra hóa đơn còn hạn & được kích hoạt hay chưa


    //truyền giá của dòng hóa đơn, giá của hóa đơn để tính giá trị khi áp mã




}
