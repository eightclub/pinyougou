package com.pinyougou.cart.service;

import com.pinyougou.vo.Cart;

import java.util.List;

public interface CartService {
    /**
     * 将商品加入到购物车列表并返回新购物车列表
     * @param cartList 购物车列表
     * @param itemId 商品sku id
     * @param num 购买数量
     * @return 新购物车列表
     */
    List<Cart> addItemToCartList(List<Cart> cartList, Long itemId, Integer num);

    /**
     * 根据用户名到redis中查询购物车列表
     * @param username 用户名
     * @return 购物车列表
     */
    List<Cart> findCartListByUsername(String username);

    /**
     * 将购物车列表保存到redis
     * @param newCartList 购物车列表
     * @param username 用户名
     */
    void saveCartListInRedisByUsername(List<Cart> newCartList, String username);
}
