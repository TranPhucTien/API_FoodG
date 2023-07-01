package com.nhom7.foodg.repositories;

import com.nhom7.foodg.models.entities.TblDiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<TblDiscountEntity, Integer> {
    //    TblDiscountEntity findFirstByName(String categoryName);
    boolean existsByCode(String code);
    TblDiscountEntity getByCode( String code);

    @Query(value = "SELECT * FROM tbl_discount where ID = ?1", nativeQuery = true)
    TblDiscountEntity getDiscountByID(int id);

}