package com.nhom7.foodg.controllers;

import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.models.dto.TblCustomerDto;
import com.nhom7.foodg.models.entities.TblCustomerEntity;
import com.nhom7.foodg.services.CustomerService;
import com.nhom7.foodg.shareds.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping(path = "/customers")
@CrossOrigin(origins = "*")
public class CustomerController {
    private final CustomerService customerService;
    private final String TABLE_NAME = "tbl_customer";

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = "")
    // [GET] localhost:8080/customers
    public ResponseEntity<FuncResult<List<TblCustomerEntity>>> getAll() {
        FuncResult<List<TblCustomerEntity>> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.GET_DATA_SUCCESS, TABLE_NAME),
                customerService.getAll()
        );
        return ResponseEntity.ok(rs);
    }


    @GetMapping(path = "/search")
    // [GET] localhost:8080/customers/search?keyword=Nguyen
    public ResponseEntity<FuncResult<List<TblCustomerEntity>>> search(@RequestParam(name = "keyword", required = false, defaultValue = "") String fullName){
        FuncResult<List<TblCustomerEntity>> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.SEARCH_SUCCESS, TABLE_NAME, fullName),
                customerService.search(fullName)
        );
        return  ResponseEntity.ok(rs);
    }


    @PostMapping(path = "")
    // [POST] localhost:8080/customers
    public ResponseEntity<FuncResult<TblCustomerDto>> create(@RequestBody TblCustomerDto tblCustomerDto){
        customerService.insert(tblCustomerDto);

        FuncResult<TblCustomerDto> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblCustomerDto
        );
        return ResponseEntity.ok(rs);
    }


    @PutMapping(path = "")
    // [PUT] localhost:8080/customers
    public ResponseEntity<FuncResult<TblCustomerEntity>> update(@RequestBody TblCustomerEntity tblCustomerEntity){
        customerService.update(tblCustomerEntity);

        FuncResult<TblCustomerEntity> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblCustomerEntity
        );
        return ResponseEntity.ok(rs);
    }

    @DeleteMapping(path = "{customerID}")
    // [DELETE] localhost:8080/customers/1
    public ResponseEntity<FuncResult<Integer>> softDelete(@PathVariable("customerID") int customerID){
        customerService.softDelete(customerID);
        FuncResult<Integer> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.DELETE_SUCCESS, TABLE_NAME, customerID),
                customerID
        );
        return ResponseEntity.ok(rs);
    }


}