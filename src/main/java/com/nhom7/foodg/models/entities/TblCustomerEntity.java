package com.nhom7.foodg.models.entities;

import com.nhom7.foodg.shareds.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@SQLDelete(sql = "UPDATE tbl_customer SET deleted = 1 WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted = false")
@Table(name = "tbl_customer", schema = "dbo", catalog = "foodg")
public class TblCustomerEntity {
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
    @Column(name = "otp")
    private String otp;
    @Basic
    @Column(name = "otp_exp")
    private Date otpExp;
    @Basic
    @Column(name = "role")
    private Integer role;
    @Basic
    @Column(name = "status")
    private Boolean status;

    public boolean isOTPRequired() {
        if (this.getOtp() == null) {
            return true;
        }

        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeInMillis = this.otpExp.getTime();

        if (otpRequestedTimeInMillis + Constants.OTP_VALID_DURATION > currentTimeInMillis) {
            return false;
        }
        return true;
    }
}
