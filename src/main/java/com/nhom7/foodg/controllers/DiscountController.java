package com.nhom7.foodg.controllers;


import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.models.dto.TblDiscountDto;
import com.nhom7.foodg.models.entities.TblCustomerEntity;
import com.nhom7.foodg.models.entities.TblDiscountEntity;
import com.nhom7.foodg.models.entities.TblProductEntity;
import com.nhom7.foodg.services.DiscountService;
import com.nhom7.foodg.shareds.Constants;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping(path = "/discounts")
//@CrossOrigin(origins = "*")
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
//check discount
@GetMapping("/checkDiscounts")
// [GET] http://localhost:8080/discounts/checkDiscounts?code=chung133&price=100.00
public ResponseEntity<FuncResult<BigDecimal>> checkDiscount(@RequestParam("code") String code,
                                                         @RequestParam("price") BigDecimal price ) {
    if(discountService.checkDiscount(code,price)!= null)
    {
        FuncResult<BigDecimal> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.ACCEPT_DISCOUNT, code),
                discountService.checkDiscount(code,price)

        );
        return ResponseEntity.ok(rs);
    }
     if(discountService.checkDiscount(code,price)== null){
        FuncResult<BigDecimal> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.NOT_ACCEPT_DISCOUNT, code),
                discountService.checkDiscount(code,price)

        );
        return ResponseEntity.ok(rs);
    }
    return null;


}
    @GetMapping(path = "{id}")
    // [GET] localhost:8080/discount/1
    public ResponseEntity<FuncResult<TblDiscountEntity>> getDiscountByID(@PathVariable("id") int id) {
        FuncResult<TblDiscountEntity> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.GET_DATA_SUCCESS, TABLE_NAME),
                discountService.getByID(id)
        );

        return ResponseEntity.ok(rs);
    }



    @PostMapping(path = "")
    // [POST] localhost:8080/discounts

    public ResponseEntity<FuncResult<TblDiscountEntity>> create(HttpSession httpSession, @RequestBody @Valid TblDiscountEntity tblDiscountEntity){

        if(httpSession.getAttribute("role") == null || !httpSession.getAttribute("role").equals("admin")){
            FuncResult<TblDiscountEntity> rs = FuncResult.create(
                    HttpStatus.OK,
                    "Ban Khong Phai ADMIN!!!",
                    null
            );
            return  ResponseEntity.ok(rs);
        }
        discountService.insert(tblDiscountEntity);
        FuncResult<TblDiscountEntity> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblDiscountEntity
        );
        return ResponseEntity.ok(rs);
    }


    @PutMapping(path = "")
    // [PUT] localhost:8080/discounts/1
    public ResponseEntity<FuncResult<TblDiscountEntity>> update(HttpSession httpSession, @RequestBody TblDiscountEntity tblDiscountEntity){
        if(httpSession.getAttribute("role") == null || !httpSession.getAttribute("role").equals("admin")){
            FuncResult<TblDiscountEntity> rs = FuncResult.create(
                    HttpStatus.OK,
                    "Ban Khong Phai ADMIN!!!",
                    null
            );
            return  ResponseEntity.ok(rs);
        }
        discountService.update(tblDiscountEntity);
        FuncResult<TblDiscountEntity> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblDiscountEntity
        );
        return ResponseEntity.ok(rs);
    }

    @DeleteMapping(path = "{discountID}")
    // [DELETE] localhost:8080/discounts/1
    public ResponseEntity<FuncResult<Integer>> softDelete(HttpSession httpSession, @PathVariable("discountID") int discountID){
        if(httpSession.getAttribute("role") == null || !httpSession.getAttribute("role").equals("admin")){
            FuncResult<Integer> rs = FuncResult.create(
                    HttpStatus.OK,
                    "Ban Khong Phai ADMIN!!!",
                    null
            );
            return  ResponseEntity.ok(rs);
        }
        discountService.softDelete(discountID);
        FuncResult<Integer> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.DELETE_SUCCESS, TABLE_NAME, discountID),
                discountID
        );
        return ResponseEntity.ok(rs);
    }
}



