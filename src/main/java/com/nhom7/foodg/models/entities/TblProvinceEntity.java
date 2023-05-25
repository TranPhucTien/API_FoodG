package com.nhom7.foodg.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "tbl_province", schema = "dbo", catalog = "foodg")
public class TblProvinceEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "ward")
    private String ward;
    @Basic
    @Column(name = "district")
    private String district;
    @Basic
    @Column(name = "city")
    private String city;
}
