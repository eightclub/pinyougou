package com.pinyougou.cart.controller;

import com.alibaba.fastjson.JSON;
import com.pinyougou.common.util.CookieUtils;
import com.pinyougou.vo.Cart;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/cart")
@RestController
public class CartController {

    //在浏览器中品优购项目的购物车列表的cookie的名字
    private static final String COOKIE_CART_LIST = "PYG_CART_LIST";

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    /**
     * 查询登录、未登录状态下的购物车列表
     * @return 购物车列表
     */
    @GetMapping("/findCartList")
    public List<Cart> findCartList(){
        //获取当前登录的用户名；因为允许匿名访问，如果为匿名访问的时候用户名为anonymousUser
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if ("anonymousUser".equals(username)) {
            List<Cart> cookieCartList = new ArrayList<>();
            //未登录；购物车数据来自cookie
            String cartListJsonStr = CookieUtils.getCookieValue(request, COOKIE_CART_LIST, true);
            if (StringUtils.isNotBlank(cartListJsonStr)) {
                cookieCartList = JSON.parseArray(cartListJsonStr, Cart.class);
            }
            return cookieCartList;
        } else {
            //已登录；购物车数据来自redis
            return new ArrayList<>();
        }
    }

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
