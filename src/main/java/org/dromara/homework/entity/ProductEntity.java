package org.dromara.homework.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@TableName("products") // 表名对应数据库表
public class ProductEntity {
    @TableId(type = IdType.ASSIGN_ID) // 自增主键
    private Integer id;

    @Column(name = "name", nullable = false, length = 255) // 商品名称
    private String name;

    @Column(name = "price", nullable = false) // 商品价格
    private BigDecimal price;


    @Column(name = "description", nullable = true, length = 500) // 商品描述
    private String description;

    @Column(name = "category", nullable = true, length = 100) // 商品类别
    private String category;

    @Column(name = "stock", nullable = false) // 库存数量
    private Integer stock;

    @Column(name = "create_time", nullable = true) // 创建时间
    private Timestamp createTime;

    @Column(name = "update_time", nullable = true) // 更新时间
    private Timestamp updateTime;
}

