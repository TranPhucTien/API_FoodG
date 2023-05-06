package com.nhom7.foodg.controllers;

import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.models.dto.TblProductDto;
import com.nhom7.foodg.models.entities.TblProductEntity;
import com.nhom7.foodg.models.entities.TblProductLogEntity;
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

    // Get all deleted product
    @GetMapping(path = "/deleted")
    // [GET] localhost:8080/products
    public ResponseEntity<FuncResult<List<TblProductEntity>>> getDeletedProducts() {
        FuncResult<List<TblProductEntity>> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.GET_DATA_SUCCESS, TABLE_NAME),
                productService.getDeletedProducts()
        );

        return ResponseEntity.ok(rs);
    }

    // Get product by id
    @GetMapping(path = "{id}")
    // [GET] localhost:8080/products/001-5-pound-sausage-sampler
    public ResponseEntity<FuncResult<TblProductEntity>> getProductsByID(@PathVariable("id") String id) {
        FuncResult<TblProductEntity> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.GET_DATA_SUCCESS, TABLE_NAME),
                productService.getByID(id)
        );

        return ResponseEntity.ok(rs);
    }

    @GetMapping(path = "edit-history/{id}")
    // [GET] localhost:8080/products/edit-history/1
    public ResponseEntity<FuncResult<List<TblProductLogEntity>>> getEditHistoryByID(@PathVariable("id") String id) {
        FuncResult<List<TblProductLogEntity>> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.GET_DATA_SUCCESS, TABLE_NAME),
                productService.getEditHistoryByProductIdAndAction(id, "UPDATE")
        );

        return ResponseEntity.ok(rs);
    }

    // get products after searching by product name
    @GetMapping(path = "/search")
    // [GET] localhost:8080/products/search?keyword=brea
    public ResponseEntity<FuncResult<List<TblProductEntity>>> search(@RequestParam(name = "keyword", required = false, defaultValue = "") String name) {
        FuncResult<List<TblProductEntity>> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.SEARCH_SUCCESS, TABLE_NAME, name),
                productService.search(name)
        );

        return ResponseEntity.ok(rs);
    }

    // create new product
    @PostMapping(path = "")
    // [POST] localhost:8080/products
    public ResponseEntity<FuncResult<TblProductDto>> create(@RequestBody TblProductDto tblProductDto) {
        productService.insert(tblProductDto);

        FuncResult<TblProductDto> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblProductDto
        );

        return ResponseEntity.ok(rs);
    }

    // update name of product by product id
    @PutMapping(path = "")
    // [PUT] localhost:8080/products
    public ResponseEntity<FuncResult<TblProductEntity>> update(@RequestBody TblProductEntity tblProductEntity) {
        productService.update(tblProductEntity);

        FuncResult<TblProductEntity> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblProductEntity
        );

        return ResponseEntity.ok(rs);
    }


    // soft delete product by product id
    @DeleteMapping(path = "{id}")
    // [DELETE] localhost:8080/categories/1
    public ResponseEntity<FuncResult<String>> softDelete(@PathVariable("id") String id) {
        productService.softDelete(id);

        FuncResult<String> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.DELETE_SUCCESS, TABLE_NAME, id),
                id
        );

        return ResponseEntity.ok(rs);
    }

    @PutMapping(path = "/restore/{id}")
    // [PUT] localhost:8080/categories/restore/1
    public ResponseEntity<FuncResult<String>> restore(@PathVariable("id") String id) {
        productService.restore(id);

        FuncResult<String> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.RESTORE_SUCCESS, TABLE_NAME, id),
                id
        );

        return ResponseEntity.ok(rs);

    }
}
