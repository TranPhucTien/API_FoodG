
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
    private String codeDiscount;
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
    public BigDecimal caculatorGrandTotal(BigDecimal price, TblDiscountEntity tblDiscountEntity)  {
        BigDecimal priceMulPer = price.multiply(BigDecimal.valueOf(tblDiscountEntity.getPercentage() / 100));

        if (priceMulPer.compareTo(tblDiscountEntity.getMaxDiscountPrice()) <= 0) {
            price = price.subtract(tblDiscountEntity.getMaxDiscountPrice());
        } else if (priceMulPer.compareTo(tblDiscountEntity.getMaxDiscountPrice()) > 0) {
            price = price.subtract(priceMulPer);
        }
        return price;

    }
//    public Boolean checkDiscountForInvoice(int idDiscount, DiscountRepository discountRepository,BigDecimal price) throws NotApplyDiscountExeption {
//        TblDiscountEntity tblDiscountEntity = discountRepository.findById(idDiscount).orElse(null);
//        // Kiểm tra end_date của hóa đơn còn hạn hay không
//        if (!discountRepository.existsById(idDiscount)) {
//            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, "Tbl_Discount", idDiscount));
//        }
//        if (price.compareTo(tblDiscountEntity.getMinAmount()) < 0 || price.compareTo(tblDiscountEntity.getMaxAmount()) > 0) {
//            throw new NotApplyDiscountExeption("tong giá trị hàng không trong mức cho phép áp dụng mã");
//
//        }
//
//        // Kiểm tra end_date của hóa đơn còn hạn hay không
//        Date startDate = tblDiscountEntity.getStartDate();
//        Date endDate = tblDiscountEntity.getEndDate();
//        Date currentDate = Constants.getCurrentDay();
//        if (currentDate.before(startDate) || currentDate.after(endDate)) {
//            // Nếu hóa đơn đã hết hạn, xử lý ngoại lệ ở đây
//            throw new NotApplyDiscountExeption("ma giam gia chua den han hoat");
//        }
//         //Kiểm tra trạng thái active của discount
//        else if (tblDiscountEntity.getIsActive() == false) {
//            throw new NotApplyDiscountExeption("ma giam gia chua duoc kich hoat");
//        }
//        else return true;
//    }





}
