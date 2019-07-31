package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.order.service.OrderService;
import com.pinyougou.pay.service.PayService;
import com.pinyougou.pojo.TbPayLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/pay")
@RestController
public class PayController {

    @Reference
    private OrderService orderService;

    @Reference(timeout = 3000)
    private PayService payService;

    /**
     * 根据支付日志id获取二维码链接等信息
     * @param outTradeNo 支付日志id
     * @return code_url支付二维码链接地址、totalFee总金额、outTradeNo 交易号、result_code操作结果
     */
    @GetMapping("/createNative")
    public Map<String, String> createNative(String outTradeNo){
        try {
            //查询支付日志
            TbPayLog payLog = orderService.findPayLogByOutTradeNo(outTradeNo);

            if (payLog != null) {
                //总金额
                String totalFee = payLog.getTotalFee().toString();
                //调用业务方法
                return payService.createNative(outTradeNo, totalFee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }
}
