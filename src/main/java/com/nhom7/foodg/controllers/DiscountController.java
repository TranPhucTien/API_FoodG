package com.nhom7.foodg.controllers;


import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.models.dto.TblDiscountDto;
import com.nhom7.foodg.models.entities.TblDiscountEntity;
import com.nhom7.foodg.services.DiscountService;
import com.nhom7.foodg.shareds.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping(path = "/discounts")
@CrossOrigin(origins = "*")
public class DiscountController {
    private final DiscountService discountService;
    private final String TABLE_NAME = "tbl_discount";

    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping(path = "")
    // [GET] localhost:8080/discounts
    public ResponseEntity<FuncResult<List<TblDiscountEntity>>> getAll() {
        FuncResult<List<TblDiscountEntity>> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.GET_DATA_SUCCESS, TABLE_NAME),
                discountService.getAll()
        );
        return ResponseEntity.ok(rs);
    }


    @GetMapping(path = "/search")
    // [GET] localhost:8080/discounts/search?keyword=Sale
    public ResponseEntity<FuncResult<List<TblDiscountEntity>>> search(@RequestParam(name = "keyword", required = false, defaultValue = "") String code){
        FuncResult<List<TblDiscountEntity>> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.SEARCH_SUCCESS, TABLE_NAME, code),
                discountService.search(code)
        );
        return  ResponseEntity.ok(rs);
    }


    @PostMapping(path = "")
    // [POST] localhost:8080/discounts
    public ResponseEntity<FuncResult<TblDiscountDto>> create(@RequestBody TblDiscountDto tblDiscountDto){
        discountService.insert(tblDiscountDto);

        FuncResult<TblDiscountDto> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblDiscountDto
        );
        return ResponseEntity.ok(rs);
    }


    @PutMapping(path = "")
    // [PUT] localhost:8080/discounts/1
    public ResponseEntity<FuncResult<TblDiscountEntity>> update(@RequestBody TblDiscountEntity tblDiscountEntity){
        discountService.update(tblDiscountEntity);

        FuncResult<TblDiscountEntity> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblDiscountEntity
        );
        return ResponseEntity.ok(rs);
    }

    @DeleteMapping(path = "{discountID}")
    // [DELETE] localhost:8080/categories/1
    public ResponseEntity<FuncResult<Integer>> softDelete(@PathVariable("discountID") int discountID){
        discountService.softDelete(discountID);
        FuncResult<Integer> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.DELETE_SUCCESS, TABLE_NAME, discountID),
                discountID
        );
        return ResponseEntity.ok(rs);
    }
}