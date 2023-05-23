package com.nhom7.foodg.repositories;

import com.nhom7.foodg.models.entities.TblCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<TblCustomerEntity, Integer> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}