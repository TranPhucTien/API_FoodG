package com.nhom7.foodg.services.impls;

import com.nhom7.foodg.exceptions.DuplicateRecordException;
import com.nhom7.foodg.exceptions.ModifyException;
import com.nhom7.foodg.exceptions.NotFoundException;
import com.nhom7.foodg.models.dto.TblCustomerDto;
import com.nhom7.foodg.models.entities.TblCustomerEntity;
import com.nhom7.foodg.repositories.CustomerRepository;
import com.nhom7.foodg.services.CustomerService;
import com.nhom7.foodg.shareds.Constants;
import com.nhom7.foodg.utils.DataUtil;
import com.nhom7.foodg.utils.Encode;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import net.bytebuddy.utility.RandomString;


import java.sql.Date;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final String TABLE_NAME = "tbl_customer";





    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<TblCustomerEntity> getAll(){
        return customerRepository.findAll();
    }


    @Override
    public List<TblCustomerEntity> search(String keyword){
        List<TblCustomerEntity> rs = new ArrayList<>();
        List<TblCustomerEntity> customers = customerRepository.findAll();
        for (TblCustomerEntity customer : customers){
            if (customer.getFullName().toLowerCase().contains(keyword.toLowerCase())){
                rs.add(customer);
            }
        }
        return rs;
    }

    @Override
    public void insert(TblCustomerEntity newCustomer){

        //Validate input
        Constants.validateRequiredFields(newCustomer, "username", "password", "fullName");
        Constants.validateStringFields(newCustomer, "UserName 6-20 Ký tự", 6, 20, "username");
        Constants.validateStringFields(newCustomer, "password 8-20 ký tự", 8, 20, "password");
        Constants.validateEmailFields(newCustomer, "email");
        Constants.validateStringFields(newCustomer, "nchar(50)", 0, 50, "email");
        Constants.validateStringFields(newCustomer, "nvarchar(100)", 0, 100, "fullName");
        Constants.validateDateFields(newCustomer, "birthday", "otpExp");
        Constants.validateBooleanFields(newCustomer, "gender", "deleted");
        Constants.validateIntegerFields(newCustomer, "idProvince", "role");


        String customerUsername = newCustomer.getUsername();
        try {
            if (customerRepository.existsByUsername(newCustomer.getUsername())){
                throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR, TABLE_NAME, customerUsername));
            }
            Date currentDate = Constants.getCurrentDay();
            String OTP = DataUtil.generateTempPwd(6);

            String password = Constants.hashPassword(newCustomer.getPassword());

            TblCustomerEntity tblCustomerEntity = TblCustomerEntity.create(
                    0,
                    customerUsername,
                    password,
                    newCustomer.getEmail(),
                    newCustomer.getFullName(),
                    newCustomer.getGender(),
                    newCustomer.getAvatar(),
                    newCustomer.getIdProvince(),
                    currentDate,
                    currentDate,
                    newCustomer.getDeletedAt(),
                    false,
                    newCustomer.getBirthday(),
                    newCustomer.getOtp(),
                    currentDate,
                    newCustomer.getRole()

            );
            customerRepository.save(tblCustomerEntity);

        } catch (DataIntegrityViolationException ex){
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME) + ex.getMessage());
        }
    }

    @Override
    public boolean insert(TblCustomerEntity newCustomer, String otp, String userName){

            //Validate input
            Constants.validateRequiredFields(newCustomer, "username", "password", "fullName");
            Constants.validateStringFields(newCustomer, "UserName 6-20 Ký tự", 6, 20, "username");
            Constants.validateStringFields(newCustomer, "password 8-20 ký tự", 8, 20, "password");
            Constants.validateEmailFields(newCustomer, "email");
            Constants.validateStringFields(newCustomer, "nchar(50)", 0, 50, "email");
            Constants.validateStringFields(newCustomer, "nvarchar(100)", 0, 100, "fullName");
            Constants.validateDateFields(newCustomer, "birthday", "otpExp");
            Constants.validateBooleanFields(newCustomer, "gender");
            Constants.validateIntegerFields(newCustomer, "idProvince", "role");

        if (!customerRepository.existsByUsername(userName) ) {

            String customerUsername = newCustomer.getUsername();
            try {

                Date currentDate = Constants.getCurrentDay();

//            String password = Constants.hashPassword(newCustomer.getPassword());
                Encode encode = new Encode();

                String password = encode.Encrypt(newCustomer.getPassword());
                // Hash Otp khi nào làm xong thì dùng sau
                String Otp = encode.Encrypt(otp);
                TblCustomerEntity tblCustomerEntity = TblCustomerEntity.create(
                        0,
                        customerUsername,
                        password,
                        newCustomer.getEmail(),
                        newCustomer.getFullName(),
                        newCustomer.getGender(),
                        newCustomer.getAvatar(),
                        newCustomer.getIdProvince(),
                        currentDate,
                        currentDate,
                        null,
                        false,
                        newCustomer.getBirthday(),
                        otp,
                        currentDate,
                        newCustomer.getRole()
                );
                customerRepository.save(tblCustomerEntity);
                return true;

            } catch (DataIntegrityViolationException ex) {
                throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME) + ex.getMessage());
            }
        } else {
            return false;
        }
    }
    @Transactional
    @Override
    public void update(TblCustomerEntity tblCustomerEntity){
        if (customerRepository.existsByUsername(tblCustomerEntity.getUsername())){
            throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR, TABLE_NAME, tblCustomerEntity.getUsername()));
        }
        if (!customerRepository.existsById(tblCustomerEntity.getId())) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, tblCustomerEntity.getId()));
        }

        try {
            TblCustomerEntity customer = customerRepository.findById(tblCustomerEntity.getId()).orElse(null);

            if (customer != null) {
                customer.setUsername(tblCustomerEntity.getUsername());
//                customer.setPassword(tblCustomerEntity.getPassword());
                customer.setEmail(tblCustomerEntity.getEmail());
                customer.setFullName(tblCustomerEntity.getFullName());
                customer.setGender(tblCustomerEntity.getGender());
                customer.setAvatar(tblCustomerEntity.getAvatar());
                customer.setIdProvince(tblCustomerEntity.getIdProvince());
                customer.setUpdatedAt(Constants.getCurrentDay());
                customer.setBirthday(tblCustomerEntity.getBirthday());
                customer.setOtp(tblCustomerEntity.getOtp());
                customer.setOtpExp(tblCustomerEntity.getOtpExp());
                customer.setRole(tblCustomerEntity.getRole());

                customerRepository.save(customer);
            }
        } catch (NullPointerException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME) + ex.getMessage());
        }
    }

    public void update(TblCustomerDto tblCustomerDto, String otp){
//        if (customerRepository.existsByUsername(tblCustomerDto.getUsername())){
//            throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR, TABLE_NAME, tblCustomerDto.getUsername()));
//        }
//        if (!customerRepository.existsById(tblCustomerDto.getId())) {
//            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, tblCustomerDto.getId()));
//        }


        try {
            TblCustomerEntity customer = customerRepository.findById(tblCustomerDto.getId()).orElse(null);

            if (customer != null) {
                customer.setUsername(tblCustomerDto.getUsername());
//                customer.setPassword(tblCustomerEntity.getPassword());
                customer.setEmail(tblCustomerDto.getEmail());
                customer.setFullName(tblCustomerDto.getFullName());
                customer.setGender(tblCustomerDto.getGender());
                customer.setAvatar(tblCustomerDto.getAvatar());
                customer.setIdProvince(tblCustomerDto.getIdProvince());
                customer.setUpdatedAt(Constants.getCurrentDay());
                customer.setBirthday(tblCustomerDto.getBirthday());
                customer.setOtp(otp);
                customer.setOtpExp(Constants.getCurrentDay());
                customer.setRole(tblCustomerDto.getRole());

                customerRepository.save(customer);
            }
        } catch (NullPointerException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME) + ex.getMessage());
        }
    }


    @Override
    public void softDelete(int id) {
        if (!customerRepository.existsById(id)) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, id));
        }

        try {
            customerRepository.deleteById(id);
            TblCustomerEntity customer = customerRepository.findById(id).orElse(null);
            if (customer != null) {
                customer.setDeleted(true);
                customer.setDeletedAt(Constants.getCurrentDay());
                customerRepository.save(customer);
            }
        } catch (DataAccessException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME, id) + ex.getMessage());
        }
    }

    public void clearOTP(TblCustomerEntity tblCustomerEntity) {
        tblCustomerEntity.setOtp(null);
        tblCustomerEntity.setOtpExp(null);
        customerRepository.save(tblCustomerEntity);
    }
}