package com.nhom7.foodg.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
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
}
