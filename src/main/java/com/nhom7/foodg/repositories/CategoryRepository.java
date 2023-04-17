package com.nhom7.foodg.repositories;


import com.nhom7.foodg.models.entities.TblCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<TblCategoryEntity, Integer> {
    TblCategoryEntity findFirstByName(String categoryName);
    boolean existsByName(String name);
}
