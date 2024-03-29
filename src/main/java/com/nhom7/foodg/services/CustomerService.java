package com.nhom7.foodg.services;

import com.nhom7.foodg.models.entities.TblAdminEntity;
import com.nhom7.foodg.models.entities.TblCustomerEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface CustomerService {
    List<TblCustomerEntity> getAll();
    List<TblCustomerEntity> search(String keyword);
    void insert(TblCustomerEntity newCustomer);
    void update(TblCustomerEntity tblCustomerEntity);
    void softDelete(int id);
    Boolean create(TblCustomerEntity tblCustomerEntity);

}