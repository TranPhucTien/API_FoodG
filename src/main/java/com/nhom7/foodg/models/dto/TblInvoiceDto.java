
package com.nhom7.foodg.models.dto;


        import jakarta.persistence.Basic;
        import jakarta.persistence.Column;
        import lombok.*;

        import java.math.BigDecimal;
        import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class TblInvoiceDto {
    private int id;
    private int customerId;
    private int invoiceNumber;
    private Date invoiceDate;
    private BigDecimal totalAmount;
    private BigDecimal tax;
    private int idDiscount;
    private BigDecimal grandTotal;
    private int status;
    private Long idOnePayResponse;
    private Date createdAt;
    private Date updatedAt;
    private Date dueDate;
    private Boolean paid;
    private Date paidDate;

}
