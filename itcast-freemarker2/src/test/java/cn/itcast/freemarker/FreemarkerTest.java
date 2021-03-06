package cn.itcast.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerTest {

    @Test
    public void test() throws Exception {
        //- 创建freemarker配置对象；
        Configuration configuration = new Configuration(Configuration.getVersion());
        //- 设置配置对象的输出文件的编码，模版目录
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassForTemplateLoading(FreemarkerTest.class,"/ftl");
        //- 获取模版
        Template template = configuration.getTemplate("test.ftl");
        //- 设置数据
        Map<String, Object> dataModal = new HashMap<>();
        dataModal.put("name", "heima");
        dataModal.put("message", "i_am_ljb can we chat.");

        //- 创建文件输出对象
        FileWriter fileWriter = new FileWriter("D:\\itcast\\test\\test.html");

        //- 模版+数据=》输出
        template.process(dataModal, fileWriter);

        fileWriter.close();
    }
}
