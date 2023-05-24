package com.nhom7.foodg.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "tbl_admin", schema = "dbo", catalog = "foodg")
public class TblAdminEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "full_name")
    private String fullName;
    @Basic
    @Column(name = "gender")
    private Boolean gender;
    @Basic
    @Column(name = "avatar")
    private String avatar;
    @Basic
    @Column(name = "id_province")
    private Integer idProvince;
    @Basic
    @Column(name = "created_at")
    private Date createdAt;
    @Basic
    @Column(name = "updated_at")
    private Date updatedAt;
    @Basic
    @Column(name = "deleted_at")
    private Date deletedAt;
    @Basic
    @Column(name = "deleted")
    private Boolean deleted;
    @Basic
    @Column(name = "birthday")
    private Date birthday;
    @Basic
    @Column(name = "role")
    private int role;
    @Basic
    @Column(name = "otp")
    private String otp;
    @Basic
    @Column(name = "otp_exp")
    private Date otpExp;
}
