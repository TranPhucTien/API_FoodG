package com.nhom7.foodg.controllers;

import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.models.entities.TblCategoryEntity;
import com.nhom7.foodg.models.entities.TblProductEntity;
import com.nhom7.foodg.services.CategoryService;
import com.nhom7.foodg.shareds.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@CrossOrigin(origins = "*")
//https:localhost:8080/categories
public class CategoryController {
    private final CategoryService categoryService;
    private final String TABLE_NAME = "tbl_category";

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Get all category
    @GetMapping(path = "")
    // [GET] https:localhost:8080/categories
    public ResponseEntity<FuncResult<List<TblCategoryEntity>>> getAll() {
        FuncResult<List<TblCategoryEntity>> rs = new FuncResult<>();
        rs.data = categoryService.getAll();
        rs.setMessage(MessageFormat.format(Constants.GET_DATA_SUCCESS, TABLE_NAME));
        rs.setStatus(HttpStatus.OK);

        return ResponseEntity.ok(rs);
    }

    // get category after searching by category name
    @GetMapping(path = "/search")
    // [GET] https:localhost:8080/categories/search?keyword=brea
    public ResponseEntity<FuncResult<List<TblCategoryEntity>>> searchCategory(@RequestParam(name = "keyword", required = false, defaultValue = "") String name) {
        FuncResult<List<TblCategoryEntity>> rs = new FuncResult<>();
        rs.data = categoryService.searchCategory(name);
        rs.setMessage(MessageFormat.format(Constants.SEARCH_SUCCESS, TABLE_NAME, name));
        rs.setStatus(HttpStatus.OK);

        return ResponseEntity.ok(rs);
    }

    // get all product of category by category name
    @GetMapping(path = "{categoryName}")
    // [GET] https:localhost:8080/categories/breads
    public ResponseEntity<FuncResult<List<TblProductEntity>>> getProductsByCategoryName(@PathVariable("categoryName") String categoryName) {
        FuncResult<List<TblProductEntity>> rs = new FuncResult<>();
        rs.data = categoryService.getProductsByCategory(categoryName);
        rs.setMessage(MessageFormat.format(Constants.GET_DATA_SUCCESS, TABLE_NAME));
        rs.setStatus(HttpStatus.OK);

        return ResponseEntity.ok(rs);
    }

    // create new category
    @PostMapping(path = "")
    // [POST] https:localhost:8080/categories
    public ResponseEntity<FuncResult<TblCategoryEntity>> createCategory(@RequestBody TblCategoryEntity tblCategoryEntity) {
        categoryService.insert(tblCategoryEntity);

        FuncResult<TblCategoryEntity> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblCategoryEntity
        );

        return ResponseEntity.ok(rs);
    }

    // update name of category by category id
    @PutMapping(path = "")
    // [PUT] https:localhost:8080/categories/1
    public ResponseEntity<FuncResult<TblCategoryEntity>> updateCategory(@RequestBody TblCategoryEntity tblCategoryEntity) {
        categoryService.update(tblCategoryEntity);

        FuncResult<TblCategoryEntity> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblCategoryEntity
        );

        return ResponseEntity.ok(rs);
    }

    // delete category by category id
    @DeleteMapping(path = "{categoryID}")
    // [DELETE] https:localhost:8080/categories/1
    public ResponseEntity<FuncResult<Integer>> deleteCategory(@PathVariable("categoryID") int categoryID) {
        categoryService.solfDelete(categoryID);

        FuncResult<Integer> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.DELETE_SUCCESS, TABLE_NAME, categoryID),
                categoryID
        );

        return ResponseEntity.ok(rs);
    }
}
