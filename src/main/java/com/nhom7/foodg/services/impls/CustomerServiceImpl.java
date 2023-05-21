package com.nhom7.foodg.services.impls;

import com.nhom7.foodg.exceptions.DuplicateRecordException;
import com.nhom7.foodg.exceptions.ModifyException;
import com.nhom7.foodg.exceptions.NotFoundException;
import com.nhom7.foodg.models.entities.TblCustomerEntity;
import com.nhom7.foodg.repositories.CustomerRepository;
import com.nhom7.foodg.services.CustomerService;
import com.nhom7.foodg.shareds.Constants;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.mindrot.jbcrypt.BCrypt;


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
                    newCustomer.getOtpExp(),
                    newCustomer.getRole()

            );
            customerRepository.save(tblCustomerEntity);

        } catch (DataIntegrityViolationException ex){
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME) + ex.getMessage());
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

}