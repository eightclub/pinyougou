package com.pinyougou.task;

import com.pinyougou.mapper.SeckillGoodsMapper;
import com.pinyougou.pojo.TbSeckillGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class SeckillTask {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    /**
     * 更新秒杀商品数据到redis
     *
     */
    @Scheduled(cron = "0/3 * * * * ?")
    public void refreshSeckillGoods(){
        //- 查询在redis中秒杀商品的id集合
        Set set = redisTemplate.boundHashOps("SECKILL_GOODS").keys();
        List list = new ArrayList(set);

        /**
         * -- 不在redis中的那些库存大于0，已审核，开始时间小于等于当前时间，结束时间大于当前时间的秒杀商品。
         * select * from tb_seckill_goods where status='1' and stock_count>0 and start_time<=?
         * and end_time>? and id not in(?,.Redis的秒杀商品id数组.,?)
         */
        //- 查询符合条件的秒杀商品
        Example example = new Example(TbSeckillGoods.class);
        Example.Criteria criteria = example.createCriteria();

        //- 已经审核
        criteria.andEqualTo("status", "1");
        //- 库存大于0
        criteria.andGreaterThan("stockCount", 0);
        //- 开始时间小于等于当前时间
        criteria.andLessThanOrEqualTo("startTime", new Date());
        //- 结束时间大于当前时间
        criteria.andGreaterThan("endTime", new Date());

        if (list != null && list.size()>0) {
            criteria.andNotIn("id", list);
        }

        List<TbSeckillGoods> seckillGoodsList = seckillGoodsMapper.selectByExample(example);

        //- 遍历每个商品设置到redis中
        if (seckillGoodsList != null && seckillGoodsList.size()>0) {
            for (TbSeckillGoods tbSeckillGoods : seckillGoodsList) {
                redisTemplate.boundHashOps("SECKILL_GOODS").put(tbSeckillGoods.getId(), tbSeckillGoods);
            }
            System.out.println("共缓存了 " + seckillGoodsList.size() + " 到缓存中...");
        }
    }

    /**
     * 将在redis中结束时间小于等于当前时间的秒杀商品需要从redis中移除并更新到mysql数据库中。
     *
     */
    @Scheduled(cron = "0/2 * * * * ?")
    public void deleteSeckillGoods(){
        //- 获取redis中秒杀商品列表
        List<TbSeckillGoods> seckillGoodsList = redisTemplate.boundHashOps("SECKILL_GOODS").values();
        //- 遍历每个商品，如果结束时间小于等于当前时间
        if (seckillGoodsList != null && seckillGoodsList.size() > 0) {
            for (TbSeckillGoods tbSeckillGoods : seckillGoodsList) {
                if (tbSeckillGoods.getEndTime().getTime() <= System.currentTimeMillis()) {
                    //  - 更新mysql中商品
                    seckillGoodsMapper.updateByPrimaryKeySelective(tbSeckillGoods);
                    //  - 删除redis中商品
                    redisTemplate.boundHashOps("SECKILL_GOODS").delete(tbSeckillGoods.getId());

                    System.out.println("将商品id为：" + tbSeckillGoods.getId() + " 移出缓存...");
                }
            }

        }
    }
}
