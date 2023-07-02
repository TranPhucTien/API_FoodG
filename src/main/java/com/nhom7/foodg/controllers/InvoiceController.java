package com.nhom7.foodg.controllers;

import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.models.dto.*;
import com.nhom7.foodg.models.entities.TblInvoiceEntity;
import com.nhom7.foodg.models.entities.TblLineEntity;
import com.nhom7.foodg.services.InvoiceService;
import com.nhom7.foodg.shareds.Constants;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping(path = "/invoices")
//@CrossOrigin(origins = "*")

public class InvoiceController {
    private final InvoiceService invoiceService;
    private final String TABLE_NAME = "tbl_invoice";

    @Autowired

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // Get all category
//create(
//            HttpStatus.OK,
//            MessageFormat.format(Constants.GET_DATA_SUCCESS,TABLE_NAME),
//            invoiceService.getById(customerId, invoiceId)
//    );
    @GetMapping(path = "invDto/{Id}")

    public ResponseEntity<FuncResult<TblInvoiceOutDto>> getByID(HttpSession httpSession, @PathVariable(name = "Id") int Id) {
        if(httpSession.getAttribute("role") == null || (!httpSession.getAttribute("role").equals("admin") && !httpSession.getAttribute("role").equals("customer"))){
            FuncResult<TblInvoiceOutDto> rs = FuncResult.create(
                    HttpStatus.OK,
                    "Ban Chua Dang Nhap",
                    null
            );
            return  ResponseEntity.ok(rs);
        }
        FuncResult<TblInvoiceOutDto> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.GET_DATA_SUCCESS,TABLE_NAME),
                invoiceService.getById(Id)
                );
        return ResponseEntity.ok(rs);
    }
    @GetMapping(path = "")
    // [GET] localhost:8080/invoices
    public ResponseEntity<FuncResult<List<TblInvoiceEntity>>> getAll(HttpSession httpSession) {
        if(httpSession.getAttribute("role") == null || !httpSession.getAttribute("role").equals("admin")){
            FuncResult<List<TblInvoiceEntity>> rs = FuncResult.create(
                    HttpStatus.OK,
                    "Ban Khong Phai ADMIN!!!",
                    null
            );
            return  ResponseEntity.ok(rs);
        }
        FuncResult<List<TblInvoiceEntity>> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.GET_DATA_SUCCESS, TABLE_NAME),
                invoiceService.getAll()
        );

        return ResponseEntity.ok(rs);
    }

    // create new invoice
    @PostMapping(path = "")
    // [POST] localhost:8080/invoices?codeDiscount=codeDiscount
    //ggiwof sửa lại cái này ntn a?ê
    public ResponseEntity<FuncResult<TblInvoiceLineDto>> create(HttpSession httpSession, @RequestBody TblInvoiceLineDto tblInvoiceLineDto,
                                                                @RequestParam(name = "codeDiscount", required = false) String codeDiscount){

//        if(httpSession.getAttribute("role") == null || !httpSession.getAttribute("role").equals("admin")){
//            FuncResult<TblInvoiceLineDto> rs = FuncResult.create(
//                    HttpStatus.OK,
//                    "Ban Khong Phai ADMIN!!!",
//                    null
//            );
//            return  ResponseEntity.ok(rs);
//        }
        invoiceService.insert(tblInvoiceLineDto,codeDiscount);

        FuncResult<TblInvoiceLineDto> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblInvoiceLineDto
        );

        return ResponseEntity.ok(rs);
    }
    @PutMapping(path = "")
    // [PUT] localhost:8080/invoices
    public ResponseEntity<FuncResult<TblInvoiceEntity>> update(HttpSession httpSession, @RequestBody TblInvoiceEntity tblInvoiceEntity) {
//        if(httpSession.getAttribute("role") == null || !httpSession.getAttribute("role").equals("admin")){
//            FuncResult<TblInvoiceEntity> rs = FuncResult.create(
//                    HttpStatus.OK,
//                    "Ban Khong Phai ADMIN!!!",
//                    null
//            );
//            return  ResponseEntity.ok(rs);
//        }
        invoiceService.update(tblInvoiceEntity);

        FuncResult<TblInvoiceEntity> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblInvoiceEntity
        );

        return ResponseEntity.ok(rs);
    }


    // solf delete invoice by invoice id
    @DeleteMapping(path = "{invoiceID}")
    // [DELETE] localhost:8080/invoices
    public ResponseEntity<FuncResult<Integer>> softDelete(HttpSession httpSession, @PathVariable("invoiceID") int invoiceId) {
        if(httpSession.getAttribute("role") == null || !httpSession.getAttribute("role").equals("admin")){
            FuncResult<Integer> rs = FuncResult.create(
                    HttpStatus.OK,
                    "Ban Khong Phai ADMIN!!!",
                    null
            );
            return  ResponseEntity.ok(rs);
        }
        invoiceService.softDelete(invoiceId);

        FuncResult<Integer> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.DELETE_SUCCESS, TABLE_NAME, invoiceId),
                invoiceId
        );

        return ResponseEntity.ok(rs);
    }

    // Chu trình

}
