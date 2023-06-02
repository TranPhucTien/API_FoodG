
package com.nhom7.foodg.models.dto;
import java.util.List;

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
