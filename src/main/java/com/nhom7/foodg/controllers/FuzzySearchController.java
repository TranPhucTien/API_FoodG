package com.nhom7.foodg.controllers;

import com.nhom7.foodg.models.entities.TblCategoryEntity;
import com.nhom7.foodg.models.entities.TblProductEntity;
import com.nhom7.foodg.services.CategoryService;
import com.nhom7.foodg.services.ProductService;
import com.nhom7.foodg.utils.FuzzySearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

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

    @GetMapping("/FuzzySearchCategories/name={name}")
    public ResponseEntity<ArrayList<TblCategoryEntity>> FuzzySearchCategories(@PathVariable("name") String name){
        FuzzySearch<TblCategoryEntity> fuzzySearch = new FuzzySearch<>(categoryService.getAll());
        return ResponseEntity.ok(fuzzySearch.FuzzySearchByName(name));
    }
}
