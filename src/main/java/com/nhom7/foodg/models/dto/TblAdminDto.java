package com.nhom7.foodg.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class TblAdminDto {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private Boolean gender;
    private String avatar;
    private Integer idProvince;
    private Integer role;
    private Date birthday;
}
