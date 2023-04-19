package com.nhom7.foodg.controllers;

import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.models.entities.TblProductEntity;
import com.nhom7.foodg.services.ProductService;
import com.nhom7.foodg.shareds.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping(path = "/products")
@CrossOrigin(origins = "*")
//localhost:8080/products
public class ProductController {
    private final ProductService productService;
    private final String TABLE_NAME = "tbl_product";

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Get all product
    @GetMapping(path = "")
    // [GET] localhost:8080/products
    public ResponseEntity<FuncResult<List<TblProductEntity>>> getAll() {
        FuncResult<List<TblProductEntity>> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.GET_DATA_SUCCESS, TABLE_NAME),
                productService.getAll()
        );

        return ResponseEntity.ok(rs);
    }

    // create new product
    @PostMapping(path = "")
    // [POST] localhost:8080/products
    public ResponseEntity<FuncResult<TblProductEntity>> create(@RequestBody TblProductEntity tblProductEntity) {
        productService.insert(tblProductEntity);

        FuncResult<TblProductEntity> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblProductEntity
        );

        return ResponseEntity.ok(rs);
    }
}
