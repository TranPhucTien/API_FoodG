package com.nhom7.foodg.repositories;

import com.nhom7.foodg.models.entities.TblProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<TblProductEntity, String> {
    List<TblProductEntity> findByIdCategory(int categoryID);
}
