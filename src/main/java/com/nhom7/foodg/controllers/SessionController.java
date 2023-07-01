package com.nhom7.foodg.controllers;

import com.nhom7.foodg.models.entities.TblAdminEntity;
import com.nhom7.foodg.models.entities.TblCustomerEntity;
import com.nhom7.foodg.repositories.AdminRepository;
import com.nhom7.foodg.repositories.CustomerRepository;
import com.nhom7.foodg.services.MailService;
import com.nhom7.foodg.utils.Encode;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

//import javax.servlet.http.*;
@RequestMapping
@RestController
public class SessionController {
    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;

    public SessionController(AdminRepository adminRepository, CustomerRepository customerRepository) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("admin/loginsuccess")
    //.........admin/loginsuccess?_usernameAd=......&_passwordAd=.............
    public String adminLoginSuccess(HttpSession session, @RequestParam(name = "_usernameAd") String username,
                              @RequestParam(name = "_passwordAd") String password) {
        Encode encode = new Encode();
        Boolean exitAdmin = adminRepository.existsByUsername(username);
        if(exitAdmin){
            TblAdminEntity admin = adminRepository.findFirstByUsername(username);
            String passwordEn = (String) encode.Encrypt(password);
            String passwordAd = (String) admin.getPassword();
            if(passwordEn.equals(passwordAd)) {
                session.setAttribute("username", username);
                session.setAttribute("role", "admin");
                return "login_thanh_cong";
            }
        }
        return "Sai password or username";
    }
    @GetMapping("customer/loginsuccess")
    //.........customer/loginsucess?_usernameUser=......&_passwordUser=.............
    public String customerLoginSuccess(HttpSession session, @RequestParam(name = "_usernameUser") String username,
                              @RequestParam(name = "_passwordUser") String password) {
        Encode encode = new Encode();
        Boolean exitCus = customerRepository.existsByUsername(username);
        if(exitCus){
            TblCustomerEntity customer = customerRepository.findFirstByUsername(username);
            String passwordEn = (String) encode.Encrypt(password);
            String passwordCus = (String) customer.getPassword();
            if(passwordEn.equals(passwordCus)){
                session.setAttribute("username", username);
                session.setAttribute("role", "customer");
                return "login_thanh_cong";
            }
        }
        return "Sai password or username";
    }
    @GetMapping("/checkLogin")
    public String checkLogin(HttpSession session){
        String username = (String) session.getAttribute("username");
        if(username == null){
            return "chua_dang_nhap";
        }
        else{
            return "/";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "/sucess";
    }
}
