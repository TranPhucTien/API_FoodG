package com.nhom7.foodg.services;

import com.nhom7.foodg.models.dto.DataMailDto;
import jakarta.mail.MessagingException;

public interface MailService {
    void sendHtmlMail(DataMailDto dataMail, String templateName) throws MessagingException;


}