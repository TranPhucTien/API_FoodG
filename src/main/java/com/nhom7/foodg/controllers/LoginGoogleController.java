package com.nhom7.foodg.controllers;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginGoogleController {
    @GetMapping("/userAttributes")
    public Object userAttributes(OAuth2AuthenticationToken oAuth2AuthenticationToken){
        if(oAuth2AuthenticationToken == null){
            return (Object)"Chua Dang Nhap!!!";
        }
        return oAuth2AuthenticationToken.getPrincipal().getAttributes();
    }
}
