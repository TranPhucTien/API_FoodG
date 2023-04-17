package com.nhom7.foodg.services;

import com.nhom7.foodg.models.entities.TblCategoryEntity;
import com.nhom7.foodg.models.entities.TblProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<TblCategoryEntity> getAll();

    List<TblProductEntity> getProductsByCategory(String categoryName);

    List<TblCategoryEntity> searchCategory(String keyword);

    TblCategoryEntity getByID(int categoryID);

    void insert(TblCategoryEntity tblCategoryEntity);

    void update(TblCategoryEntity tblCategoryEntity);

    void delete(int id);
    void solfDelete(int id);

    void restore(int id);
}
