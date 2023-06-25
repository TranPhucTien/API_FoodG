package com.nhom7.foodg.controllers;

import com.nhom7.foodg.exceptions.DuplicateRecordException;
import com.nhom7.foodg.exceptions.NotFoundException;
import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.models.dto.TblAdminDto;
import com.nhom7.foodg.models.dto.TblAdminDto;
import com.nhom7.foodg.models.entities.TblAdminEntity;
import com.nhom7.foodg.models.entities.TblAdminEntity;
import com.nhom7.foodg.repositories.AdminRepository;
import com.nhom7.foodg.services.AdminService;
import com.nhom7.foodg.shareds.Constants;
import com.nhom7.foodg.utils.DataUtil;
import com.nhom7.foodg.utils.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping(path = "/admins")
@CrossOrigin(origins = "*")
public class AdminController {
    private final AdminService adminService;
    private final AdminRepository adminRepository;
    private final String TABLE_NAME = "tbl_admin";

    @Autowired
    public AdminController(AdminService adminService, AdminRepository adminRepository) {
        this.adminService = adminService;
        this.adminRepository = adminRepository;
    }

    @PostMapping(path = "/loginAdmin")
    // http://localhost:8080/admins/loginAdmin
    public ResponseEntity<FuncResult<TblAdminEntity>> login(@RequestBody TblAdminEntity tblAdminEntity){
        String username = tblAdminEntity.getUsername().trim();
        String password = tblAdminEntity.getPassword().trim().toString();

        // Valid input
        Constants.validateRequiredFields(tblAdminEntity, "username", "password");
        Constants.validateStringFields(tblAdminEntity, "password", 8, 20, "password");
        Encode encode = new Encode();

        TblAdminEntity admin = adminRepository.findFirstByUsername(username);
        if (admin !=null){
            if (admin.getPassword().equals(encode.Encrypt(password)) && admin.getStatus() == true){
                // Đăng nhập thành công
                FuncResult<TblAdminEntity> rs = FuncResult.create(
                        HttpStatus.OK,
                        Constants.LOGIN_SUCCESS,
                        null
                );
                return ResponseEntity.ok(rs);
            }
        }
        // Đăng nhập thất bại
        FuncResult<TblAdminEntity> rs = FuncResult.create(
                HttpStatus.BAD_REQUEST,
                Constants.LOGIN_FAIL,
                null
        );
        return ResponseEntity.badRequest().body(rs);
    }

    @PostMapping(path = "/register")
    // http://localhost:8080/admins/register
    public ResponseEntity<FuncResult<TblAdminDto>> create(@RequestBody TblAdminDto tblAdminDto){
        // check có username chưa
        String Otp = DataUtil.generateTempPwd(6);
        String userName = tblAdminDto.getUsername().trim();
        if (adminRepository.existsByUsername(userName)){
            /* username đã tồn tại trong DB rồi thì update Opt mới */

            TblAdminEntity admin = adminRepository.findFirstByUsername(userName);
            /* Chỉ gửi lại otp đăng ký khi tài khoản chưa được kích hoạt  */
            if (admin.getStatus() == false){
                /* Giới hạn thời gian gửi lại OTP */
                if (admin.isOTPRequired()){
                    admin.setOtp(Otp);
                    admin.setOtpExp(Constants.getCurrentDay());
                    adminRepository.save(admin);
                    adminService.create(admin);
                    FuncResult<TblAdminDto> rs = FuncResult.create(
                            HttpStatus.OK,
                            MessageFormat.format(Constants.RESEND_EMAIL_SUCCESS, admin.getEmail()),
                            null
                    );
                    return ResponseEntity.ok(rs);

                }else {
                    FuncResult<TblAdminDto> rs = FuncResult.create(
                            HttpStatus.BAD_REQUEST,
                            Constants.WAITING_TIME,
                            null
                    );
                    return ResponseEntity.badRequest().body(rs);
                }
            } else {
                FuncResult<TblAdminDto> rs = FuncResult.create(
                        HttpStatus.BAD_REQUEST,
                        MessageFormat.format(Constants.DUPLICATE_ERROR_USERNAME, admin.getUsername()),
                        null
                );
                return ResponseEntity.badRequest().body(rs);
            }
        } else {
            /*Chưa tồn tại trong DB thì tạo mới */
            TblAdminEntity tblAdminEntity = TblAdminEntity.create(
                    0,
                    tblAdminDto.getUsername(),
                    tblAdminDto.getPassword(),
                    tblAdminDto.getEmail(),
                    tblAdminDto.getFullName(),
                    tblAdminDto.getGender(),
                    tblAdminDto.getAvatar(),
                    tblAdminDto.getIdProvince(),
                    null,
                    null,
                    null,
                    false,
                    tblAdminDto.getBirthday(),
                    tblAdminDto.getRole(),
                    Otp,
                    Constants.getCurrentDay(),
                    false
            );
            adminService.insert(tblAdminEntity);
            adminService.create(tblAdminEntity);
        }
        FuncResult<TblAdminDto> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblAdminDto
        );
        return ResponseEntity.ok(rs);
    }

    @PutMapping(path = "/checkotp")
    // http://localhost:8080/admins/checkotp?otp=263157
    public ResponseEntity<FuncResult<TblAdminDto>> check(@RequestBody TblAdminEntity tblAdminEntity,
                                                            @RequestParam(name = "otp", required = false, defaultValue = "") String otpInput){
        if (tblAdminEntity.getUsername() != null) {
            try{
                TblAdminEntity admin = adminRepository.findFirstByUsername(tblAdminEntity.getUsername());
                /* Kiểm tra xem OTP còn trong thời gian sử dụng không 60s kể từ lúc tạo */
                if (!admin.isOTPRequired()){
                    if (admin.getOtp().equals(otpInput)) {
                        /* Otp nhập vào đúng */
                        admin.setStatus(true);
                        admin.setOtp(null);
                        admin.setOtpExp(null);
                        adminRepository.save(admin);

                        FuncResult<TblAdminDto> rs = FuncResult.create(
                                HttpStatus.OK,
                                MessageFormat.format(Constants.OTP_SUCCESS, admin.getUsername()),
                                null
                        );
                        return ResponseEntity.ok(rs);
                    } else {
                        FuncResult<TblAdminDto> rs = FuncResult.create(
                                HttpStatus.BAD_REQUEST,
                                MessageFormat.format(Constants.OTP_FAIL, admin.getUsername()),
                                null
                        );
                        return ResponseEntity.ok(rs);
                    }
                } else {
                    FuncResult<TblAdminDto> rs = FuncResult.create(
                            HttpStatus.BAD_REQUEST,
                            Constants.EXPIRED_OTP,
                            null
                    );
                    return ResponseEntity.badRequest().body(rs);
                }
            } catch (Exception ex){
                throw new NotFoundException(Constants.NOT_FOUND_FIELDS);
            }
        }
        else {
            // Valid input
            Constants.validateRequiredFields(tblAdminEntity, "email", "password");
            Constants.validateEmailFields(tblAdminEntity, "email");
            Constants.validateStringFields(tblAdminEntity, "password", 8, 20, "password");

            try {
                TblAdminEntity admin = adminRepository.findFirstByEmail(tblAdminEntity.getEmail());
                /* Kiểm tra xem OTP còn hạn sử dụng không */
                if (!admin.isOTPRequired()){
                    if (admin.getOtp().equals(otpInput)) {
                        /* Otp nhập vào đúng */
                        Encode encode = new Encode();
                        admin.setPassword(encode.Encrypt(tblAdminEntity.getPassword()));
                        admin.setOtp(null);
                        admin.setOtpExp(null);
                        adminRepository.save(admin);

                        FuncResult<TblAdminDto> rs = FuncResult.create(
                                HttpStatus.OK,
                                MessageFormat.format(Constants.OTP_SUCCESS, admin.getUsername()),
                                null
                        );
                        return ResponseEntity.ok(rs);
                    } else {
                        FuncResult<TblAdminDto> rs = FuncResult.create(
                                HttpStatus.BAD_REQUEST,
                                MessageFormat.format(Constants.OTP_FAIL, "Email bạn nhận được"),
                                null
                        );
                        return ResponseEntity.ok(rs);
                    }
                }else {
                    FuncResult<TblAdminDto> rs = FuncResult.create(
                            HttpStatus.BAD_REQUEST,
                            Constants.EXPIRED_OTP,
                            null
                    );
                    return ResponseEntity.badRequest().body(rs);
                }
            } catch (Exception ex){
                throw new NotFoundException(Constants.NOT_FOUND_FIELDS);
            }
        }
    }

    @PatchMapping (path = "/forgetPassword")
    // http://localhost:8080/admins/forgetPassword
    public ResponseEntity<FuncResult<TblAdminDto>> forgetPassword(@RequestBody TblAdminEntity tblAdminEntity) {
        // Valid input
        Constants.validateRequiredFields(tblAdminEntity, "email");
        Constants.validateEmailFields(tblAdminEntity, "email");

        TblAdminEntity admin = adminRepository.findFirstByEmail(tblAdminEntity.getEmail());
        String Otp = DataUtil.generateTempPwd(6);

        if (adminRepository.existsByEmail(admin.getEmail())){
            /* Giới hạn việc gửi lại OTP */
            if (admin.isOTPRequired()) {
                admin.setOtp(Otp);
                admin.setOtpExp(Constants.getCurrentDay());
                adminService.create(admin);
                adminRepository.save(admin);
            } else {
                FuncResult<TblAdminDto> rs = FuncResult.create(
                        HttpStatus.BAD_REQUEST,
                        Constants.WAITING_TIME,
                        null
                );
                return ResponseEntity.badRequest().body(rs);
            }
            FuncResult<TblAdminDto> rs = FuncResult.create(
                    HttpStatus.OK,
                    MessageFormat.format(Constants.SEND_EMAIL_SUCCESS, admin.getEmail()),
                    null
            );
            return ResponseEntity.ok(rs);
        } else {
            throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR_EMAIL, TABLE_NAME, admin.getEmail()));
        }
    }

    @GetMapping(path = "")
    // [GET] localhost:8080/admins
    public ResponseEntity<FuncResult<List<TblAdminEntity>>> getAll() {
        FuncResult<List<TblAdminEntity>> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.GET_DATA_SUCCESS, TABLE_NAME),
                adminService.getAll()
        );
        return ResponseEntity.ok(rs);
    }

    @GetMapping(path = "/search")
    // [GET] localhost:8080/admins/search?keyword=Nguyen
    public ResponseEntity<FuncResult<List<TblAdminEntity>>> search(@RequestParam(name = "keyword", required = false, defaultValue = "") String fullName){
        FuncResult<List<TblAdminEntity>> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.SEARCH_SUCCESS, TABLE_NAME, fullName),
                adminService.search(fullName)
        );
        return  ResponseEntity.ok(rs);
    }


    @PostMapping(path = "")
    // [POST] localhost:8080/admins
    public ResponseEntity<FuncResult<TblAdminEntity>> create(@RequestBody TblAdminEntity tblAdminEntity){
        adminService.insert(tblAdminEntity);

        FuncResult<TblAdminEntity> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblAdminEntity
        );
        return ResponseEntity.ok(rs);
    }


    @PutMapping(path = "")
    // [PUT] localhost:8080/admins
    public ResponseEntity<FuncResult<TblAdminEntity>> update(@RequestBody TblAdminEntity tblAdminEntity){
        adminService.update(tblAdminEntity);

        FuncResult<TblAdminEntity> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblAdminEntity
        );
        return ResponseEntity.ok(rs);
    }

    @DeleteMapping(path = "{adminID}")
    // [DELETE] localhost:8080/admins/1
    public ResponseEntity<FuncResult<Integer>> softDelete(@PathVariable("adminID") int adminID){
        adminService.softDelete(adminID);
        FuncResult<Integer> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.DELETE_SUCCESS, TABLE_NAME, adminID),
                adminID
        );
        return ResponseEntity.ok(rs);
    }


}