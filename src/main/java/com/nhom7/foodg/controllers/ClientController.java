package com.nhom7.foodg.controllers;

import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.models.dto.TblCustomerDto;
import com.nhom7.foodg.models.entities.TblCustomerEntity;
import com.nhom7.foodg.services.ClientService;
import com.nhom7.foodg.services.CustomerService;
import com.nhom7.foodg.shareds.Constants;
import com.nhom7.foodg.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private final CustomerService customerService;
    private final String TABLE_NAME = "tbl_customer";

    public ClientController(CustomerService customerService) {
        this.customerService = customerService;
    }


//    @PostMapping(path = "/register/value")
//    public ResponseEntity<FuncResult<List<TblCustomerDto>>> search(@RequestParam(name = "keyword", required = false, defaultValue = "") String fullName){
//
//        FuncResult<TblCustomerDto> rs = FuncResult.create(
//                HttpStatus.OK,
//                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
//                tblCustomerDto
//        );
//        return ResponseEntity.ok(rs);
//    }

    @PostMapping(path = "/register")
    public ResponseEntity<FuncResult<TblCustomerDto>> create(@RequestBody TblCustomerDto tblCustomerDto){
        String Otp = DataUtil.generateTempPwd(6);
        String userName = tblCustomerDto.getUsername();
            TblCustomerEntity tblCustomerEntity = TblCustomerEntity.create(
                    0,
                    tblCustomerDto.getUsername(),
                    tblCustomerDto.getPassword(),
                    tblCustomerDto.getEmail(),
                    tblCustomerDto.getFullName(),
                    tblCustomerDto.getGender(),
                    tblCustomerDto.getAvatar(),
                    tblCustomerDto.getIdProvince(),
                    null,
                    null,
                    null,
                    false,
                    tblCustomerDto.getBirthday(),
                    Otp,
                    Constants.getCurrentDay(),
                    2
            );
            clientService.create(tblCustomerEntity);
        customerService.insert(tblCustomerEntity);

        FuncResult<TblCustomerDto> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblCustomerDto
        );
        return ResponseEntity.ok(rs);
    }
}