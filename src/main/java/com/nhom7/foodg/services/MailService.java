package com.nhom7.foodg.services;

import com.nhom7.foodg.models.dto.DataMailDto;
import jakarta.mail.MessagingException;

import javax.validation.Valid;

public interface MailService {
    void sendHtmlMail(@Valid DataMailDto dataMail, String templateName) throws MessagingException;


}