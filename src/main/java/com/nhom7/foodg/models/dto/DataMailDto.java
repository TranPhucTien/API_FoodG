package com.nhom7.foodg.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataMailDto {
        private String to;
        private String subject;
        private String content;
        private Map<String, Object> props;
}
