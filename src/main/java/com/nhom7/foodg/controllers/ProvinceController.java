package com.nhom7.foodg.controllers;

import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.models.entities.TblProvinceEntity;
import com.nhom7.foodg.models.entities.TblProvinceEntity;
import com.nhom7.foodg.services.ProvinceService;
import com.nhom7.foodg.shareds.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping(path = "/provinces")
@CrossOrigin(origins = "*")
//localhost:8080/provinces
public class ProvinceController {
    private final ProvinceService provinceService;
    private final String TABLE_NAME = "tbl_province";

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping(path = "")
    // [GET] localhost:8080/provinces
    public ResponseEntity<FuncResult<List<TblProvinceEntity>>> getAll() {
        FuncResult<List<TblProvinceEntity >> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.GET_DATA_SUCCESS, TABLE_NAME),
                provinceService.getAll()
        );

        return ResponseEntity.ok(rs);
    }

    @GetMapping(path = "/search")
    // [GET] localhost:8080/provinces/search?keyword=Xã
    public ResponseEntity<FuncResult<List<TblProvinceEntity>>> search(@RequestParam(name = "keyword", required = false, defaultValue = "") String ward) {
        FuncResult<List<TblProvinceEntity>> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.SEARCH_SUCCESS, TABLE_NAME, ward),
                provinceService.search(ward)
        );

        return ResponseEntity.ok(rs);
    }

    @PostMapping(path = "")
    // [POST] localhost:8080/provinces

    public ResponseEntity<FuncResult<TblProvinceEntity>> create( @RequestBody @Valid TblProvinceEntity tblProvinceEntity){
        provinceService.insert(tblProvinceEntity);

        FuncResult<TblProvinceEntity> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblProvinceEntity
        );
        return ResponseEntity.ok(rs);
    }


    @PutMapping(path = "")
    // [PUT] localhost:8080/provinces/1
    public ResponseEntity<FuncResult<TblProvinceEntity>> update(@RequestBody TblProvinceEntity tblProvinceEntity){
        provinceService.update(tblProvinceEntity);

        FuncResult<TblProvinceEntity> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblProvinceEntity
        );
        return ResponseEntity.ok(rs);
    }


}