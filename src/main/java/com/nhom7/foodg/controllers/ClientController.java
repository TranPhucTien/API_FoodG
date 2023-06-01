package com.nhom7.foodg.controllers;

import com.nhom7.foodg.exceptions.DuplicateRecordException;
import com.nhom7.foodg.exceptions.NotFoundException;
import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.models.dto.TblCustomerDto;
import com.nhom7.foodg.models.entities.TblCustomerEntity;
import com.nhom7.foodg.repositories.CustomerRepository;
import com.nhom7.foodg.services.ClientService;
import com.nhom7.foodg.services.CustomerService;
import com.nhom7.foodg.shareds.Constants;
import com.nhom7.foodg.utils.DataUtil;
import com.nhom7.foodg.utils.Encode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

@RestController
@RequestMapping("/client")
//@CrossOrigin(origins = "*")
public class ClientController {
    private ClientService clientService;
    private final CustomerService customerService;

    private final CustomerRepository customerRepository;
    private final String TABLE_NAME = "tbl_customer";

    public ClientController(CustomerService customerService, CustomerRepository customerRepository, ClientService clientService) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
        this.clientService = clientService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<FuncResult<TblCustomerDto>> create(@RequestBody TblCustomerDto tblCustomerDto){
        // check có username chưa
        String Otp = DataUtil.generateTempPwd(6);
        String userName = tblCustomerDto.getUsername();
        if (customerRepository.existsByUsername(userName)){
            /* username đã tồn tại trong DB rồi thì update Opt mới */

            TblCustomerEntity customer = customerRepository.findFirstByUsername(userName);
            /* Giới hạn thời gian gửi lại OTP */
            if (customer.isOTPRequired()){
                customer.setOtp(Otp);
                customer.setOtpExp(Constants.getCurrentDay());
                customerRepository.save(customer);
                clientService.create(customer);
            }else {
                FuncResult<TblCustomerDto> rs = FuncResult.create(
                        HttpStatus.OK,
                        Constants.WAITING_TIME,
                        null
                );
                return ResponseEntity.badRequest().body(rs);
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
                    null,
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
            clientService.create(tblCustomerEntity);
        }
        FuncResult<TblCustomerDto> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.MODIFY_DATA_SUCCESS, TABLE_NAME),
                tblCustomerDto
        );
        return ResponseEntity.ok(rs);
    }

    @PutMapping(path = "/checkotp")
    // http://localhost:8080/client/checkotp?otp=263157
    public ResponseEntity<FuncResult<TblCustomerDto>> check(@RequestBody TblCustomerEntity tblCustomerEntity,
                                                            @RequestParam(name = "otp", required = false, defaultValue = "") String otpInput){
        if (tblCustomerEntity.getUsername() != null) {
            try{
                TblCustomerEntity customer = customerRepository.findFirstByUsername(tblCustomerEntity.getUsername());
                /* Kiểm tra xem OTP còn trong thời gian sử dụng không 60s kể từ lúc tạo */
                if (!customer.isOTPRequired()){
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
                                HttpStatus.OK,
                                MessageFormat.format(Constants.OTP_FAIL, "Email bạn nhận được"),
                                null
                        );
                        return ResponseEntity.ok(rs);
                    }
                } else {
                    FuncResult<TblCustomerDto> rs = FuncResult.create(
                            HttpStatus.OK,
                            Constants.EXPIRED_OTP,
                            null
                    );
                    return ResponseEntity.badRequest().body(rs);
                }
            } catch (Exception ex){
                throw new NotFoundException(Constants.NOT_FOUND_FIELDS);
            }



        }
        if (tblCustomerEntity.getEmail() != null && tblCustomerEntity.getPassword() != null){
            try {
                TblCustomerEntity customer = customerRepository.findFirstByEmail(tblCustomerEntity.getEmail());
                /* Kiểm tra xem OTP còn hạn sử dụng không */
                if (!customer.isOTPRequired()){
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
                                HttpStatus.OK,
                                MessageFormat.format(Constants.OTP_FAIL, "Email bạn nhận được"),
                                null
                        );
                        return ResponseEntity.ok(rs);
                    }
                }else {
                    FuncResult<TblCustomerDto> rs = FuncResult.create(
                            HttpStatus.OK,
                            Constants.EXPIRED_OTP,
                            null
                    );
                    return ResponseEntity.badRequest().body(rs);
                }
            } catch (Exception ex){
                throw new NotFoundException(Constants.NOT_FOUND_FIELDS);
            }
        }
        FuncResult<TblCustomerDto> rs = FuncResult.create(
                HttpStatus.OK,
                "Không nhận được dữ liệu",
                null
        );
        return ResponseEntity.badRequest().body(rs);

    }

    @PatchMapping (path = "/forgetPassword")
    public ResponseEntity<FuncResult<TblCustomerDto>> forgetPassword(@RequestBody TblCustomerEntity tblCustomerEntity) {
        TblCustomerEntity customer = customerRepository.findFirstByEmail(tblCustomerEntity.getEmail());
        String Otp = DataUtil.generateTempPwd(6);

        if (customerRepository.existsByEmail(customer.getEmail())){
                /* Giới hạn việc gửi lại OTP */
                if (customer.isOTPRequired()) {
                    customer.setOtp(Otp);
                    customer.setOtpExp(Constants.getCurrentDay());
                    clientService.create(customer);
                    customerRepository.save(customer);
                } else {
                    FuncResult<TblCustomerDto> rs = FuncResult.create(
                            HttpStatus.OK,
                            Constants.WAITING_TIME,
                            null
                    );
                    return ResponseEntity.badRequest().body(rs);
                }
            FuncResult<TblCustomerDto> rs = FuncResult.create(
                    HttpStatus.OK,
                    MessageFormat.format(Constants.SEND_EMAIL_SUCCESS, customer.getEmail()),
                    null
            );
            return ResponseEntity.ok(rs);
        } else {
            throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR_EMAIL, TABLE_NAME, customer.getEmail()));
        }
    }

}
