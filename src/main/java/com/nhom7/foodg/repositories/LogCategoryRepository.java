package com.nhom7.foodg.repositories;

import com.nhom7.foodg.models.entities.TblCategoryEntity;
import com.nhom7.foodg.models.entities.TblCategoryLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogCategoryRepository extends JpaRepository<TblCategoryLogEntity, Integer> {
}
