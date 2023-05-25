package com.nhom7.foodg.repositories;

import com.nhom7.foodg.models.entities.TblAdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<TblAdminEntity, Integer> {
}
