package com.nhom7.foodg.services;

import com.nhom7.foodg.exceptions.DuplicateRecordException;
import com.nhom7.foodg.exceptions.ModifyException;
import com.nhom7.foodg.exceptions.NotFoundException;
import com.nhom7.foodg.models.dto.TblCategoryDto;
import com.nhom7.foodg.models.entities.TblCategoryEntity;
import com.nhom7.foodg.models.entities.TblProductEntity;
import com.nhom7.foodg.repositories.CategoryRepository;
import com.nhom7.foodg.repositories.ProductRepository;
import com.nhom7.foodg.shareds.Constants;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final String TABLE_NAME = "tbl_category";

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    // Get all category
    @Override
    public List<TblCategoryEntity> getAll() {
        return categoryRepository.findAll();
    }

    // Get all product of category by category name
    @Override
    public List<TblProductEntity> getProductsByCategory(String categoryName, int page, int limit, String q, String sort, String order) {
        if (!categoryRepository.existsByName(categoryName)) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, categoryName));
        }

        TblCategoryEntity cat = categoryRepository.findFirstByName(categoryName);
        List<TblProductEntity> rs;

        if (page == -1 || limit == -1) {
            if (q.equals("")) {
                rs = productRepository.findByIdCategory(cat.getId());
            } else {
                rs = productRepository.findByIdCategoryAndNameContaining(cat.getId(), q);
            }
        } else {
            // start page of JPA is 0
            int _page = page - 1;

            Pageable pageable;

            if (!sort.equals("") && order.equals("desc")) {
                pageable = PageRequest.of(_page, limit, Sort.by(sort).descending());
            } else if (!sort.equals("") && order.equals("asc")) {
                pageable = PageRequest.of(_page, limit, Sort.by(sort).ascending());
            } else {
                pageable = PageRequest.of(_page, limit);
            }

            if (q.equals("")) {
                rs = productRepository.findByIdCategory(cat.getId(), pageable).getContent();
            } else {
                rs = productRepository.findByIdCategoryAndNameContaining(cat.getId(), q, pageable).getContent();
            }
        }

        return rs;
    }

    // Get category by category id
    @Override
    public TblCategoryEntity getByID(int id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, id));
        }

        return categoryRepository.findById(id).orElse(null);
    }

    // Search category by category name
    @Override
    public List<TblCategoryEntity> search(String keyword) {
        List<TblCategoryEntity> rs = new ArrayList<>();
        List<TblCategoryEntity> categories = categoryRepository.findAll();

        for (TblCategoryEntity category : categories) {
            if (category.getName().contains(keyword)) {
                rs.add(category);
            }
        }

        return rs;
    }

    @Override
    public void insert(TblCategoryDto newCategory) {
        String categoryName = newCategory.getName();
        try {
            if (categoryRepository.existsByName(categoryName)) {
                throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR, TABLE_NAME, categoryName));
            }

            Date currentDate = Constants.getCurrentDay();

            TblCategoryEntity tblCategoryEntity = TblCategoryEntity.
                    create(
                            0,
                            categoryName,
                            newCategory.getIcon(),
                            currentDate,
                            currentDate,
                            newCategory.getDeletedAt(),
                            false
                    );
            categoryRepository.save(tblCategoryEntity);

        } catch (DataIntegrityViolationException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME) + ex.getMessage());
        }
    }

    @Transactional
    @Override
    public void update(TblCategoryEntity tblCategoryEntity) {
        if (categoryRepository.existsByName(tblCategoryEntity.getName())) {
            throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR, TABLE_NAME, tblCategoryEntity.getName()));
        }

        if (!categoryRepository.existsById(tblCategoryEntity.getId())) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, tblCategoryEntity.getId()));
        }

        try {
            TblCategoryEntity category = categoryRepository.findById(tblCategoryEntity.getId()).orElse(null);

            if (category != null) {
                category.setName(tblCategoryEntity.getName());
                category.setIcon(tblCategoryEntity.getIcon());
                category.setUpdatedAt(Constants.getCurrentDay());

                categoryRepository.save(category);
            }
        } catch (NullPointerException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME) + ex.getMessage());
        }
    }

    @Transactional
    @Override
    public void deletePermanently(int id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, id));
        }

        categoryRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void softDelete(int id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, id));
        }

        try {
            categoryRepository.deleteById(id);
            TblCategoryEntity category = categoryRepository.findById(id).orElse(null);
            if (category != null) {
                category.setDeleted(true);
                category.setDeletedAt(Constants.getCurrentDay());
                categoryRepository.save(category);
            }
        } catch (DataAccessException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME, id) + ex.getMessage());
        }
    }

    @Transactional
    @Override
    public void restore(int id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, id));
        }

        TblCategoryEntity category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            category.setDeleted(false);
            category.setDeletedAt(null);
            categoryRepository.save(category);
        }
    }
}
