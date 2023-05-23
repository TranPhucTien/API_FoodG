package com.nhom7.foodg.models.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tbl_comment", schema = "dbo", catalog = "foodg")
public class TblCommentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "parent_id")
    private Integer parentId;
    @Basic
    @Column(name = "product_id")
    private String productId;
    @Basic
    @Column(name = "customer_id")
    private int customerId;
    @Basic
    @Column(name = "comment")
    private String comment;
    @Basic
    @Column(name = "created_at")
    private Date createdAt;
    @Basic
    @Column(name = "updated_at")
    private Date updatedAt;
    @Basic
    @Column(name = "deleted")
    private Boolean deleted;
    @Basic
    @Column(name = "deleted_by")
    private Integer deletedBy;
    @Basic
    @Column(name = "deleted_at")
    private Date deletedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Integer deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TblCommentEntity that = (TblCommentEntity) o;
        return id == that.id && customerId == that.customerId && Objects.equals(parentId, that.parentId) && Objects.equals(productId, that.productId) && Objects.equals(comment, that.comment) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(deleted, that.deleted) && Objects.equals(deletedBy, that.deletedBy) && Objects.equals(deletedAt, that.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parentId, productId, customerId, comment, createdAt, updatedAt, deleted, deletedBy, deletedAt);
    }
}
