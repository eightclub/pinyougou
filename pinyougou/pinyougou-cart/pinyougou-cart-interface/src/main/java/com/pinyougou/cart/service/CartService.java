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
}
