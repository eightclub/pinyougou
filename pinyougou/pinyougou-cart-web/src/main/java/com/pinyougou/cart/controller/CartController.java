package com.pinyougou.cart.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/cart")
@RestController
public class CartController {

    /**
     * 获取用户信息
     * @return 用户信息
     */
    @GetMapping("/getUsername")
    public Map<String, Object> getUsername(){
        Map<String, Object> map = new HashMap<>();
        //获取当前登录的用户名；因为允许匿名访问，如果为匿名访问的时候用户名为anonymousUser
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        map.put("username", username);

        return map;
    }
}
