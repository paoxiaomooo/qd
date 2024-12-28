package org.dromara.homework.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private String name;
    private BigDecimal price;
    private Integer image_id;
    private String description;
    private String category;
//    private String username;
    private Integer stock;
}

