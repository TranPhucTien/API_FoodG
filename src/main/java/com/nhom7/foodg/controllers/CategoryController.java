package com.nhom7.foodg.controllers;

import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.models.dto.TblCategoryDto;
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
//localhost:8080/categories
public class CategoryController {
    private final CategoryService categoryService;
    private final String TABLE_NAME = "tbl_category";

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Get all category
    @GetMapping(path = "")
    // [GET] localhost:8080/categories
    public ResponseEntity<FuncResult<List<TblCategoryEntity>>> getAll() {
        FuncResult<List<TblCategoryEntity>> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.GET_DATA_SUCCESS, TABLE_NAME),
                categoryService.getAll()
        );

        return ResponseEntity.ok(rs);
    }

    // get category after searching by category name
    @GetMapping(path = "/search")
    // [GET] localhost:8080/categories/search?keyword=brea
    public ResponseEntity<FuncResult<List<TblCategoryEntity>>> search(@RequestParam(name = "keyword", required = false, defaultValue = "") String name) {
        FuncResult<List<TblCategoryEntity>> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.SEARCH_SUCCESS, TABLE_NAME, name),
                categoryService.search(name)
        );

        return ResponseEntity.ok(rs);
    }

    // get all product of category by category name
    @GetMapping(path = "{categoryName}")
    // [GET] localhost:8080/categories/breads
    public ResponseEntity<FuncResult<List<TblProductEntity>>> getProductsByCategoryName(@PathVariable("categoryName") String categoryName,
                                                                                        @RequestParam(name = "_page", required = false, defaultValue = "-1") String page,
                                                                                        @RequestParam(name = "_limit", required = false, defaultValue = "-1") String limit,
                                                                                        @RequestParam(name = "q", required = false, defaultValue = "") String q,
                                                                                        @RequestParam(name = "_order", required = false, defaultValue = "") String order,
                                                                                        @RequestParam(name = "_sort", required = false, defaultValue = "") String sort) {
        int pageInt;
        int limitInt;

        FuncResult<List<TblProductEntity>> rs = FuncResult.create(
                null,
                null,
                null
        );

        try {
            pageInt = Integer.parseInt(page);
            limitInt = Integer.parseInt(limit);
        } catch (NumberFormatException e) {
            rs.setStatus(HttpStatus.BAD_REQUEST);
            rs.setMessage(MessageFormat.format(Constants.REQUIRE_TYPE, "số", "_page, _limit"));
            return ResponseEntity.badRequest().body(rs);
        }

        List<TblProductEntity> data = categoryService.getProductsByCategory(categoryName, pageInt, limitInt, q, sort, order);

        rs.setStatus(HttpStatus.OK);
        rs.setMessage(MessageFormat.format(Constants.GET_DATA_SUCCESS, TABLE_NAME));
        rs.setData(data);

        return ResponseEntity.ok(rs);
    }

    // create new category
    @PostMapping(path = "")
    // [POST] localhost:8080/categories
    public ResponseEntity<FuncResult<TblCategoryDto>> create(@RequestBody TblCategoryDto tblCategoryDto) {
        categoryService.insert(tblCategoryDto);

        FuncResult<TblCategoryDto> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblCategoryDto
        );

        return ResponseEntity.ok(rs);
    }

    // update name of category by category id
    @PutMapping(path = "")
    // [PUT] localhost:8080/categories/1
    public ResponseEntity<FuncResult<TblCategoryEntity>> update(@RequestBody TblCategoryEntity tblCategoryEntity) {
        categoryService.update(tblCategoryEntity);

        FuncResult<TblCategoryEntity> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblCategoryEntity
        );

        return ResponseEntity.ok(rs);
    }

    // solf delete category by category id
    @DeleteMapping(path = "{categoryID}")
    // [DELETE] localhost:8080/categories/1
    public ResponseEntity<FuncResult<Integer>> softDelete(@PathVariable("categoryID") int categoryID) {
        categoryService.softDelete(categoryID);

        FuncResult<Integer> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.DELETE_SUCCESS, TABLE_NAME, categoryID),
                categoryID
        );

        return ResponseEntity.ok(rs);
    }
}
