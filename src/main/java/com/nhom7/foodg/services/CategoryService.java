package com.nhom7.foodg.services;

import com.nhom7.foodg.models.dto.TblCategoryDto;
import com.nhom7.foodg.models.entities.TblCategoryEntity;
import com.nhom7.foodg.models.entities.TblProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<TblCategoryEntity> getAll();

    List<TblProductEntity> getProductsByCategory(String categoryName, int page, int limit, String q, String sort, String order);

    List<TblCategoryEntity> search(String keyword);

    TblCategoryEntity getByID(int id);

    void insert(TblCategoryDto tblCategoryDto);

    void update(TblCategoryEntity tblCategoryEntity);

    void deletePermanently(int id);
    void softDelete(int id);

    void restore(int id);
}
