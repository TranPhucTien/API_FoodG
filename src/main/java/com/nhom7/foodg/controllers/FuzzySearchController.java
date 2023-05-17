package com.nhom7.foodg.controllers;

import com.nhom7.foodg.models.entities.TblCategoryEntity;
import com.nhom7.foodg.models.entities.TblProductEntity;
import com.nhom7.foodg.services.CategoryService;
import com.nhom7.foodg.services.FuzzySearchService;
import com.nhom7.foodg.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping
public class FuzzySearchController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final FuzzySearchService fuzzySearchService;
    @Autowired
    public FuzzySearchController(ProductService productService, CategoryService categoryService, FuzzySearchService fuzzySearchService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.fuzzySearchService = fuzzySearchService;
    }

    @GetMapping("/FuzzySearchProducts/name={name}")
    public ResponseEntity<ArrayList<String>> FuzzySearchProducts(@PathVariable("name") String name){
        List<TblProductEntity> arr = productService.getAll();
        ArrayList<String> r = new ArrayList<String>();
        for(int i = 0; i < arr.size(); i++){
            r.add(arr.get(i).getName());
        }
        ArrayList<String> result = new ArrayList<String>();
        result = fuzzySearchService.ResultFuzzySearch(r, name);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/FuzzySearchCategories/name={name}")
    public ResponseEntity<ArrayList<String>> FuzzySearchCategories(@PathVariable("name") String name){
        List<TblCategoryEntity> arr = categoryService.getAll();
        ArrayList<String> r = new ArrayList<String>();
        for(int i = 0; i < arr.size(); i++){
            r.add(arr.get(i).getName());
        }
        ArrayList<String> result = new ArrayList<String>();
        result = fuzzySearchService.ResultFuzzySearch(r, name);
        return ResponseEntity.ok(result);
    }
}
