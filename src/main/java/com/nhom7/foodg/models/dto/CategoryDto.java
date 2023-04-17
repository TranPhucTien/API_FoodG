package com.nhom7.foodg.models.dto;


import lombok.*;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class CategoryDto {
    private String name;
    private String icon;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private Boolean deleted;
}
