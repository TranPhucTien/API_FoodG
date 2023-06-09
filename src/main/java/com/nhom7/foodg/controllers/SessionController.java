package com.nhom7.foodg.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

//import javax.servlet.http.*;
@RequestMapping
@RestController
public class SessionController {
    @GetMapping("/loginsucess")
    public String loginsucess(HttpSession session, @RequestParam(name = "_username") String username,
                              @RequestParam(name = "_password") String password) {
        session.setAttribute("username", username);
        return "/";
    }
    @GetMapping("/checkLogin")
    public String checkLogin(HttpSession session){
        String username = (String) session.getAttribute("username");
        if(username == null){
            return "/login";
        }
        else{
            return "/";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
}
