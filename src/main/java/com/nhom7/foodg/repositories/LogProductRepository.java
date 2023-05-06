package com.nhom7.foodg.repositories;

import com.nhom7.foodg.models.entities.TblProductLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogProductRepository extends JpaRepository<TblProductLogEntity, Integer> {
    List<TblProductLogEntity> findByProductIdAndAction(String productId, String action);
}
