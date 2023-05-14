package com.nhom7.foodg.controllers;

import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.services.PaginationService;
import com.nhom7.foodg.shareds.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.Map;

@RestController
@RequestMapping(path = "/pagination")
@CrossOrigin(origins = "*")
//localhost:8080/pagination
public class PaginationController {
    private final PaginationService paginationService;

    public PaginationController(PaginationService paginationService) {
        this.paginationService = paginationService;
    }

    // get count product each category
    @GetMapping(path = "")
    // [GET] localhost:8080/pagination
    public ResponseEntity<FuncResult<Map<String, Long>>> getCountProductEachCategory() {
        FuncResult<Map<String, Long>> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.GET_DATA_SUCCESS, "pagination"),
                paginationService.getCountProductEachCategory()
        );

        return ResponseEntity.ok(rs);
    }
}
