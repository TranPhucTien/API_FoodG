package com.nhom7.foodg.services;


import com.nhom7.foodg.exceptions.InactiveDiscountException;
import com.nhom7.foodg.exceptions.NotApplyDiscountExeption;
import com.nhom7.foodg.models.dto.TblDiscountDto;
import com.nhom7.foodg.models.entities.TblDiscountEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DiscountService {
    List<TblDiscountEntity> getAll();

    List<TblDiscountEntity> search(String keyword);

    void insert(TblDiscountEntity tblDiscountDto);

    void update(TblDiscountEntity tblDiscountEntity);

    void softDelete(int id);

}