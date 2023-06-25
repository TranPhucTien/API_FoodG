package com.nhom7.foodg.services.impls;

import com.nhom7.foodg.exceptions.DuplicateRecordException;
import com.nhom7.foodg.exceptions.ModifyException;
import com.nhom7.foodg.exceptions.NotFoundException;
import com.nhom7.foodg.models.dto.DataMailDto;
import com.nhom7.foodg.models.entities.TblCustomerEntity;
import com.nhom7.foodg.repositories.CustomerRepository;
import com.nhom7.foodg.services.CustomerService;
import com.nhom7.foodg.services.MailService;
import com.nhom7.foodg.shareds.Constants;
import com.nhom7.foodg.utils.Encode;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;


import java.util.*;
import java.text.MessageFormat;

@Component
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final MailService mailService;
    private final String TABLE_NAME = "tbl_customer";


    public CustomerServiceImpl(CustomerRepository customerRepository, MailService mailService) {
        this.customerRepository = customerRepository;
        this.mailService = mailService;
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
        Constants.validateRequiredFields(newCustomer, "username", "password", "fullName", "email");
        Constants.validateStringFields(newCustomer, "UserName 6-20 Ký tự", 6, 20, "username");
        Constants.validateStringFields(newCustomer, "password 8-20 ký tự", 8, 20, "password");
        Constants.validateEmailFields(newCustomer, "email");
        Constants.validateStringFields(newCustomer, "nchar(50)", 0, 50, "email");
        Constants.validateStringFields(newCustomer, "nvarchar(100)", 0, 100, "fullName");
        Constants.validateIntegerFields(newCustomer, "idProvince", "role");



        String customerUsername = newCustomer.getUsername();
        try {
            if (customerRepository.existsByUsername(newCustomer.getUsername())){
                throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR, TABLE_NAME, customerUsername));
            }
//            if (customerRepository.existsByEmail(newCustomer.getEmail())){
//                throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR_EMAIL, TABLE_NAME, newCustomer.getEmail()));
//            }
            Date currentDate = Constants.getCurrentDay();
            Encode encode = new Encode();
            String password = encode.Encrypt(newCustomer.getPassword());
            TblCustomerEntity tblAdminEntity = TblCustomerEntity.create(
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
                    newCustomer.getRole(),
                    newCustomer.getStatus()
            );
            customerRepository.save(tblAdminEntity);

        } catch (DataIntegrityViolationException ex){
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME) + ex.getMessage());
        }
    }


    @Transactional
    @Override
    public void update(TblCustomerEntity tblAdminEntity){
        if (!customerRepository.existsById(tblAdminEntity.getId())) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, tblAdminEntity.getId()));
        }

        TblCustomerEntity customer = customerRepository.findById(tblAdminEntity.getId()).orElse(null);
        if (customerRepository.existsByUsername(tblAdminEntity.getUsername())){
            if (customer.getUsername().trim() == tblAdminEntity.getUsername().trim()){
                throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR, TABLE_NAME, tblAdminEntity.getUsername()));
            }
        }
        try {

            if (customer != null) {
                customer.setUsername(tblAdminEntity.getUsername());
//                customer.setPassword(tblAdminEntity.getPassword());
                customer.setEmail(tblAdminEntity.getEmail());
                customer.setFullName(tblAdminEntity.getFullName());
                customer.setGender(tblAdminEntity.getGender());
                customer.setAvatar(tblAdminEntity.getAvatar());
                customer.setIdProvince(tblAdminEntity.getIdProvince());
                customer.setUpdatedAt(Constants.getCurrentDay());
                customer.setBirthday(tblAdminEntity.getBirthday());
                customer.setOtp(tblAdminEntity.getOtp());
                customer.setOtpExp(tblAdminEntity.getOtpExp());
                customer.setRole(tblAdminEntity.getRole());
                customer.setStatus(tblAdminEntity.getStatus());

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

    @Override
    public Boolean create(TblCustomerEntity tblCustomerEntity) {
        try {
            DataMailDto dataMail = new DataMailDto();

            dataMail.setTo(tblCustomerEntity.getEmail());
            dataMail.setSubject(Constants.SEND_MAIL_SUBJECT.CLIENT_REGISTER);

            Map<String, Object> props = new HashMap<>();
            props.put("name", tblCustomerEntity.getFullName());
            props.put("username", tblCustomerEntity.getUsername());
            props.put("password", tblCustomerEntity.getPassword());
            props.put("OTP", tblCustomerEntity.getOtp());
            dataMail.setProps(props);

            mailService.sendHtmlMail(dataMail, Constants.TEMPLATE_FILE_NAME.CLIENT_REGISTER);
            return true;
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        return false;
    }





}