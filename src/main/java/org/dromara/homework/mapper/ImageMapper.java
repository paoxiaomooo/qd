package org.dromara.homework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.dromara.homework.entity.ImageEntity;
import org.dromara.homework.entity.UserEntity;

@Mapper
public interface ImageMapper extends BaseMapper<ImageEntity> {


}

