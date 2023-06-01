package com.nhom7.foodg.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Table(name = "tbl_invoice", schema = "dbo", catalog = "foodg")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TblInvoiceEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "customer_id")
    private int customerId;
    @Basic
    @Column(name = "invoice_number")
    private int invoiceNumber;
    @Basic
    @Column(name = "invoice_date")
    private Date invoiceDate;
    @Basic
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Basic
    @Column(name = "tax")
    private BigDecimal tax;
    @Basic
    @Column(name = "id_discount")
    private int idDiscount;
    @Basic
    @Column(name = "grand_total")
    private BigDecimal grandTotal;
    @Basic
    @Column(name = "status")
    private int status;
    @Basic
    @Column(name = "id_one_pay_response")
    private Long idOnePayResponse;
    @Basic
    @Column(name = "created_at")
    private Date createdAt;
    @Basic
    @Column(name = "updated_at")
    private Date updatedAt;
    @Basic
    @Column(name = "due_date")
    private Date dueDate;
    @Basic
    @Column(name = "paid")
    private Boolean paid;
    @Basic
    @Column(name = "paid_date")
    private Date paidDate;
}
