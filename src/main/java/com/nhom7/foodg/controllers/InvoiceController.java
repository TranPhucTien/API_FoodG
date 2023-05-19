package com.nhom7.foodg.controllers;

import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.models.dto.TblCategoryDto;
import com.nhom7.foodg.models.dto.TblInvoiceDto;
import com.nhom7.foodg.models.entities.TblCategoryEntity;
import com.nhom7.foodg.models.entities.TblInvoiceEntity;
import com.nhom7.foodg.services.InvoiceService;
import com.nhom7.foodg.shareds.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping(path = "/invoices")
@CrossOrigin(origins = "*")

public class InvoiceController {
    private final InvoiceService invoiceService;
    private final String TABLE_NAME = "tbl_invoice";

    @Autowired

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // Get all category
    @GetMapping(path = "")
    // [GET] localhost:8080/invoices
    public ResponseEntity<FuncResult<List<TblInvoiceEntity>>> getAll() {
        FuncResult<List<TblInvoiceEntity>> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.GET_DATA_SUCCESS, TABLE_NAME),
                invoiceService.getAll()
        );

        return ResponseEntity.ok(rs);
    }


    // create new invoice
    @PostMapping(path = "")
    // [POST] localhost:8080/invoices
    public ResponseEntity<FuncResult<TblInvoiceDto>> create(@RequestBody TblInvoiceDto tblInvoiceDto) {
        invoiceService.insert(tblInvoiceDto);

        FuncResult<TblInvoiceDto> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblInvoiceDto
        );

        return ResponseEntity.ok(rs);
    }
    @PutMapping(path = "")
    // [PUT] localhost:8080/invoices
    public ResponseEntity<FuncResult<TblInvoiceEntity>> update(@RequestBody TblInvoiceEntity tblInvoiceEntity) {
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
    public ResponseEntity<FuncResult<Integer>> softDelete(@PathVariable("invoiceID") int invoiceId) {
        invoiceService.softDelete(invoiceId);

        FuncResult<Integer> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.DELETE_SUCCESS, TABLE_NAME, invoiceId),
                invoiceId
        );

        return ResponseEntity.ok(rs);
    }
}