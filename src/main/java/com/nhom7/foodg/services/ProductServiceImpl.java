package com.nhom7.foodg.services;

import com.google.gson.Gson;
import com.nhom7.foodg.exceptions.DuplicateRecordException;
import com.nhom7.foodg.exceptions.ModifyException;
import com.nhom7.foodg.exceptions.NotFoundException;
import com.nhom7.foodg.models.dto.TblProductDto;
import com.nhom7.foodg.models.entities.TblProductEntity;
import com.nhom7.foodg.models.entities.TblProductLogEntity;
import com.nhom7.foodg.repositories.LogProductRepository;
import com.nhom7.foodg.repositories.ProductRepository;
import com.nhom7.foodg.shareds.Constants;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final String TABLE_NAME = "tbl_product";
    private final LogProductRepository logProductRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              LogProductRepository logProductRepository) {
        this.productRepository = productRepository;
        this.logProductRepository = logProductRepository;
    }

    @Override
    public List<TblProductEntity> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<TblProductLogEntity> getEditHistoryByProductIdAndAction(String productId, String action) {
        return logProductRepository.findByProductIdAndAction(productId, action);
    }

    @Override
    public List<TblProductEntity> getDeletedProducts() {
        return productRepository.getDeletedProducts();
    }

    @Override
    public List<TblProductEntity> search(String keyword) {
        List<TblProductEntity> rs = new ArrayList<>();
        List<TblProductEntity> products = productRepository.findAll();

        for (TblProductEntity product : products) {
            if (product.getName().toLowerCase().contains(keyword.toLowerCase())) {
                rs.add(product);
            }
        }

        return rs;
    }

    @Override
    public TblProductEntity getByID(String id) {
        if (productRepository.getProductByID(id) == null) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, id));
        }

        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void insert(TblProductDto newProduct) {
        String newProductName = newProduct.getName();
        int randomNumber = new Random().nextInt(900) + 100;
        String randomIdByName = randomNumber + "-" + newProductName.toLowerCase().replace(" ", "-");

        try {
            if (productRepository.getProductByID(randomIdByName) != null) {
                throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR, TABLE_NAME, newProductName));
            }

            Gson gson = new Gson();
            String dataJson = gson.toJson((newProduct));

            //----------------------------------------------------------------------
            //----------------------------------------------------------------------
            // Lưu ý: Thay đổi đoạn code này khi đã thêm chức năng đăng kí đăng nhập
            int defaultAdminID = 1;
            //----------------------------------------------------------------------
            //----------------------------------------------------------------------

            Date currentDate = Constants.getCurrentDay();

            TblProductEntity tblProductEntity = TblProductEntity.
                    create(randomIdByName,
                            newProduct.getImg(),
                            newProductName,
                            newProduct.getDsc(),
                            newProduct.getPrice(),
                            newProduct.getRate(),
                            newProduct.getCountry(),
                            newProduct.getIdCategory(),
                            currentDate,
                            currentDate,
                            null,
                            false,
                            defaultAdminID,
                            defaultAdminID,
                            null
                    );

            TblProductLogEntity log = TblProductLogEntity.create(
                    0,
                    defaultAdminID,
                    Constants.ACTION_CREATE,
                    randomIdByName,
                    null,
                    dataJson,
                    Constants.getCurrentDay()
            );

            productRepository.save(tblProductEntity);
            logProductRepository.save(log);

        } catch (DataIntegrityViolationException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME) + ex.getMessage());
        }
    }

    @Transactional
    @Override
    public void update(TblProductEntity tblProductEntity) {
        if (productRepository.getProductByID(tblProductEntity.getId()) == null) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, tblProductEntity.getId()));
        }

        try {
            TblProductEntity product = productRepository.findById(tblProductEntity.getId()).orElse(null);

            if (product != null) {
                Gson gson = new Gson();
                String oldDataJson = gson.toJson((product));
                //----------------------------------------------------------------------
                //----------------------------------------------------------------------
                // Lưu ý: Thay đổi đoạn code này khi đã thêm chức năng đăng kí đăng nhập
                int defaultAdminID = 1;
                //----------------------------------------------------------------------
                //----------------------------------------------------------------------

                product.setImg(tblProductEntity.getImg());
                product.setName(tblProductEntity.getName());
                product.setDsc(tblProductEntity.getDsc());
                product.setPrice(tblProductEntity.getPrice());
                product.setCountry(tblProductEntity.getCountry());
                product.setIdCategory(tblProductEntity.getIdCategory());
                product.setUpdatedAt(Constants.getCurrentDay());
                product.setUpdatedBy(defaultAdminID);

                String newDataJson = gson.toJson((product));

                TblProductLogEntity log = TblProductLogEntity.create(
                        0,
                        defaultAdminID,
                        Constants.ACTION_UPDATE,
                        product.getId(),
                        oldDataJson,
                        newDataJson,
                        Constants.getCurrentDay()
                );

                productRepository.save(product);
                logProductRepository.save(log);
            }
        } catch (NullPointerException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME) + ex.getMessage());
        }
    }

    @Transactional
    @Override
    public void deletePermanently(String id) {
        if (productRepository.getDeletedProductsByID(id) == null) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, id));
        }

        productRepository.deleteByIdAndDeleted(id);
    }

    @Transactional
    @Override
    public void softDelete(String id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, id));
        }

        try {
            productRepository.deleteById(id);
            TblProductEntity product = productRepository.getProductByID(id);
            if (product != null) {
                Gson gson = new Gson();
                String dataJson = gson.toJson((product));

                //----------------------------------------------------------------------
                //----------------------------------------------------------------------
                // Lưu ý: Thay đổi đoạn code này khi đã thêm chức năng đăng kí đăng nhập
                int defaultAdminID = 1;
                //----------------------------------------------------------------------
                //----------------------------------------------------------------------

                TblProductLogEntity log = TblProductLogEntity.create(
                        0,
                        defaultAdminID,
                        Constants.ACTION_DELETE,
                        product.getId(),
                        dataJson,
                        dataJson,
                        Constants.getCurrentDay()
                );

                logProductRepository.save(log);

                product.setDeleted(true);
                product.setDeletedAt(Constants.getCurrentDay());
                product.setDeletedBy(defaultAdminID);
                productRepository.save(product);
            }
        } catch (DataAccessException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME, id) + ex.getMessage());
        }
    }

    @Transactional
    @Override
    public void restore(String id) {
        if (productRepository.getProductByID(id) == null) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, id));
        }

        TblProductEntity product = productRepository.findById(id).orElse(null);
        if (product != null) {
            Gson gson = new Gson();
            String dataJson = gson.toJson((product));

            //----------------------------------------------------------------------
            //----------------------------------------------------------------------
            // Lưu ý: Thay đổi đoạn code này khi đã thêm chức năng đăng kí đăng nhập
            int defaultAdminID = 1;
            //----------------------------------------------------------------------
            //----------------------------------------------------------------------

            TblProductLogEntity log = TblProductLogEntity.create(
                    0,
                    defaultAdminID,
                    Constants.ACTION_RESTORE,
                    product.getId(),
                    dataJson,
                    dataJson,
                    Constants.getCurrentDay()
            );

            logProductRepository.save(log);

            product.setDeleted(false);
            product.setDeletedAt(null);
            productRepository.save(product);
        }
    }
}
