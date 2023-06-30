package com.nhom7.foodg.controllers;

import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.models.dto.TblDiscountDto;
import com.nhom7.foodg.models.dto.TblInvoiceOutDto;
import com.nhom7.foodg.models.dto.TblLineDto;
import com.nhom7.foodg.models.entities.TblLineEntity;
import com.nhom7.foodg.models.entities.TblProductEntity;
import com.nhom7.foodg.services.LineService;
import com.nhom7.foodg.shareds.Constants;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping(path = "/lines")
//@CrossOrigin(origins = "*")

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
    public ResponseEntity<FuncResult<List<TblLineEntity>>> getAll(HttpSession httpSession) {
        if(httpSession.getAttribute("role") == null || !httpSession.getAttribute("role").equals("admin")){
            FuncResult<List<TblLineEntity>> rs = FuncResult.create(
                    HttpStatus.OK,
                    "Ban Khong Phai ADMIN!!!",
                    null
            );
            return  ResponseEntity.ok(rs);
        }
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
    public ResponseEntity<FuncResult<TblLineDto>> create(HttpSession httpSession, @RequestBody TblLineDto tblLineDto) {
        if(httpSession.getAttribute("role") == null){
            FuncResult<TblLineDto> rs = FuncResult.create(
                    HttpStatus.OK,
                    "Ban Khong Phai ADMIN!!!",
                    null
            );
            return  ResponseEntity.ok(rs);
        }
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
    public ResponseEntity<FuncResult<TblLineEntity>> update(HttpSession httpSession, @RequestBody TblLineEntity tblLineEntity) {
        if(httpSession.getAttribute("role") == null || !httpSession.getAttribute("role").equals("admin")){
            FuncResult<TblLineEntity> rs = FuncResult.create(
                    HttpStatus.OK,
                    "Ban Khong Phai ADMIN!!!",
                    null
            );
            return  ResponseEntity.ok(rs);
        }
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
    public ResponseEntity<FuncResult<Integer>> softDelete(HttpSession httpSession, @PathVariable("lineID") int lineId) {
        if(httpSession.getAttribute("role") == null || !httpSession.getAttribute("role").equals("admin")){
            FuncResult<Integer> rs = FuncResult.create(
                    HttpStatus.OK,
                    "Ban Khong Phai ADMIN!!!",
                    null
            );
            return  ResponseEntity.ok(rs);
        }
        lineService.softDelete(lineId);

        FuncResult<Integer> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.DELETE_SUCCESS, TABLE_NAME, lineId),
                lineId
        );

        return ResponseEntity.ok(rs);
    }
//    List<TblLineEntity> lineEntities=lineRepository.GetLineStatusActive();
//    Decimal total =0
//    lineEntities.get().forEach(p->{
//        TblProductEntity foundProduct=productRepository.getByID(idproduct);
//        total+=p.getquatity * foundProduct.getPrice();
//    })
//       tsetTatal(total)
//     invoice.setCreateAt(Constain.getCurren)


//    public class TblLineOutDto {
//        private String idProduct;
//        private int quantity;
//        private TblDiscountDto discountDto;

        //them vao gio hang cac Linesdto(tu giao dien gio hang)
    //lineDto:  du lieu gio hang cung cap:id sp, so luong, gia

    // Insertline. Mình từ Dto để tạo lineEntity

    //Tạo các dòng line
    //Tạo ra giỏ hàng chứa các dòng Line(dto) trạng thái PENDING

    ////thanh toan tao hoa don

    //Viết Chức năng chuyển trạng thái PENDING sang ACTIVE và Viết View
    //Kết hợp câu lệnh getLineStatusActive de tao listLine
    //Dùng forEach để tính tiền + cập nhật số lưng
    //Set time và người làm phiếu


    //quy trinh: tao invoice, lay id_invoice tao line
    //
}
