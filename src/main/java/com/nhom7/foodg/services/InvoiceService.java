
package com.nhom7.foodg.services;

        import com.nhom7.foodg.models.dto.TblCategoryDto;
        import com.nhom7.foodg.models.dto.TblInvoiceDto;
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
    void insert(TblInvoiceDto newInvoice);
    @Transactional
    void update(TblInvoiceEntity tblInvoiceEntity);
    @Transactional
    void softDelete(int id);
    // Get all lines of invoice by invoice id
//    List<TblLineEntity> getLinesByInvoice(int invNum);

//    List<TblLineEntity> getLinesByInvoice(int Id);
//
//    List<TblInvoiceEntity> search(int id);
//
//    TblInvoiceEntity getByID(int id);
//

//    @Transactional
//    void update(TblInvoiceEntity tblInvoiceEntity);
//
//    void update(TblInvoiceEntity tblInvoiceEntity);
//
//    void deletePermanently(int id);
//    void softDelete(int id);
//
//    void restore(int id);
}
