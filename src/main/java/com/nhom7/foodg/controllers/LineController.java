package com.nhom7.foodg.controllers;

import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.models.dto.TblLineDto;
import com.nhom7.foodg.models.entities.TblLineEntity;
import com.nhom7.foodg.services.LineService;
import com.nhom7.foodg.shareds.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping(path = "/lines")
@CrossOrigin(origins = "*")

public class LineController {
    private final LineService lineService;
    private final String TABLE_NAME = "tbl_line";

    @Autowired

    public LineController(LineService lineService) {
        this.lineService = lineService;
    }

    // Get all lines
    @GetMapping(path = "")
    // [GET] localhost:8080/lines
    public ResponseEntity<FuncResult<List<TblLineEntity>>> getAll() {
        FuncResult<List<TblLineEntity>> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.GET_DATA_SUCCESS, TABLE_NAME),
                lineService.getAll()
        );

        return ResponseEntity.ok(rs);
    }
    // create new line
    @PostMapping(path = "")
    // [POST] localhost:8080/lines
    public ResponseEntity<FuncResult<TblLineDto>> create(@RequestBody TblLineDto tblLineDto) {
        lineService.insert(tblLineDto);

        FuncResult<TblLineDto> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblLineDto
        );

        return ResponseEntity.ok(rs);
    }

    @PutMapping(path = "")
    // [PUT] localhost:8080/lines
    public ResponseEntity<FuncResult<TblLineEntity>> update(@RequestBody TblLineEntity tblLineEntity) {
        lineService.update(tblLineEntity);

        FuncResult<TblLineEntity> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblLineEntity
        );

        return ResponseEntity.ok(rs);
    }


    // solf delete line by line id
    @DeleteMapping(path = "{lineID}")
    // [DELETE] localhost:8080/lines
    public ResponseEntity<FuncResult<Integer>> softDelete(@PathVariable("lineID") int lineId) {
        lineService.softDelete(lineId);

        FuncResult<Integer> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.DELETE_SUCCESS, TABLE_NAME, lineId),
                lineId
        );

        return ResponseEntity.ok(rs);
    }
}