package com.nhom7.foodg.services;

import com.nhom7.foodg.models.dto.TblProductDto;
import com.nhom7.foodg.models.entities.TblCategoryEntity;
import com.nhom7.foodg.models.entities.TblProductEntity;
import com.nhom7.foodg.models.entities.TblProductLogEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<TblProductEntity> getAll();
    List<TblProductLogEntity> getEditHistoryByProductIdAndAction(String productId, String action);
    public List<TblProductLogEntity> getAllEditAndDeleteHistory();

    List<TblProductEntity> getDeletedProducts();

    List<TblProductEntity> search(String keyword);

    TblProductEntity getByID(String id);

    void insert(TblProductEntity newProduct);

    void update(TblProductEntity tblProductEntity);

    void deletePermanently(String id); // xoá hẳn (Không khôi phục được)

    void softDelete(String productId, int adminId); // xoá tạm thời (soft delete)

    void restore(String productId, int adminId); // khôi phục cái đã xoá
}
