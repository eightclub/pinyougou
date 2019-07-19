package cn.itcast.es;

import com.pinyougou.pojo.TbItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-es.xml")
public class ElasticSearchTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    //创建索引和映射
    @Test
    public void createIndexAndMapping(){
        //创建索引
        elasticsearchTemplate.createIndex(TbItem.class);

        //添加映射
        elasticsearchTemplate.putMapping(TbItem.class);
    }
}
