
package com.nhom7.foodg.models.dto;


        import jakarta.persistence.Basic;
        import jakarta.persistence.Column;
        import lombok.*;

        import java.math.BigDecimal;
        import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "createLine")
public class TblLineDto {
    private int id;
    private int idInvoice;
    private String idProduct;
    private String description;
    private int quantity;
    private BigDecimal price;
    private BigDecimal unitPrice;
    private BigDecimal total;

}
