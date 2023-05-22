package com.nhom7.foodg.controllers;

import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.models.entities.TblCategoryEntity;
import com.nhom7.foodg.models.entities.TblProductEntity;
import com.nhom7.foodg.services.CategoryService;
import com.nhom7.foodg.services.ProductService;
import com.nhom7.foodg.shareds.Constants;
import com.nhom7.foodg.utils.Algorithm;
import com.nhom7.foodg.utils.FuzzySearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping
public class FuzzySearchController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public FuzzySearchController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/FuzzySearchProducts/name={name}")
    public ResponseEntity<ArrayList<TblProductEntity>> FuzzySearchProducts(@PathVariable("name") String name){
        FuzzySearch<TblProductEntity> fuzzySearch = new FuzzySearch<>(productService.getAll());
        return ResponseEntity.ok(fuzzySearch.FuzzySearchByName(name));
    }

    @GetMapping("/FuzzySearchProducts/productName={productName}")
    // ex: http://localhost:8080/FuzzySearchProducts/productName=a?q=brea&_page=2&_limit=2&_order=asc&_sort=name
    public ResponseEntity<FuncResult<ArrayList<ArrayList<TblProductEntity>>>> getProductsV2(@PathVariable("productName") String productName,
                                                                                               @RequestParam(name = "q", required = false, defaultValue = "") String q,
                                                                                               @RequestParam(name = "_page", required = false, defaultValue = "1") String page,
                                                                                               @RequestParam(name = "_limit", required = false, defaultValue = "-1") String limit,
                                                                                               @RequestParam(name = "_order", required = false, defaultValue = "asc") String order,
                                                                                               @RequestParam(name = "_sort", required = false, defaultValue = "name") String sort
    ) throws NoSuchMethodException {
        int pageInt;
        int limitInt;
        FuncResult<ArrayList<ArrayList<TblProductEntity>>> rs = FuncResult.create(
                null,
                null,
                null
        );
        try{
            pageInt = Integer.parseInt(page);
            limitInt = Integer.parseInt(limit);
        }catch (NumberFormatException e){
            rs.setStatus(HttpStatus.BAD_REQUEST);
            rs.setMessage(MessageFormat.format(Constants.REQUIRE_TYPE, "số", "_page, _limit"));
            return ResponseEntity.badRequest().body(rs);
        }

        FuzzySearch<TblProductEntity> fuzzySearch = new FuzzySearch<>(productService.getAll());
        List<TblProductEntity> temp = fuzzySearch.FuzzySearchByName(productName); // Tìm kiếm mờ = categoryName


        Algorithm<TblProductEntity> algorithm = new Algorithm<>();
        //Sắp xếp = Comparator get'sort'
        temp.sort(algorithm.getComparatorByName(sort, TblProductEntity.class));

        if(order.equals("desc")){ //Kiểm tra sắp xếp
            Collections.reverse(temp);
        }

        if(limitInt < 0){ //Chia trang nếu không limit
            limitInt = temp.size();
        }

        //Trả kết quả
        ArrayList<ArrayList<TblProductEntity>> result = algorithm.splitList(pageInt, limitInt, temp); //Mảng cuối

        String TABLE_NAME = "PRODUCT";
        rs.setMessage(MessageFormat.format(Constants.GET_DATA_SUCCESS, TABLE_NAME));
        rs.setStatus(HttpStatus.OK);
        rs.setData(result);
        return ResponseEntity.ok(rs);
    }

    @GetMapping("/FuzzySearchCategories/name={name}")
    public ResponseEntity<ArrayList<TblCategoryEntity>> FuzzySearchCategories(@PathVariable("name") String name){
        FuzzySearch<TblCategoryEntity> fuzzySearch = new FuzzySearch<>(categoryService.getAll());
        return ResponseEntity.ok(fuzzySearch.FuzzySearchByName(name));
    }
    @GetMapping("/FuzzySearchCategories/categoryName={categoryName}")
    // ex: http://localhost:8080/FuzzySearchCategories/categoryName=a?q=brea&_page=2&_limit=2&_order=asc&_sort=name
    public ResponseEntity<FuncResult<ArrayList<ArrayList<TblCategoryEntity>>>> getCategoriesV2(@PathVariable("categoryName") String categoryName,
                                                                                     @RequestParam(name = "q", required = false, defaultValue = "") String q,
                                                                                     @RequestParam(name = "_page", required = false, defaultValue = "1") String page,
                                                                                     @RequestParam(name = "_limit", required = false, defaultValue = "-1") String limit,
                                                                                     @RequestParam(name = "_order", required = false, defaultValue = "asc") String order,
                                                                                     @RequestParam(name = "_sort", required = false, defaultValue = "name") String sort
                                                                                     ) throws NoSuchMethodException {
        int pageInt;
        int limitInt;
        FuncResult<ArrayList<ArrayList<TblCategoryEntity>>> rs = FuncResult.create(
                null,
                null,
                null
        );
        try{
            pageInt = Integer.parseInt(page);
            limitInt = Integer.parseInt(limit);
        }catch (NumberFormatException e){
            rs.setStatus(HttpStatus.BAD_REQUEST);
            rs.setMessage(MessageFormat.format(Constants.REQUIRE_TYPE, "số", "_page, _limit"));
            return ResponseEntity.badRequest().body(rs);
        }

        FuzzySearch<TblCategoryEntity> fuzzySearch = new FuzzySearch<>(categoryService.getAll());
        List<TblCategoryEntity> temp = fuzzySearch.FuzzySearchByName(categoryName); // Tìm kiếm mờ = categoryName


        Algorithm<TblCategoryEntity> algorithm = new Algorithm<>();
        //Sắp xếp = Comparator get'sort'
        temp.sort(algorithm.getComparatorByName(sort, TblCategoryEntity.class));

        if(order.equals("desc")){ //Kiểm tra sắp xếp
            Collections.reverse(temp);
        }

        if(limitInt == -1){ //Chia trang nếu không limit
            limitInt = temp.size();
        }

        //Trả kết quả
        ArrayList<ArrayList<TblCategoryEntity>> result = algorithm.splitList(pageInt, limitInt, temp); //Mảng cuối

        String TABLE_NAME = "CATEGORY";
        rs.setMessage(MessageFormat.format(Constants.GET_DATA_SUCCESS, TABLE_NAME));
        rs.setStatus(HttpStatus.OK);
        rs.setData(result);
        return ResponseEntity.ok(rs);
    }
}
