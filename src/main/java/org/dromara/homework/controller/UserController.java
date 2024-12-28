package org.dromara.homework.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.dromara.homework.dto.UserDto;
import org.dromara.homework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping ("/login")//登录
    public SaResult login(@RequestParam String username, @RequestParam String password){
        System.out.println(username);
        System.out.println(password);
        Integer userId=userService.getId(username, password);
        if(userId!=null) {

            StpUtil.login(userId);
            return SaResult.ok("true");
        }
        else return SaResult.error("false");
    }
    @RequestMapping("/register")
    public SaResult register(@RequestBody UserDto userDto){
        if(!userService.insertByDto(userDto)){
            return SaResult.ok("false");
        }
        return SaResult.ok("true");
    }
    @RequestMapping("/isLogin")
    public SaResult isLogin() {
        return SaResult.ok("是否登录：" + StpUtil.isLogin());
    }
    @RequestMapping("/tokenInfo")
    public SaResult tokenInfo() {
        return SaResult.data(StpUtil.getTokenInfo());
    }

    @RequestMapping("/logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok();
    }
}
