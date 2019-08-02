package com.pinyougou.seckill.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.util.IdWorker;
import com.pinyougou.common.util.RedisLock;
import com.pinyougou.mapper.SeckillGoodsMapper;
import com.pinyougou.mapper.SeckillOrderMapper;
import com.pinyougou.pojo.TbSeckillGoods;
import com.pinyougou.pojo.TbSeckillOrder;
import com.pinyougou.seckill.service.SeckillOrderService;
import com.pinyougou.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class SeckillOrderServiceImpl extends BaseServiceImpl<TbSeckillOrder> implements SeckillOrderService {

    //秒杀订单在redis中的键名
    private static final String SECKILL_ORDERS = "SECKILL_ORDERS";
    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IdWorker idWorker;

    @Override
    public PageInfo<TbSeckillOrder> search(Integer pageNum, Integer pageSize, TbSeckillOrder seckillOrder) {
        //设置分页
        PageHelper.startPage(pageNum, pageSize);
        //创建查询对象
        Example example = new Example(TbSeckillOrder.class);

        //创建查询条件对象
        Example.Criteria criteria = example.createCriteria();

        //模糊查询
        /**if (StringUtils.isNotBlank(seckillOrder.getProperty())) {
            criteria.andLike("property", "%" + seckillOrder.getProperty() + "%");
        }*/

        List<TbSeckillOrder> list = seckillOrderMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public String submitOrder(Long seckillGoodsId, String userId) throws Exception {
        //- 加入分布式锁
        RedisLock redisLock = new RedisLock(redisTemplate);
        if(redisLock.lock(seckillGoodsId.toString())) {
            //- 根据秒杀商品id到redis查询秒杀商品
            TbSeckillGoods seckillGoods = (TbSeckillGoods) redisTemplate.boundHashOps(SeckillGoodsServiceImpl.SECKILL_GOODS).get(seckillGoodsId);
            //- 判断商品是否存在、库存是否大于0
            if (seckillGoods == null) {
                throw new RuntimeException("商品不存在");
            }
            if (seckillGoods.getStockCount() <= 0) {
                throw new RuntimeException("已经被抢完了");
            }
            //- 库存减1
            seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
            if(seckillGoods.getStockCount() > 0) {
                //  - 如果库存大于0则直接更新回redis
                redisTemplate.boundHashOps(SeckillGoodsServiceImpl.SECKILL_GOODS).put(seckillGoodsId, seckillGoods);
            } else {
                //  - 如果库存小于等于0的时候；需要更新秒杀商品到mysql，将redis中该商品删除
                seckillGoodsMapper.updateByPrimaryKeySelective(seckillGoods);

                redisTemplate.boundHashOps(SeckillGoodsServiceImpl.SECKILL_GOODS).delete(seckillGoodsId);
            }
            //- 释放分布式锁
            redisLock.unlock(seckillGoodsId.toString());

            //- 生成秒杀订单并保存到redis
            TbSeckillOrder seckillOrder = new TbSeckillOrder();
            String orderId = idWorker.nextId()+"";
            seckillOrder.setId(orderId);
            //未支付
            seckillOrder.setStatus("0");
            seckillOrder.setUserId(userId);
            seckillOrder.setSellerId(seckillGoods.getSellerId());
            seckillOrder.setCreateTime(new Date());
            seckillOrder.setMoney(seckillGoods.getCostPrice().doubleValue());

            redisTemplate.boundHashOps(SECKILL_ORDERS).put(orderId, seckillOrder);

            //- 返回订单id
            return orderId;
        }
        return null;
    }

}
