package com.nhom7.foodg.models.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TblProvinceEntity that = (TblProvinceEntity) o;
        return id == that.id && Objects.equals(ward, that.ward) && Objects.equals(district, that.district) && Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ward, district, city);
    }
}
