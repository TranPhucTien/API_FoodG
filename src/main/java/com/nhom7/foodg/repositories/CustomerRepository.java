package com.nhom7.foodg.repositories;

import com.nhom7.foodg.models.entities.TblCustomerEntity;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface CustomerRepository extends JpaRepository<TblCustomerEntity, Integer> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    TblCustomerEntity findFirstByEmail(String email);
    TblCustomerEntity findFirstByUsername(String userName);

}