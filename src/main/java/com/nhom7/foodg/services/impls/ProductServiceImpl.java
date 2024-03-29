package com.nhom7.foodg.services.impls;

import com.google.gson.Gson;
import com.nhom7.foodg.exceptions.DuplicateRecordException;
import com.nhom7.foodg.exceptions.ModifyException;
import com.nhom7.foodg.exceptions.NotFoundException;
import com.nhom7.foodg.models.entities.TblProductEntity;
import com.nhom7.foodg.models.entities.TblProductLogEntity;
import com.nhom7.foodg.repositories.AdminRepository;
import com.nhom7.foodg.repositories.LogProductRepository;
import com.nhom7.foodg.repositories.ProductRepository;
import com.nhom7.foodg.services.ProductService;
import com.nhom7.foodg.shareds.Constants;
import com.nhom7.foodg.utils.VariableHandler;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final String TABLE_NAME = "tbl_product";
    private final LogProductRepository logProductRepository;
    private final AdminRepository adminRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              LogProductRepository logProductRepository,
                              AdminRepository adminRepository) {
        this.productRepository = productRepository;
        this.logProductRepository = logProductRepository;
        this.adminRepository = adminRepository;
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
    public List<TblProductLogEntity> getAllEditAndDeleteHistory() {
        return logProductRepository.findAll();
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
    public void insert(TblProductEntity newProduct) {
        //Validate input
        Constants.validateRequiredFields(newProduct, "name", "dsc", "price");
        Constants.validateIntegerFields(newProduct, "idCategory");
        Constants.validateStringFields(newProduct, "nvarchar(100)", 0, 100, "country");
        Constants.validateStringFields(newProduct, "nvarchar(200)", 0, 200, "name");
        Constants.validateIntegerFields(newProduct, "createdBy", "updatedBy", "deletedBy");


        String newProductName = newProduct.getName();
        int randomNumber = new Random().nextInt(900) + 100;
        String randomIdByName = randomNumber + "-" + newProductName.toLowerCase().replace(" ", "-");

        try {
            if (productRepository.getProductByID(randomIdByName) != null) {
                throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR, TABLE_NAME, newProductName));
            }

            Gson gson = new Gson();
            String dataJson = gson.toJson((newProduct));

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
                            newProduct.getCreatedBy(),
                            newProduct.getUpdatedBy(),
                            null
                    );

            TblProductLogEntity log = TblProductLogEntity.create(
                    0,
                    newProduct.getCreatedBy(),
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
        TblProductEntity currentProduct = productRepository.getProductByID(tblProductEntity.getId());
        if (currentProduct == null) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, tblProductEntity.getId()));
        }

        try {
            TblProductEntity product = productRepository.findById(tblProductEntity.getId()).orElse(null);

            if (product != null) {
                Gson gson = new Gson();
                String oldDataJson = gson.toJson((product));

                product.setImg(VariableHandler.isNullOrEmpty(tblProductEntity.getImg()) ? currentProduct.getImg() : tblProductEntity.getImg());
                product.setName(VariableHandler.isNullOrEmpty(tblProductEntity.getName()) ? currentProduct.getName() : tblProductEntity.getName());
                product.setDsc(VariableHandler.isNullOrEmpty(tblProductEntity.getDsc()) ? currentProduct.getDsc() : tblProductEntity.getDsc());
                product.setPrice(VariableHandler.isNullOrEmpty(tblProductEntity.getPrice()) ? currentProduct.getPrice() : tblProductEntity.getPrice());
                product.setCountry(VariableHandler.isNullOrEmpty(tblProductEntity.getCountry()) ? currentProduct.getCountry() : tblProductEntity.getCountry());
                product.setIdCategory(VariableHandler.isNullOrEmpty(tblProductEntity.getIdCategory()) ? currentProduct.getIdCategory() : tblProductEntity.getIdCategory());
                product.setUpdatedAt(Constants.getCurrentDay());
                product.setUpdatedBy(tblProductEntity.getUpdatedBy());

                String newDataJson = gson.toJson((product));

                TblProductLogEntity log = TblProductLogEntity.create(
                        0,
                        tblProductEntity.getUpdatedBy(),
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
    public void softDelete(String productId, int adminId) {
        if (!productRepository.existsById(productId)) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, productId));
        }

        if (!adminRepository.existsById(adminId)) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, adminId));
        }

        try {
            productRepository.deleteById(productId);
            TblProductEntity product = productRepository.getProductByID(productId);
            if (product != null) {
                Gson gson = new Gson();
                String dataJson = gson.toJson((product));

                TblProductLogEntity log = TblProductLogEntity.create(
                        0,
                        adminId,
                        Constants.ACTION_DELETE,
                        product.getId(),
                        dataJson,
                        dataJson,
                        Constants.getCurrentDay()
                );

                logProductRepository.save(log);

                product.setDeleted(true);
                product.setDeletedAt(Constants.getCurrentDay());
                product.setDeletedBy(adminId);
                productRepository.save(product);
            }
        } catch (DataAccessException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME, productId) + ex.getMessage());
        }
    }

    @Transactional
    @Override
    public void restore(String productId, int adminId) {
        if (productRepository.getProductByID(productId) == null) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, productId));
        }

        TblProductEntity product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            Gson gson = new Gson();
            String dataJson = gson.toJson((product));

            TblProductLogEntity log = TblProductLogEntity.create(
                    0,
                    adminId,
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