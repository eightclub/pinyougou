package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Override
    public Map<String, Object> search(Map<String, Object> searchMap) {
        Map<String, Object> resultMap = new HashMap<>();

        //创建查询条件构造对象
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        //没有搜索条件的时候查询全部
        builder.withQuery(QueryBuilders.matchAllQuery());

        if (searchMap != null) {
            //搜索关键字
            String keyword = searchMap.get("keyword") + "";
            if (StringUtils.isNotBlank(keyword)) {
                //在标题、分类、品牌、商家名称4个域中都查询keyword
                builder.withQuery(QueryBuilders.multiMatchQuery(keyword, "title", "category", "brand", "seller"));
            }
        }

        //获取查询对象
        NativeSearchQuery searchQuery = builder.build();

        //查询
        AggregatedPage<TbItem> pageResult = esTemplate.queryForPage(searchQuery, TbItem.class);

        //商品列表
        resultMap.put("itemList", pageResult.getContent());

        return resultMap;
    }
}
