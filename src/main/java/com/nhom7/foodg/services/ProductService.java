package com.nhom7.foodg.services;

import com.nhom7.foodg.models.dto.TblProductDto;
import com.nhom7.foodg.models.entities.TblCategoryEntity;
import com.nhom7.foodg.models.entities.TblProductEntity;

import java.util.List;

public interface ProductService {
    List<TblProductEntity> getAll();

    List<TblProductEntity> getDeletedProducts();

    List<TblProductEntity> search(String keyword);

    TblProductEntity getByID(String id);

    void insert(TblProductDto newProduct);

    void update(TblProductEntity tblProductEntity);

    void deletePermanently(String id); // xoá hẳn (Không khôi phục được)

    void softDelete(String id); // xoá tạm thời (solf delete)

    void restore(String id); // khôi phục cái đã xoá
}
