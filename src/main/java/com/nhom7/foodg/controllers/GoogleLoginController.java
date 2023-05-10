package com.nhom7.foodg.controllers;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loginGG")
public class GoogleLoginController {
    @GetMapping
    public Object currentUser(OAuth2AuthenticationToken oAuth2AuthenticationToken){ //Ham kieu Object nhan vao OAuth2AuthenticationToken 
        return oAuth2AuthenticationToken.getPrincipal().getAttributes(); //getPrincipal(): Tra ve doi tuong OAuth2 + getAttributes(): tra ve cac thuoc tinh cua doi tuong OAuth
    }
}
