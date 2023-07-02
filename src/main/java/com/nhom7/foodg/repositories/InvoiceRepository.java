
package com.nhom7.foodg.repositories;


        import com.nhom7.foodg.models.entities.TblInvoiceEntity;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<TblInvoiceEntity, Integer> {
        TblInvoiceEntity findFirstById(int invoiceId);
        Boolean existsByInvoiceNumber(int invoiceNumber);
        TblInvoiceEntity findFirstByInvoiceNumber(int invoiceNumber);
}
