package com.nhom7.foodg.controllers;

import com.nhom7.foodg.exceptions.DuplicateRecordException;
import com.nhom7.foodg.exceptions.NotFoundException;
import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.models.dto.TblAdminDto;
import com.nhom7.foodg.models.dto.TblCustomerDto;
import com.nhom7.foodg.models.dto.TblCustomerDto;
import com.nhom7.foodg.models.entities.TblCustomerEntity;
import com.nhom7.foodg.models.entities.TblCustomerEntity;
import com.nhom7.foodg.models.entities.TblCustomerEntity;
import com.nhom7.foodg.repositories.CustomerRepository;
import com.nhom7.foodg.services.CustomerService;
import com.nhom7.foodg.shareds.Constants;
import com.nhom7.foodg.utils.DataUtil;
import com.nhom7.foodg.utils.Encode;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping(path = "/customers")
//@CrossOrigin(origins = "*")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final String TABLE_NAME = "tbl_customer";

    @Autowired
    public CustomerController(CustomerService customerService, CustomerRepository customerRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    @PostMapping(path = "/loginCustomer")
    // http://localhost:8080/customers/loginCustomer
    public ResponseEntity<FuncResult<TblCustomerDto>> login(@RequestBody TblCustomerDto tblCustomerDto) {
        String email = tblCustomerDto.getEmail().trim();
        String password = tblCustomerDto.getPassword().trim().toString();

        Encode encode = new Encode();

        TblCustomerEntity customer = customerRepository.findFirstByEmail(email);

        if (customer != null) {
            tblCustomerDto.setId(customer.getId());
            tblCustomerDto.setUsername(customer.getUsername());
            tblCustomerDto.setFullName(customer.getFullName());
            tblCustomerDto.setGender(customer.getGender());
            tblCustomerDto.setAvatar(customer.getAvatar());
            tblCustomerDto.setBirthday(customer.getBirthday());

            if (customer.getPassword().equals(encode.Encrypt(password)) && customer.getStatus()) {
                // Đăng nhập thành công
                FuncResult<TblCustomerDto> rs = FuncResult.create(
                        HttpStatus.OK,
                        Constants.LOGIN_SUCCESS,
                        tblCustomerDto
                );
                return ResponseEntity.ok(rs);
            } else {
                // Đăng nhập thất bại
                FuncResult<TblCustomerDto> rs = FuncResult.create(
                        HttpStatus.OK,
                        Constants.LOGIN_FAIL,
                        null
                );
                return ResponseEntity.ok(rs);
            }
        } else {
            // Khong có tài khoản này
            FuncResult<TblCustomerDto> rs = FuncResult.create(
                    HttpStatus.OK,
                    Constants.NOT_FOUND_ACCOUNT,
                    null
            );
            return ResponseEntity.ok(rs);
        }
    }


    @GetMapping(path = "")
    // [GET] localhost:8080/customers
    public ResponseEntity<FuncResult<List<TblCustomerEntity>>> getAll(HttpSession httpSession) {
        if (httpSession.getAttribute("role") == null || !httpSession.getAttribute("role").equals("admin")) {
            FuncResult<List<TblCustomerEntity>> rs = FuncResult.create(
                    HttpStatus.OK,
                    "Ban Khong Phai ADMIN!!!",
                    null
            );
            return ResponseEntity.ok(rs);
        }
        FuncResult<List<TblCustomerEntity>> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.GET_DATA_SUCCESS, TABLE_NAME),
                customerService.getAll()
        );
        return ResponseEntity.ok(rs);
    }

    @GetMapping(path = "/search")
    // [GET] localhost:8080/customers/search?keyword=Nguyen
    public ResponseEntity<FuncResult<List<TblCustomerEntity>>> search(HttpSession httpSession, @RequestParam(name = "keyword", required = false, defaultValue = "") String fullName) {
        if (httpSession.getAttribute("role") == null || !httpSession.getAttribute("role").equals("admin")) {
            FuncResult<List<TblCustomerEntity>> rs = FuncResult.create(
                    HttpStatus.OK,
                    "Ban Khong Phai ADMIN!!!",
                    null
            );
            return ResponseEntity.ok(rs);
        }
        FuncResult<List<TblCustomerEntity>> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.SEARCH_SUCCESS, TABLE_NAME, fullName),
                customerService.search(fullName)
        );
        return ResponseEntity.ok(rs);
    }

    @PostMapping(path = "")
    // [POST] localhost:8080/customers
    public ResponseEntity<FuncResult<TblCustomerEntity>> create(HttpSession httpSession, @RequestBody TblCustomerEntity tblCustomerEntity) {
        customerService.insert(tblCustomerEntity);
        if (httpSession.getAttribute("role") == null || !httpSession.getAttribute("role").equals("admin")) {
            FuncResult<TblCustomerEntity> rs = FuncResult.create(
                    HttpStatus.OK,
                    "Ban Khong Phai ADMIN!!!",
                    null
            );
            return ResponseEntity.ok(rs);
        }
        FuncResult<TblCustomerEntity> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblCustomerEntity
        );
        return ResponseEntity.ok(rs);
    }

    @PutMapping(path = "")
    // [PUT] localhost:8080/customers
    public ResponseEntity<FuncResult<TblCustomerEntity>> update(HttpSession httpSession, @RequestBody TblCustomerEntity tblCustomerEntity) {
        customerService.update(tblCustomerEntity);
        if (httpSession.getAttribute("role") == null || !httpSession.getAttribute("role").equals("admin")) {
            FuncResult<TblCustomerEntity> rs = FuncResult.create(
                    HttpStatus.OK,
                    "Ban Khong Phai ADMIN!!!",
                    null
            );
            return ResponseEntity.ok(rs);
        }
        FuncResult<TblCustomerEntity> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblCustomerEntity
        );
        return ResponseEntity.ok(rs);
    }

    @DeleteMapping(path = "{customerID}")
    // [DELETE] localhost:8080/customers/1
    public ResponseEntity<FuncResult<Integer>> softDelete(HttpSession httpSession, @PathVariable("customerID") int customerID) {
        customerService.softDelete(customerID);
        if (httpSession.getAttribute("role") == null || !httpSession.getAttribute("role").equals("admin")) {
            FuncResult<Integer> rs = FuncResult.create(
                    HttpStatus.OK,
                    "Ban Khong Phai ADMIN!!!",
                    null
            );
            return ResponseEntity.ok(rs);
        }
        FuncResult<Integer> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.DELETE_SUCCESS, TABLE_NAME, customerID),
                customerID
        );
        return ResponseEntity.ok(rs);
    }

    @PostMapping(path = "/register")
    // http://localhost:8080/customers/register
    public ResponseEntity<FuncResult<TblCustomerDto>> create(@RequestBody TblCustomerDto tblCustomerDto) {
        // check có username chưa
        String Otp = DataUtil.generateTempPwd(6);
        String userName = tblCustomerDto.getUsername();
        if (customerRepository.existsByUsername(userName)) {
            /* username đã tồn tại trong DB rồi mà status = false thì update Opt mới */

            TblCustomerEntity customer = customerRepository.findFirstByUsername(userName);
            /* Chỉ gửi lại otp đăng ký khi tài khoản chưa được kích hoạt  */
            if (customer.getStatus() == false) {
                /* Giới hạn thời gian gửi lại OTP */
//                if (customer.isOTPRequired()){
                Encode encode = new Encode();
                String newpass = encode.Encrypt(tblCustomerDto.getPassword());
                customer.setPassword(newpass);
                customer.setBirthday(tblCustomerDto.getBirthday());
                customer.setGender(tblCustomerDto.getGender());
                customer.setRole(tblCustomerDto.getRole());
                customer.setEmail(tblCustomerDto.getEmail());
                customer.setFullName(tblCustomerDto.getFullName());
                customer.setOtp(Otp);
                customer.setOtpExp(Constants.getCurrentDay());
                customerRepository.save(customer);
                customerService.create(customer);
                FuncResult<TblCustomerDto> rs = FuncResult.create(
                        HttpStatus.OK,
                        MessageFormat.format(Constants.RESEND_EMAIL_SUCCESS, customer.getEmail()),
                        null
                );
                return ResponseEntity.ok(rs);
//                }else {
//                    FuncResult<TblCustomerDto> rs = FuncResult.create(
//                            HttpStatus.BAD_REQUEST,
//                            Constants.WAITING_TIME,
//                            null
//                    );
//                    return ResponseEntity.badRequest().body(rs);
//                }
            } else {
                FuncResult<TblCustomerDto> rs = FuncResult.create(
                        HttpStatus.OK,
                        MessageFormat.format(Constants.DUPLICATE_ERROR_USERNAME, customer.getUsername()),
                        null
                );
                return ResponseEntity.ok(rs);
            }

        } else {
            /*Chưa tồn tại trong DB thì tạo mới */
            TblCustomerEntity tblCustomerEntity = TblCustomerEntity.create(
                    0,
                    tblCustomerDto.getUsername(),
                    tblCustomerDto.getPassword(),
                    tblCustomerDto.getEmail(),
                    tblCustomerDto.getFullName(),
                    tblCustomerDto.getGender(),
                    tblCustomerDto.getAvatar(),
                    tblCustomerDto.getIdProvince(),
                    Constants.getCurrentDay(),
                    null,
                    null,
                    false,
                    tblCustomerDto.getBirthday(),
                    Otp,
                    Constants.getCurrentDay(),
                    2,
                    false
            );
            customerService.insert(tblCustomerEntity);
            customerService.create(tblCustomerEntity);
        }
        FuncResult<TblCustomerDto> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblCustomerDto
        );
        return ResponseEntity.ok(rs);
    }

    @PutMapping(path = "/checkotp")
    // http://localhost:8080/customers/checkotp?otp=263157
    public ResponseEntity<FuncResult<TblCustomerDto>> check(@RequestBody TblCustomerEntity tblCustomerEntity,
                                                            @RequestParam(name = "otp", required = false, defaultValue = "") String otpInput) {
        if (tblCustomerEntity.getUsername() != null) {
            try {
                TblCustomerEntity customer = customerRepository.findFirstByUsername(tblCustomerEntity.getUsername());
                /* Kiểm tra xem OTP còn trong thời gian sử dụng không 60s kể từ lúc tạo */
                if (!customer.isOTPRequired()) {
                    if (customer.getOtp().equals(otpInput)) {
                        /* Otp nhập vào đúng */
                        customer.setStatus(true);
                        customer.setOtp(null);
                        customer.setOtpExp(null);

                        customerRepository.save(customer);

                        FuncResult<TblCustomerDto> rs = FuncResult.create(
                                HttpStatus.OK,
                                MessageFormat.format(Constants.OTP_SUCCESS, customer.getUsername()),
                                null
                        );
                        return ResponseEntity.ok(rs);
                    } else {
                        FuncResult<TblCustomerDto> rs = FuncResult.create(
                                HttpStatus.BAD_REQUEST,
                                MessageFormat.format(Constants.OTP_FAIL, "Email bạn nhận được"),
                                null
                        );
                        return ResponseEntity.badRequest().body(rs);
                    }
                } else {
                    FuncResult<TblCustomerDto> rs = FuncResult.create(
                            HttpStatus.BAD_REQUEST,
                            Constants.EXPIRED_OTP,
                            null
                    );
                    return ResponseEntity.ok(rs);
                }
            } catch (Exception ex) {
                throw new NotFoundException(Constants.NOT_FOUND_FIELDS);
            }


        }
        if (tblCustomerEntity.getEmail() != null && tblCustomerEntity.getPassword() != null) {
            // Valid input
            Constants.validateEmailFields(tblCustomerEntity, "email");
            Constants.validateStringFields(tblCustomerEntity, "password", 8, 20, "password");

            try {
                TblCustomerEntity customer = customerRepository.findFirstByEmail(tblCustomerEntity.getEmail());
                /* Kiểm tra xem OTP còn hạn sử dụng không */
                if (!customer.isOTPRequired()) {
                    if (customer.getOtp().equals(otpInput)) {
                        /* Otp nhập vào đúng */
                        Encode encode = new Encode();
                        customer.setPassword(encode.Encrypt(tblCustomerEntity.getPassword()));
                        customer.setOtp(null);
                        customer.setOtpExp(null);
                        customerRepository.save(customer);

                        FuncResult<TblCustomerDto> rs = FuncResult.create(
                                HttpStatus.OK,
                                MessageFormat.format(Constants.OTP_SUCCESS, customer.getUsername()),
                                null
                        );
                        return ResponseEntity.ok(rs);
                    } else {
                        FuncResult<TblCustomerDto> rs = FuncResult.create(
                                HttpStatus.BAD_REQUEST,
                                MessageFormat.format(Constants.OTP_FAIL, "Email bạn nhận được"),
                                null
                        );
                        return ResponseEntity.ok(rs);
                    }
                } else {
                    FuncResult<TblCustomerDto> rs = FuncResult.create(
                            HttpStatus.BAD_REQUEST,
                            Constants.EXPIRED_OTP,
                            null
                    );
                    return ResponseEntity.ok(rs);
                }
            } catch (Exception ex) {
                throw new NotFoundException(Constants.NOT_FOUND_FIELDS);
            }
        }
        FuncResult<TblCustomerDto> rs = FuncResult.create(
                HttpStatus.BAD_REQUEST,
                "Không nhận được dữ liệu",
                null
        );
        return ResponseEntity.badRequest().body(rs);

    }

    @PatchMapping(path = "/forgetPassword")
    // http://localhost:8080/customers/forgetPassword
    public ResponseEntity<FuncResult<TblCustomerDto>> forgetPassword(@RequestBody TblCustomerEntity tblCustomerEntity) {
        // valid input
        Constants.validateRequiredFields(tblCustomerEntity, "email");
        Constants.validateEmailFields(tblCustomerEntity, "email");

        TblCustomerEntity customer = customerRepository.findFirstByEmail(tblCustomerEntity.getEmail());
        String Otp = DataUtil.generateTempPwd(6);

        if (customerRepository.existsByEmail(customer.getEmail())) {
            /* Giới hạn việc gửi lại OTP */
//            if (customer.isOTPRequired()) {
            customer.setOtp(Otp);
            customer.setOtpExp(Constants.getCurrentDay());
            customerService.create(customer);
            customerRepository.save(customer);
//            } else {
//                FuncResult<TblCustomerDto> rs = FuncResult.create(
//                        HttpStatus.BAD_REQUEST,
//                        Constants.WAITING_TIME,
//                        null
//                );
//                return ResponseEntity.badRequest().body(rs);
//            }
            FuncResult<TblCustomerDto> rs = FuncResult.create(
                    HttpStatus.OK,
                    MessageFormat.format(Constants.SEND_EMAIL_SUCCESS, customer.getEmail()),
                    null
            );
            return ResponseEntity.ok(rs);
        } else {
            FuncResult<TblCustomerDto> rs = FuncResult.create(
                    HttpStatus.BAD_REQUEST,
                    MessageFormat.format(Constants.DUPLICATE_ERROR_EMAIL, customer.getEmail()),
                    null
            );
            return ResponseEntity.ok(rs);
        }
    }


}