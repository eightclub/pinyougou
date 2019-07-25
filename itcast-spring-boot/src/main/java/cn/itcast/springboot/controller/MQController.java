package cn.itcast.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/mq")
@RestController
public class MQController {

/*
    @Autowired
    private JmsTemplate jmsTemplate;
*/

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    /**
     * 发送mq消息
     * @return 操作结果
     */
    @GetMapping("/sendMsg")
    public String sendMsg(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", "黑马");
        map.put("age", 13);
        jmsMessagingTemplate.convertAndSend("spring.boot.mq.queue", map);
        return "已经发送队列消息；队列为：spring.boot.mq.queue";
    }
    /**
     * 发送mq消息
     * @return 操作结果
     */
    @GetMapping("/sendsms")
    public String sendSmsMsg(){
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", "18169481560");
        map.put("signName", "黑马");
        map.put("templateCode", "SMS_125018593");
        map.put("templateParam", "{\"code\":654321}");
        jmsMessagingTemplate.convertAndSend("itcast_sms_queue", map);
        return "已经发送队列消息；队列为：itcast_sms_queue";
    }
}
