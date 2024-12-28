package org.dromara.homework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;
import org.dromara.homework.entity.UserEntity;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
}
