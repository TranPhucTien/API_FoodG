package com.nhom7.foodg.services;

import com.nhom7.foodg.models.entities.TblAdminEntity;
import com.nhom7.foodg.models.entities.TblCustomerEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface AdminService {
    List<TblAdminEntity> getAll();
    List<TblAdminEntity> search(String keyword);
    void insert(TblAdminEntity newAdmin);
    void update(TblAdminEntity tblAdminEntity);
    void softDelete(int id);

    Boolean create(TblAdminEntity tblAdminEntity);

}
