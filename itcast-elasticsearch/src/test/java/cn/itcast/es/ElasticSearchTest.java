package cn.itcast.es;

import cn.itcast.es.dao.ItemDao;
import com.pinyougou.pojo.TbItem;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-es.xml")
public class ElasticSearchTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemDao itemDao;

    //创建索引和映射
    @Test
    public void createIndexAndMapping(){
        //创建索引
        elasticsearchTemplate.createIndex(TbItem.class);

        //添加映射
        elasticsearchTemplate.putMapping(TbItem.class);
    }

    //新增或者更新
    @Test
    public void save(){
        TbItem item = new TbItem();
        item.setId(100005945610L);
        item.setTitle("OPPO Reno Z 4800万超清像素 超清夜景2.0 VOOC闪充 8GB+128GB 极夜黑 全网通4G 全面屏拍照智能游戏手机");
        item.setPrice(2199.0);
        item.setImage("//img10.360buyimg.com/n1/s450x450_jfs/t1/46829/21/1181/137805/5cee1c4eEba6498c9/8f819fe7ce4c7c8e.jpg");
        item.setCategory("手机");
        item.setBrand("OPPO");
        item.setSeller("步步高");
        item.setGoodsId(1L);
        item.setUpdateTime(new Date());

        itemDao.save(item);
    }

    //查询所有
    @Test
    public void findAll(){
        Iterable<TbItem> items = itemDao.findAll();
        for (TbItem item : items) {
            System.out.println(item);
        }
    }

    //分页查询所有
    @Test
    public void findAllByPage(){
        //设置分页
        //参数1：页号（从0开始）
        //参数2：页大小
        PageRequest pageRequest = PageRequest.of(0, 2);

        Iterable<TbItem> items = itemDao.findAll(pageRequest);
        for (TbItem item : items) {
            System.out.println(item);
        }
    }

    //删除
    @Test
    public void delete(){
        itemDao.deleteById(100005945610L);
    }

    //通配符搜索
    @Test
    public void wildCardQuery(){

        //创建查询构造对象
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        /**
         * 参数1：域名（与实体类中@Field配置的一致）
         */
        queryBuilder.withQuery(QueryBuilders.wildcardQuery("title", "手*"));

        //获取查询对象
        NativeSearchQuery query = queryBuilder.build();

        //查询
        AggregatedPage<TbItem> items = elasticsearchTemplate.queryForPage(query, TbItem.class);
        System.out.println("总记录数：" + items.getTotalElements());
        System.out.println("总页数：" + items.getTotalPages());
        for (TbItem item : items) {
            System.out.println(item);
        }
    }


    //分词搜索
    @Test
    public void matchQuery(){

        //创建查询构造对象
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        /**
         * 参数1：域名（与实体类中@Field配置的一致）
         */
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "极夜黑"));

        //获取查询对象
        NativeSearchQuery query = queryBuilder.build();

        //查询
        AggregatedPage<TbItem> items = elasticsearchTemplate.queryForPage(query, TbItem.class);
        System.out.println("总记录数：" + items.getTotalElements());
        System.out.println("总页数：" + items.getTotalPages());
        for (TbItem item : items) {
            System.out.println(item);
        }
    }

    //复制域搜索
    @Test
    public void copyFieldQuery(){

        //创建查询构造对象
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        /**
         * 参数1：域名（与实体类中@Field配置的一致）
         */
        queryBuilder.withQuery(QueryBuilders.matchQuery("keywords", "步步高"));

        //获取查询对象
        NativeSearchQuery query = queryBuilder.build();

        //查询
        AggregatedPage<TbItem> items = elasticsearchTemplate.queryForPage(query, TbItem.class);
        System.out.println("总记录数：" + items.getTotalElements());
        System.out.println("总页数：" + items.getTotalPages());
        for (TbItem item : items) {
            System.out.println(item);
        }
    }


}
