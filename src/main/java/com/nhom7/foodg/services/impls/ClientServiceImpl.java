package com.nhom7.foodg.services.impls;

import com.nhom7.foodg.models.dto.DataMailDto;
import com.nhom7.foodg.services.ClientService;
import com.nhom7.foodg.services.MailService;
import com.nhom7.foodg.services.sdi.ClientSdi;
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
    public Boolean create(ClientSdi sdi) {
        try {
            DataMailDto dataMail = new DataMailDto();

            dataMail.setTo(sdi.getEmail());
            dataMail.setSubject(Constants.SEND_MAIL_SUBJECT.CLIENT_REGISTER);

            Map<String, Object> props = new HashMap<>();
            props.put("name", sdi.getName());
            props.put("username", sdi.getUsername());
            props.put("password", DataUtil.generateTempPwd(6));
            dataMail.setProps(props);

            mailService.sendHtmlMail(dataMail, Constants.TEMPLATE_FILE_NAME.CLIENT_REGISTER);
            return true;
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        return false;
    }
}

