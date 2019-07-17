package com.pinyougou.content.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.ContentMapper;
import com.pinyougou.pojo.TbContent;
import com.pinyougou.content.service.ContentService;
import com.pinyougou.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ContentServiceImpl extends BaseServiceImpl<TbContent> implements ContentService {

    //内容列表在redis中的key的名称
    private static final String REDIS_CONTENT = "CONTENT_LIST";
    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageInfo<TbContent> search(Integer pageNum, Integer pageSize, TbContent content) {
        //设置分页
        PageHelper.startPage(pageNum, pageSize);
        //创建查询对象
        Example example = new Example(TbContent.class);

        //创建查询条件对象
        Example.Criteria criteria = example.createCriteria();

        //模糊查询
        /**if (StringUtils.isNotBlank(content.getProperty())) {
            criteria.andLike("property", "%" + content.getProperty() + "%");
        }*/

        List<TbContent> list = contentMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public List<TbContent> findContentListByCategoryId(Long categoryId) {
        List<TbContent> contentList = null;

        try {
            //从redis查询数据
            contentList = (List<TbContent>) redisTemplate.boundHashOps(REDIS_CONTENT).get(categoryId);
            if (contentList != null && contentList.size() > 0) {
                return contentList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * --根据内容分类id查询其对应的所有有效的内容列表并且根据排序字段降序排序。
         * SELECT * FROM tb_content WHERE category_id = ? AND status = 1 ORDER BY sort_order DESC
         */

        Example example = new Example(TbContent.class);
        example.createCriteria()
                .andEqualTo("status", "1")
                .andEqualTo("categoryId", categoryId);
        //排序
        example.orderBy("sortOrder").desc();

        contentList = contentMapper.selectByExample(example);

        try {
            //设置缓存
            redisTemplate.boundHashOps(REDIS_CONTENT).put(categoryId, contentList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contentList;
    }

}
