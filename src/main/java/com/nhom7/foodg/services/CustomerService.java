package com.nhom7.foodg.services;

import com.nhom7.foodg.models.dto.TblCustomerDto;
import com.nhom7.foodg.models.entities.TblCustomerEntity;
import com.nhom7.foodg.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface CustomerService {
    List<TblCustomerEntity> getAll();

    List<TblCustomerEntity> search(String keyword);

    void insert(TblCustomerEntity newCustomer);

    boolean insert(TblCustomerEntity newCustomer, String otp, String userName);

    void update(TblCustomerEntity tblCustomerEntity);

    void update(TblCustomerDto tblCustomerDto, String otp);
    //
    void softDelete(int id);
//    @Scheduled(fixedDelay = 60 * 1000) // Xóa dữ liệu mỗi 24 giờ
//    void cleanupExpiredData(TblCustomerEntity tblCustomerEntity);

//    @Transactional
//    public void cleanupExpiredOTP();


}