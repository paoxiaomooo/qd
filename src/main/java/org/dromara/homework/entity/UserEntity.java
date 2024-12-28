package org.dromara.homework.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.Column;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("user")
public class UserEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;

    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "email", nullable = true, length = 255)
    private String email;

    @Column(name = "phone", nullable = true, length = 255)
    private String phone;

    @Column(name = "create_time", nullable = true)
    private Timestamp createTime;

    @Column(name = "update_time", nullable = true)
    private Timestamp updateTime;
}
