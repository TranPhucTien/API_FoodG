package com.nhom7.foodg.repositories;

import com.nhom7.foodg.models.entities.TblProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductRepository extends JpaRepository<TblProductEntity, String> {
    List<TblProductEntity> findByIdCategory(int categoryID);
    Page<TblProductEntity> findByIdCategory(int categoryID, Pageable pageable);
    Page<TblProductEntity> findByIdCategoryAndNameContaining(int categoryID, String name, Pageable pageable);
    List<TblProductEntity> findByIdCategoryAndNameContaining(int categoryID, String name);

    // Đếm số lượng sản phẩm trong mỗi category
    @Query(value = "SELECT c.name, COUNT(p.id) FROM TblProductEntity p JOIN TblCategoryEntity c on p.idCategory = c.id GROUP BY c.name")
    List<Object[]> countAllProductEachCategory();

    // Lấy sản phẩm theo id (đã xoá và chưa xoá)
    @Query(value = "SELECT * FROM tbl_product where ID = ?1", nativeQuery = true)
    TblProductEntity getProductByID(String id);

    // Lấy tất cả sản phẩm đã xoá
    @Query(value = "SELECT * FROM tbl_product WHERE deleted = 1", nativeQuery = true)
    List<TblProductEntity> getDeletedProducts();

    // Lấy sản phẩm đã xoá theo id
    @Query(value = "SELECT * FROM tbl_product WHERE ID = ?1 and deleted = 1", nativeQuery = true)
    TblProductEntity getDeletedProductsByID(String id);

    // Xoá vĩnh viễn sản phẩm
    @Modifying
    @Query(value = "DELETE FROM tbl_product WHERE ID = ?1 and deleted = 1", nativeQuery = true)
    void deleteByIdAndDeleted(String id);
}
