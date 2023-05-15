package com.nhom7.foodg.repositories;


import com.nhom7.foodg.models.entities.TblProvinceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<TblProvinceEntity, Integer> {
    boolean existsByWard(String name);
}
