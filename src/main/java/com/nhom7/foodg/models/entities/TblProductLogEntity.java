package com.nhom7.foodg.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;
import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Table(name = "tbl_product_log", schema = "dbo", catalog = "foodg")
public class TblProductLogEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "user_id")
    private int userId;
    @Basic
    @Column(name = "action")
    private String action;
    @Basic
    @Column(name = "product_id")
    private String productId;
    @Basic
    @Column(name = "old_value")
    private String oldValue;
    @Basic
    @Column(name = "new_value")
    private String newValue;
    @Basic
    @Column(name = "created_date")
    private Date createdDate;
}
