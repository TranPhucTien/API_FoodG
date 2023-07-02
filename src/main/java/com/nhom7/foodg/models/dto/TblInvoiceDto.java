
package com.nhom7.foodg.models.dto;


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
        import java.text.MessageFormat;
        import java.util.Date;

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
