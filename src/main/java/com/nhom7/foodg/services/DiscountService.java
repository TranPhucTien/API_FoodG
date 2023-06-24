package com.nhom7.foodg.services;


import com.nhom7.foodg.exceptions.InactiveDiscountException;
import com.nhom7.foodg.exceptions.NotApplyDiscountExeption;
import com.nhom7.foodg.models.dto.TblDiscountDto;
import com.nhom7.foodg.models.entities.TblDiscountEntity;
import com.nhom7.foodg.models.entities.TblProductEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public interface DiscountService {
    List<TblDiscountEntity> getAll();

    List<TblDiscountEntity> search(String keyword);

    void insert( TblDiscountEntity tblDiscountEntity);

    void update(TblDiscountEntity tblDiscountEntity);

    void softDelete(int id);
    TblDiscountEntity getByID(int id);

}