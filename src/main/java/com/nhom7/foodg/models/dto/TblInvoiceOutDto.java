package com.nhom7.foodg.models.dto;

import com.nhom7.foodg.models.entities.TblLineEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor(staticName = "create")
public class TblInvoiceOutDto {
    private int id;
    private TblCustomerOutDto customerOutDto;
    private List<TblLineEntity> lines;
    private int invoiceNumber;
    private Date invoiceDate;
    private BigDecimal totalAmount;
    private BigDecimal tax;
    private Integer idDiscount;
    private BigDecimal grandTotal;
    private int status;
    private Long idOnePayResponse;
    private Date createdAt;
    private Date updatedAt;
    private Date dueDate;
    private Boolean paid;
    private Date paidDate;
}
