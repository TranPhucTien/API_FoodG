package com.nhom7.foodg.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "tbl_product", schema = "dbo", catalog = "foodg")
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@SQLDelete(sql = "UPDATE tbl_product SET deleted = 1 WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted = false")
public class TblProductEntity {
    @Id
    @Column(name = "ID")
    private String id;
    @Basic
    @Column(name = "Img")
    private String img;
    @Basic
    @Column(name = "Name")
    private String name;
    @Basic
    @Column(name = "Dsc")
    private String dsc;
    @Basic
    @Column(name = "Price")
    private double price;
    @Basic
    @Column(name = "Rate")
    private Double rate;
    @Basic
    @Column(name = "Country")
    private String country;
    @Basic
    @Column(name = "IDCategory")
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

    public boolean isDeleted() {
        return deleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TblProductEntity that = (TblProductEntity) o;
        return Double.compare(that.price, price) == 0 && Objects.equals(id, that.id) && Objects.equals(img, that.img) && Objects.equals(name, that.name) && Objects.equals(dsc, that.dsc) && Objects.equals(rate, that.rate) && Objects.equals(country, that.country) && Objects.equals(idCategory, that.idCategory) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(deletedAt, that.deletedAt) && Objects.equals(deleted, that.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, img, name, dsc, price, rate, country, idCategory, createdAt, updatedAt, deletedAt, deleted);
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Integer deletedBy) {
        this.deletedBy = deletedBy;
    }
}
