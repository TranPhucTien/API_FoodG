package com.nhom7.foodg.services;

import com.nhom7.foodg.models.entities.TblCategoryEntity;
import com.nhom7.foodg.models.entities.TblProductEntity;

import java.util.List;

public interface ProductService {
    List<TblProductEntity> getAll();

    List<TblProductEntity> search(String keyword);

    TblProductEntity getByID(String id);

    void insert(TblProductEntity tblProductEntity);

    void update(TblProductEntity tblProductEntity);

    void delete(int id); // xoá hẳn (Không khôi phục được)

    void solfDelete(int id); // xoá tạm thời (solf delete)

    void restore(int id); // khôi phục cái đã xoá
}
