//package com.nhom7.foodg.controllers;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@RestController
//@RequestMapping
//@Controller
//public class TestController {
//        @GetMapping("/example")
//        public String redirectWithFlashAttribute(RedirectAttributes ra) {
//            // Trả về thông báo thành công
//            ra.addFlashAttribute("message", "Success!");
//
//            // Redirect đến trang example
//            return "redirect:/example";
//        }
//
//        @GetMapping("/example")
//        public String showMessage(Model model) {
//            // Lấy thông báo từ RedirectAttributes và hiển thị trên trang
//            String message = (String) model.asMap().get("message");
//            model.addAttribute("message", message);
//            // Trả về trang example.html
//            return "example";
//        }
//}
