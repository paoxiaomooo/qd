package org.dromara.homework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.Column;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("user_products")
public class UserProductEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;
    @Column(name = "user_id", nullable = true)
    private Integer userId;
    @Column(name="product_id",nullable = true)
    private Integer productId;
    @Column(name = "create_time", nullable = true)
    private Timestamp createTime;

    @Column(name = "update_time", nullable = true)
    private Timestamp updateTime;
}
