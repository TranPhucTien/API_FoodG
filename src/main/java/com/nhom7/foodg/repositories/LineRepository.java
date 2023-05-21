package com.nhom7.foodg.repositories;


import com.nhom7.foodg.models.entities.TblCategoryEntity;
import com.nhom7.foodg.models.entities.TblLineEntity;
import com.nhom7.foodg.models.entities.TblProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineRepository extends JpaRepository<TblLineEntity, Integer> {
    List<TblLineEntity> findByIdInvoice(int Id);
}

