package com.nhom7.foodg.services.impls;

import com.nhom7.foodg.exceptions.DuplicateRecordException;
import com.nhom7.foodg.exceptions.ModifyException;
import com.nhom7.foodg.exceptions.NotFoundException;
import com.nhom7.foodg.models.dto.DataMailDto;
import com.nhom7.foodg.models.entities.TblAdminEntity;
import com.nhom7.foodg.repositories.AdminRepository;
import com.nhom7.foodg.services.AdminService;
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
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final MailService mailService;

    private final String TABLE_NAME = "tbl_admin";
    public AdminServiceImpl(AdminRepository adminRepository, MailService mailService) {
        this.adminRepository = adminRepository;
        this.mailService = mailService;
    }

    @Override
    public List<TblAdminEntity> getAll(){
        return adminRepository.findAll();
    }


    @Override
    public List<TblAdminEntity> search(String keyword){
        List<TblAdminEntity> rs = new ArrayList<>();
        List<TblAdminEntity> admins = adminRepository.findAll();
        for (TblAdminEntity admin : admins){
            if (admin.getFullName().toLowerCase().contains(keyword.toLowerCase())){
                rs.add(admin);
            }
        }
        return rs;
    }

    @Override
    public void insert(TblAdminEntity newAdmin){

        // Validate input
        Constants.validateRequiredFields(newAdmin, "username", "password", "fullName");
        Constants.validateStringFields(newAdmin, "UserName 6-20 Ký tự", 6, 20, "username");
        Constants.validateStringFields(newAdmin, "password 8-20 ký tự", 8, 20, "password");
        Constants.validateEmailFields(newAdmin, "email");
        Constants.validateStringFields(newAdmin, "nchar(50)", 0, 50, "email");
        Constants.validateStringFields(newAdmin, "nvarchar(100)", 0, 100, "fullName");
        Constants.validateIntegerFields(newAdmin, "idProvince", "role");



        String adminUserName = newAdmin.getUsername();
        try {
            if (adminRepository.existsByUsername(newAdmin.getUsername())){
                throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR, TABLE_NAME, adminUserName));
            }
            if (adminRepository.existsByEmail(newAdmin.getEmail())){
                throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR_EMAIL, TABLE_NAME, newAdmin.getEmail()));
            }
            Date currentDate = Constants.getCurrentDay();
            Encode encode = new Encode();
            String password = encode.Encrypt(newAdmin.getPassword());
            TblAdminEntity tblAdminEntity = TblAdminEntity.create(
                    0,
                    adminUserName,
                    password,
                    newAdmin.getEmail(),
                    newAdmin.getFullName(),
                    newAdmin.getGender(),
                    newAdmin.getAvatar(),
                    newAdmin.getIdProvince(),
                    currentDate,
                    currentDate,
                    newAdmin.getDeletedAt(),
                    false,
                    newAdmin.getBirthday(),
                    newAdmin.getRole(),
                    newAdmin.getOtp(),
                    currentDate,
                    newAdmin.getStatus()

            );
            adminRepository.save(tblAdminEntity);

        } catch (DataIntegrityViolationException ex){
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME) + ex.getMessage());
        }
    }


    @Transactional
    @Override
    public void update(TblAdminEntity tblAdminEntity){
        if (!adminRepository.existsById(tblAdminEntity.getId())) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, tblAdminEntity.getId()));
        }

        TblAdminEntity admin = adminRepository.findById(tblAdminEntity.getId()).orElse(null);
        if (adminRepository.existsByUsername(tblAdminEntity.getUsername())){
            if (admin.getUsername().trim() == tblAdminEntity.getUsername().trim()){
                throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR, TABLE_NAME, tblAdminEntity.getUsername()));
            }
        }

        try {

            if (admin != null) {
                admin.setUsername(tblAdminEntity.getUsername());
//                admin.setPassword(tblAdminEntity.getPassword());
                admin.setEmail(tblAdminEntity.getEmail());
                admin.setFullName(tblAdminEntity.getFullName());
                admin.setGender(tblAdminEntity.getGender());
                admin.setAvatar(tblAdminEntity.getAvatar());
                admin.setIdProvince(tblAdminEntity.getIdProvince());
                admin.setUpdatedAt(Constants.getCurrentDay());
                admin.setBirthday(tblAdminEntity.getBirthday());
                admin.setOtp(tblAdminEntity.getOtp());
                admin.setOtpExp(tblAdminEntity.getOtpExp());
                admin.setRole(tblAdminEntity.getRole());
                admin.setStatus(tblAdminEntity.getStatus());

                adminRepository.save(admin);
            }
        } catch (NullPointerException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME) + ex.getMessage());
        }
    }




    @Override
    public void softDelete(int id) {
        if (!adminRepository.existsById(id)) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, id));
        }

        try {
            adminRepository.deleteById(id);
            TblAdminEntity admin = adminRepository.findById(id).orElse(null);
            if (admin != null) {
                admin.setDeleted(true);
                admin.setDeletedAt(Constants.getCurrentDay());
                adminRepository.save(admin);
            }
        } catch (DataAccessException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME, id) + ex.getMessage());
        }
    }
    @Override
    public Boolean create(TblAdminEntity tblAdminEntity) {
        try {
            DataMailDto dataMail = new DataMailDto();

            dataMail.setTo(tblAdminEntity.getEmail());
            dataMail.setSubject(Constants.SEND_MAIL_SUBJECT.CLIENT_REGISTER);

            Map<String, Object> props = new HashMap<>();
            props.put("name", tblAdminEntity.getFullName());
            props.put("username", tblAdminEntity.getUsername());
            props.put("password", tblAdminEntity.getPassword());
            props.put("OTP", tblAdminEntity.getOtp());
            dataMail.setProps(props);

            mailService.sendHtmlMail(dataMail, Constants.TEMPLATE_FILE_NAME.CLIENT_REGISTER);
            return true;
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        return false;
    }


}