package com.nhom7.foodg.controllers;

import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.models.entities.TblCategoryEntity;
import com.nhom7.foodg.models.entities.TblProductEntity;
import com.nhom7.foodg.services.CategoryService;
import com.nhom7.foodg.shareds.Constants;
import com.nhom7.foodg.utils.FuzzySearch;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping(path = "/categories")
//@CrossOrigin(origins = "*")
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

    // Get all category
    @GetMapping(path = "detail/{id}")
    // [GET] localhost:8080/categories/detail/1
    public ResponseEntity<FuncResult<TblCategoryEntity>> getById(HttpSession httpSession, @PathVariable(name = "id") int id) {
            FuncResult<TblCategoryEntity> rs = FuncResult.create(
                    HttpStatus.OK,
                    MessageFormat.format(Constants.GET_DATA_SUCCESS, TABLE_NAME),
                    categoryService.getByID(id)
            );
            return ResponseEntity.ok(rs);
    }
    // get category after searching by category name
    @GetMapping(path = "/search")
    // [GET] localhost:8080/categories/search?keyword=break
    public ResponseEntity<FuncResult<List<TblCategoryEntity>>> search(HttpSession httpSession, @RequestParam(name = "keyword", required = false, defaultValue = "") String name) {

        FuzzySearch<TblCategoryEntity> fuzzySearch = new FuzzySearch<>(categoryService.getAll());
        FuncResult<List<TblCategoryEntity>> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.SEARCH_SUCCESS, TABLE_NAME, name),
                fuzzySearch.FuzzySearchByName(name)
        );
        return ResponseEntity.ok(rs);
    }

    // get all product of category by category name
    @GetMapping(path = "{categoryName}")
        // [GET] localhost:8080/categories/breads?q=brea&_page=1&_limit=12&_order=asc&_sort=name
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
    public ResponseEntity<FuncResult<TblCategoryEntity>> create(HttpSession httpSession, @RequestBody TblCategoryEntity tblCategoryEntity) {
        if(httpSession.getAttribute("role") == null || !httpSession.getAttribute("role").equals("admin")){
            FuncResult<TblCategoryEntity> rs = FuncResult.create(
                    HttpStatus.OK,
                    "Ban Khong Phai ADMIN!!!",
                    null
            );
            return  ResponseEntity.ok(rs);
        }
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
    // [PUT] localhost:8080/categories
    public ResponseEntity<FuncResult<TblCategoryEntity>> update(HttpSession httpSession, @RequestBody TblCategoryEntity tblCategoryEntity) {
        if(httpSession.getAttribute("role") == null || !httpSession.getAttribute("role").equals("admin")){
            FuncResult<TblCategoryEntity> rs = FuncResult.create(
                    HttpStatus.OK,
                    "Ban Khong Phai ADMIN!!!",
                    null
            );
            return  ResponseEntity.ok(rs);
        }
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
    // [DELETE] localhost:8080/categories
    public ResponseEntity<FuncResult<Integer>> softDelete(HttpSession httpSession, @PathVariable("categoryID") int categoryID) {
        if(httpSession.getAttribute("role") == null || !httpSession.getAttribute("role").equals("admin")){
            FuncResult<Integer> rs = FuncResult.create(
                    HttpStatus.OK,
                    "Ban Khong Phai ADMIN!!!",
                    null
            );
            return ResponseEntity.ok(rs);
        }
        categoryService.softDelete(categoryID);
        FuncResult<Integer> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.DELETE_SUCCESS, TABLE_NAME, categoryID),
                categoryID
        );

        return ResponseEntity.ok(rs);
    }
}
