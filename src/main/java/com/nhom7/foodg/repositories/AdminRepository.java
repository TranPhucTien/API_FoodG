package com.nhom7.foodg.repositories;

import com.nhom7.foodg.models.entities.TblAdminEntity;
import com.nhom7.foodg.models.entities.TblCustomerEntity;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface AdminRepository extends JpaRepository<TblAdminEntity, Integer> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    TblAdminEntity findFirstByEmail(String email);
    TblAdminEntity findFirstByUsername(String userName);

}