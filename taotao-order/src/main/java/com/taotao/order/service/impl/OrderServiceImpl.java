package com.taotao.order.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.component.RedisClient;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbOrderShippingMapper shippingMapper;
	@Autowired
	private RedisClient redisClient;
	
	@Value("${REDIS_ORDER_ID_KEY}")
	private String REDIS_ORDER_ID_KEY;
	@Value("${REDIS_ORDER_ID_BEGIN}")
	private String REDIS_ORDER_ID_BEGIN;
	@Value("${REDIS_ORDER_ITEM_ID_KEY}")
	private String REDIS_ORDER_ITEM_ID_KEY;
	
	@Override
	public TaotaoResult createOrder(OrderInfo orderInfo) {
		//获取订单号，并且插入
		String id = redisClient.get(REDIS_ORDER_ID_KEY);
		if (StringUtils.isBlank(id)) {
			redisClient.set(REDIS_ORDER_ID_KEY, REDIS_ORDER_ID_BEGIN);
		}
		Long orderId = redisClient.incr(REDIS_ORDER_ID_KEY);
		orderInfo.setOrderId(orderId.toString());
		orderInfo.setStatus(1);
		Date date = new Date();
		orderInfo.setCreateTime(date);
		orderInfo.setUpdateTime(date);
		orderMapper.insert(orderInfo);
		
		//插入orderItem
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for(TbOrderItem orderItem : orderItems) {
			Long orderItemId = redisClient.incr(REDIS_ORDER_ITEM_ID_KEY);
			orderItem.setId(orderItemId.toString());
			orderItem.setItemId(orderId.toString());
			orderItemMapper.insert(orderItem);
		}
		
		//插入物流订单
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setUpdated(date);
		orderShipping.setCreated(date);
		orderShipping.setOrderId(orderId.toString());
		shippingMapper.insert(orderShipping);
		return TaotaoResult.ok(orderId);
	}

}
