package org.dromara.homework.dto;

import lombok.Data;

@Data
public class ProductQueryDto {
    private Integer page;
    private Integer pageSize;
    private String searchQuery;
    private String category;
}

