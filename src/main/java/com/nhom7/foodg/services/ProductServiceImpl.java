package com.nhom7.foodg.services;

import com.nhom7.foodg.exceptions.DuplicateRecordException;
import com.nhom7.foodg.exceptions.ModifyException;
import com.nhom7.foodg.models.entities.TblCategoryEntity;
import com.nhom7.foodg.models.entities.TblProductEntity;
import com.nhom7.foodg.repositories.ProductRepository;
import com.nhom7.foodg.shareds.Constants;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.MessageFormat;
import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final String TABLE_NAME = "tbl_product";

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<TblProductEntity> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<TblProductEntity> searchCategory(String keyword) {
        return null;
    }

    @Override
    public TblProductEntity getByID(int categoryID) {
        return null;
    }

    @Override
    public void insert(TblProductEntity newProduct) {
        String productID = newProduct.getId();
        try {
            if (productRepository.existsById(productID)) {
                throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR, TABLE_NAME, productID));
            }

            System.out.println(newProduct);

            Date currentDate = Constants.getCurrentDay();

            TblProductEntity tblCategoryEntity = TblProductEntity.
                    create(
                            productID,
                            newProduct.getImg(),
                            newProduct.getName(),
                            newProduct.getDsc(),
                            newProduct.getPrice(),
                            newProduct.getRate(),
                            newProduct.getCountry(),
                            newProduct.getIdCategory(),
                            currentDate,
                            currentDate,
                            currentDate,
                            false
                    );
            productRepository.save(tblCategoryEntity);

        } catch (DataIntegrityViolationException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME) + ex.getMessage());
        }
    }

    @Override
    public void update(TblProductEntity tblProductEntity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void solfDelete(int id) {

    }

    @Override
    public void restore(int id) {

    }
}
