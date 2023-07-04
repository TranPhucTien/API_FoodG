package com.nhom7.foodg.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Table(name = "tbl_invoice", schema = "dbo", catalog = "foodg")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TblInvoiceEntity {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Integer idDiscount;
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




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public Integer getIdDiscount() {
        return idDiscount;
    }

    public void setIdDiscount(Integer idDiscount) {
        this.idDiscount = idDiscount;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getIdOnePayResponse() {
        return idOnePayResponse;
    }

    public void setIdOnePayResponse(Long idOnePayResponse) {
        this.idOnePayResponse = idOnePayResponse;
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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    public Boolean getPaid() {return paid;}

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

//    public void setLines(List<TblLineEntity> lines){this.lines = lines;}
//    public List<TblLineEntity> getLines()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TblInvoiceEntity that = (TblInvoiceEntity) o;
        return id == that.id && customerId == that.customerId && invoiceNumber == that.invoiceNumber && idDiscount == that.idDiscount && status == that.status && Objects.equals(invoiceDate, that.invoiceDate) && Objects.equals(totalAmount, that.totalAmount) && Objects.equals(tax, that.tax) && Objects.equals(grandTotal, that.grandTotal) && Objects.equals(idOnePayResponse, that.idOnePayResponse) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(dueDate, that.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, invoiceNumber, invoiceDate, totalAmount, tax, idDiscount, grandTotal, status, idOnePayResponse, createdAt, updatedAt, dueDate);
    }
}
