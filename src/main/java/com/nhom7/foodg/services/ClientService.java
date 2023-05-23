package com.nhom7.foodg.services;

import com.nhom7.foodg.models.dto.TblCustomerDto;
import com.nhom7.foodg.models.entities.TblCustomerEntity;

public interface ClientService {
    Boolean create(TblCustomerEntity tblCustomerEntity);

}
