package com.nhom7.foodg.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "tbl_discount", schema = "dbo", catalog = "foodg")
public class TblDiscountEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "code")
    private String code;
    @Basic
    @Column(name = "percentage")
    private int percentage;
    @Basic
    @Column(name = "min_amount")
    private float minAmount;
    @Basic
    @Column(name = "start_date")
    private Date startDate;
    @Basic
    @Column(name = "end_date")
    private Date endDate;
    @Basic
    @Column(name = "is_active")
    private Boolean isActive;
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
    @Column(name = "max_discount_price")
    private float maxDiscountPrice;
    @Basic
    @Column(name = "created_by")
    private int createdBy;
    @Basic
    @Column(name = "updated_by")
    private int updatedBy;
    @Basic
    @Column(name = "deleted_by")
    private Integer deletedBy;
    @Basic
    @Column(name = "max_amount")
    private Float maxAmount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TblDiscountEntity that = (TblDiscountEntity) o;
        return id == that.id && Objects.equals(code, that.code) && Objects.equals(percentage, that.percentage)  && Objects.equals(minAmount, that.minAmount) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(isActive, that.isActive) &&  Objects.equals(updatedAt, that.updatedAt) && Objects.equals(deletedAt, that.deletedAt) && Objects.equals(deleted, that.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, percentage, minAmount, startDate, endDate, isActive, createdAt, updatedAt, deletedAt, deleted);
    }

}
