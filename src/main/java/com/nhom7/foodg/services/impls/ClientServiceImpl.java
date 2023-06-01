package com.nhom7.foodg.services.impls;

import com.nhom7.foodg.models.dto.DataMailDto;
import com.nhom7.foodg.models.dto.TblCustomerDto;
import com.nhom7.foodg.models.entities.TblCustomerEntity;
import com.nhom7.foodg.services.ClientService;
import com.nhom7.foodg.services.MailService;
import com.nhom7.foodg.shareds.Constants;
import com.nhom7.foodg.utils.DataUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private MailService mailService;



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

