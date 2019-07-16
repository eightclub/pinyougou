package com.pinyougou.sellergoods.service;

import com.github.pagehelper.PageInfo;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.Goods;

import java.util.List;

public interface GoodsService extends BaseService<TbGoods> {
    /**
     * 根据条件搜索
     * @param pageNum 页号
     * @param pageSize 页面大小
     * @param goods 搜索条件
     * @return 分页信息
     */
    PageInfo<TbGoods> search(Integer pageNum, Integer pageSize, TbGoods goods);

    /**
     * 新增
     * @param goods 商品vo（商品基本、描述、sku列表）
     */
    void addGoods(Goods goods);

    /**
     * 根据主键查询
     * @param id 主键；spu id
     * @return 商品vo：基本、描述、sku列表
     */
    Goods findGoodsById(Long id);

    /**
     * 修改
     * @param goods 商品vo：基本、描述、sku列表
     */
    void updateGoods(Goods goods);

    /**
     * 批量更新商品spu的状态
     * @param status 商品审核状态
     * @param ids 商品spu id数组
     */
    void updateStatus(String status, Long[] ids);
}
