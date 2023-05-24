package com.nhom7.foodg.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Table(name = "tbl_line", schema = "dbo", catalog = "foodg")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TblLineEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "id_invoice")
    private int idInvoice;
    @Basic
    @Column(name = "id_product")
    private String idProduct;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "quantity")
    private int quantity;
    @Basic
    @Column(name = "price")
    private BigDecimal price;
    @Basic
    @Column(name = "unit_price")
    private BigDecimal unitPrice;
    @Basic
    @Column(name = "id_discount")
    private Integer idDiscount;
    @Basic
    @Column(name = "total")
    private BigDecimal total;
}
