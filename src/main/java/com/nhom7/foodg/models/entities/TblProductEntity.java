package com.nhom7.foodg.models.entities;

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
@Table(name = "tbl_product", schema = "dbo", catalog = "foodg")
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@SQLDelete(sql = "UPDATE tbl_product SET deleted = 1 WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted = false")
public class TblProductEntity {
    @Id
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "img")
    private String img;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "dsc")
    private String dsc;
    @Basic
    @Column(name = "price")
    private double price;
    @Basic
    @Column(name = "rate")
    private Double rate;
    @Basic
    @Column(name = "country")
    private String country;
    @Basic
    @Column(name = "id_category")
    private Integer idCategory;
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
    @Column(name = "created_by")
    private int createdBy;
    @Basic
    @Column(name = "updated_by")
    private int updatedBy;
    @Basic
    @Column(name = "deleted_by")
    private Integer deletedBy;
}
