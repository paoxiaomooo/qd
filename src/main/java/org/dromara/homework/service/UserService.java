package org.dromara.homework.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dromara.homework.dto.UserDto;
import org.dromara.homework.entity.UserEntity;
import org.dromara.homework.mapper.UserMapper;
import org.dromara.homework.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public Integer getId(String name,String pwd){
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", name);  // 设置用户名条件
        // 查询用户
        UserEntity userEntity = userMapper.selectOne(queryWrapper);
        // 如果用户存在，验证密码
        if (userEntity != null && PasswordUtils.verifyPassword(pwd, userEntity.getPassword())) {
            // 密码匹配，返回用户ID
            return userEntity.getId();
        }
        // 用户名或密码错误
        return null;
    }
    public boolean insertByDto(UserDto userDto){
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDto.getUsername());  // 设置用户名条件
        UserEntity userEntity = userMapper.selectOne(queryWrapper);
        if(userEntity != null){
            return false;//已有用户，插入失败
        }else{
            UserEntity newUserEntity=new UserEntity();
            newUserEntity.setUsername(userDto.getUsername());
            newUserEntity.setPassword(PasswordUtils.encryptPassword(userDto.getPassword()));
            newUserEntity.setPhone(userDto.getPhone());
            newUserEntity.setEmail(userDto.getEmail());
            userMapper.insert(newUserEntity);
            return true;
        }

    }

}
