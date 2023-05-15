package com.nhom7.foodg.services;

import com.nhom7.foodg.models.entities.TblProvinceEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProvinceService {
    List<TblProvinceEntity> getAll();

    List<TblProvinceEntity> search(String keyword);

}
