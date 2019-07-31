package com.pinyougou.pay.service;

import java.util.Map;

public interface PayService {
    /**
     * 发送请求到支付系统中统一下单返回需要的数据
     * @param outTradeNo 交易号
     * @param totalFee 金额
     * @return 包含了支付二维码链接等的信息
     */
    Map<String, String> createNative(String outTradeNo, String totalFee);
}
