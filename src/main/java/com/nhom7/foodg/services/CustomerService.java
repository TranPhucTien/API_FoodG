package com.nhom7.foodg.services;

import com.nhom7.foodg.models.dto.TblCustomerDto;
import com.nhom7.foodg.models.dto.TblDiscountDto;
import com.nhom7.foodg.models.entities.TblCustomerEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    List<TblCustomerEntity> getAll();

    List<TblCustomerEntity> search(String keyword);

    void insert(TblCustomerDto tblCustomerDto);

    void update(TblCustomerEntity tblCustomerEntity);
//
    void softDelete(int id);

}
