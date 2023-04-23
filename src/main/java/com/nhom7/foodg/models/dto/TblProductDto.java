package com.nhom7.foodg.models.dto;


import lombok.Data;

import java.sql.Date;

@Data
public class TblProductDto {
    private String img;
    private String name;
    private String dsc;
    private double price;
    private Double rate;
    private String country;
    private Integer idCategory;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
