package com.pinyougou.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.vo.Cart;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Override
    public List<Cart> addItemToCartList(List<Cart> cartList, Long itemId, Integer num) {
        //商品是否存在、是否合法。
        //
        //1. 商品对应的商家(cart)不存在在购物车列表（cartList)
        //   创建对应的商家（cart)的orderItemList属性添加一个订单商品（orderItem)；将该商家加入到购物车列表
        //2. 商品对应的商家（cart）存在在购物车列表（cartList)
        //   2.1. 商品存在在商家对应的订单商品列表（orderItemList）
        //   2.1.1. 将订单商品的购买数量叠加；重新计算订单商品的总价
        //   2.1.2. 如果订单商品的购买数量为小于1的时候需要将该订单商品从购物车对象cart的orderItemList中删除
        //   2.1.3. 如果购物车对象cart中的orderItemList的大小为0的时候，需要将该购物车对象cart从购物车列表cartList中删除
        //   2.2. 商品不存在在商家对应的订单商品列表（orderItemList）；创建一个订单商品对象加入到当前的购物车对象cart中
        return cartList;
    }
}
