package com.nhom7.foodg.services;

import com.nhom7.foodg.models.dto.TblCategoryDto;
import com.nhom7.foodg.models.dto.TblLineDto;
import com.nhom7.foodg.models.entities.TblCategoryEntity;
import com.nhom7.foodg.models.entities.TblLineEntity;
import com.nhom7.foodg.models.entities.TblProductEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LineService {
    List<TblLineEntity> getAll();
    void insert(TblLineDto newLine);
    @Transactional
    void update(TblLineEntity tblLineEntity);
    @Transactional
    void softDelete(int id);


}

