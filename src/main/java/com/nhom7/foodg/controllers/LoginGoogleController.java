package com.nhom7.foodg.controllers;

import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.shareds.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

@RestController
public class LoginGoogleController {
//    @GetMapping("/userAttributes")
//    public Object userAttributes(OAuth2AuthenticationToken oAuth2AuthenticationToken){
//        if(oAuth2AuthenticationToken == null){
//            return (Object)"Chua Dang Nhap!!!";
//        }
//        return oAuth2AuthenticationToken.getPrincipal().getAttributes();
//    }

    @GetMapping("/userAttributes")
    public ResponseEntity<FuncResult<Object>> userAttributes(OAuth2AuthenticationToken oAuth2AuthenticationToken){
        FuncResult<Object> rs = FuncResult.create(
                HttpStatus.OK,
                MessageFormat.format(Constants.GET_DATA_SUCCESS, "users"),
                null
        );

        if(oAuth2AuthenticationToken == null){
            rs.setStatus(HttpStatus.BAD_REQUEST);
            rs.setMessage("Chưa đăng nhập!");
        } else {
            rs.setData(oAuth2AuthenticationToken.getPrincipal().getAttributes());
        }

        return ResponseEntity.ok(rs);
    }
}
