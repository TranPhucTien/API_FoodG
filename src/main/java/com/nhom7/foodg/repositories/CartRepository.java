

package com.nhom7.foodg.repositories;


        import com.nhom7.foodg.models.entities.TblCartEntity;
        import com.nhom7.foodg.models.entities.TblInvoiceEntity;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<TblCartEntity, Integer> {
    TblCartEntity findFirstByCustomerId(int customerId);


}
