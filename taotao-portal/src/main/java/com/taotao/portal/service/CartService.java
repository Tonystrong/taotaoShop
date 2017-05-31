package com.taotao.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;

public interface CartService {

	TaotaoResult addToCart(Long itemId, Integer num, 
			HttpServletRequest request, HttpServletResponse response);

	List<CartItem> getCartItems(HttpServletRequest request);

	TaotaoResult updateNum(Long itemId, Integer num, 
			HttpServletRequest request, HttpServletResponse response);
	
	TaotaoResult deleteCart(Long itemId, HttpServletRequest request, HttpServletResponse response);
}
