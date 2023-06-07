
package com.nhom7.foodg.models.dto;
import java.text.MessageFormat;
import java.util.List;

import com.nhom7.foodg.exceptions.InactiveDiscountException;
import com.nhom7.foodg.exceptions.NotApplyDiscountExeption;
import com.nhom7.foodg.exceptions.NotFoundException;
import com.nhom7.foodg.models.entities.TblDiscountEntity;
import com.nhom7.foodg.repositories.DiscountRepository;
import com.nhom7.foodg.shareds.Constants;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class TblInvoiceLineDto {
    TblInvoiceDto newInvoice;
    List<TblLineOutDto> tblLineOutDtos;


}
