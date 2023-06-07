
package com.nhom7.foodg.services;

        import com.nhom7.foodg.exceptions.InactiveDiscountException;
        import com.nhom7.foodg.exceptions.NotApplyDiscountExeption;
        import com.nhom7.foodg.models.dto.*;
        import com.nhom7.foodg.models.entities.TblCategoryEntity;
        import com.nhom7.foodg.models.entities.TblInvoiceEntity;
        import com.nhom7.foodg.models.entities.TblLineEntity;
        import com.nhom7.foodg.models.entities.TblProductEntity;
        import jakarta.transaction.Transactional;
        import org.springframework.stereotype.Service;

        import java.util.List;

@Service
public interface InvoiceService {
    List<TblInvoiceEntity> getAll();
    TblInvoiceOutDto getById(int invoiceId);
    void insert(TblInvoiceLineDto tblInvoiceLineDto);
    @Transactional
    void update(TblInvoiceEntity tblInvoiceEntity);
    @Transactional
    void softDelete(int id);


}
