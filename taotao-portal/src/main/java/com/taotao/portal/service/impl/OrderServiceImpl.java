package com.taotao.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.OrderInfo;
import com.taotao.portal.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;
	
	@Override
	public String createOrder(OrderInfo orderInfo) {
		String json = JsonUtils.objectToJson(orderInfo);
		String order = HttpClientUtil.doPostJson(ORDER_BASE_URL+ORDER_CREATE_URL, json);
		TaotaoResult result = TaotaoResult.format(order);
		String orderId = (String) result.getData();
		return orderId;
	}

}
