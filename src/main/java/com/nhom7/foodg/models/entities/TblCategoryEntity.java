package com.nhom7.foodg.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nhom7.foodg.shareds.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "tbl_category", schema = "dbo", catalog = "foodg")
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@SQLDelete(sql = "UPDATE tbl_category SET deleted = 1 WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted = false")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TblCategoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "icon")
    @Pattern(regexp = Constants.REGEX_URL_IMAGE, message = "Icon nhận vào phải là link ảnh (đuôi png, jpg)")
    private String icon;
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

    public boolean isDeleted() {
        return deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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
        TblCategoryEntity that = (TblCategoryEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(icon, that.icon) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(deletedAt, that.deletedAt) && Objects.equals(deleted, that.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, icon, createdAt, updatedAt, deletedAt, deleted);
    }
}
