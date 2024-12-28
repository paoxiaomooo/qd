package org.dromara.homework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
@TableName("image")
public class ImageEntity {
    @TableId(type = IdType.ASSIGN_ID) // 自增主键
    private Integer id;

    @Column(name = "product_id",nullable = true) // 产品id
    private Integer productId;
    @Lob
    @Column(name = "image", nullable = true) // 商品图片（以二进制存储）
    private byte[] image;
}
