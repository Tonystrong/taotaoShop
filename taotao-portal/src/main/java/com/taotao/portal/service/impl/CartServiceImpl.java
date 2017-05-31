package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.ItemService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private ItemService itemService;
	
	@Value("${COOKIE_EXPIRE}")
	private Integer COOKIE_EXPIRE;
	
	@Override
	public TaotaoResult addToCart(Long itemId, Integer num, 
			HttpServletRequest request, HttpServletResponse response) {
		//从cookie中取出商品
		List<CartItem> cartItems = getCartList(request);
		for(CartItem cartItem : cartItems) {
			if (cartItem.getId().equals(itemId)) {
				cartItem.setNum(cartItem.getNum()+num);
				CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(cartItems), COOKIE_EXPIRE, true);
				return TaotaoResult.ok();
			}
		}
		TbItem item = itemService.getItemById(itemId);
		CartItem cart = new CartItem();
		cart.setId(itemId);
		cart.setNum(num);
		cart.setPrice(item.getPrice());
		cart.setTitle(item.getTitle());
		if (StringUtils.isNotBlank(item.getImage())) {
			String[] images = item.getImage().split(",");
			cart.setImage(images[0]);
		}
		cartItems.add(cart);
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(cartItems), COOKIE_EXPIRE, true);
		return TaotaoResult.ok();
	}

	@Override
	public List<CartItem> getCartItems(HttpServletRequest request) {
		return getCartList(request);
	}
	
	@Override
	public TaotaoResult updateNum(Long itemId, Integer num, 
				HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> list = getCartList(request);
		for(CartItem item : list) {
			if (item.getId().equals(itemId)) {
				item.setNum(item.getNum()+num);
				CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(list), COOKIE_EXPIRE, true);
				break;
			}
		}
		return TaotaoResult.ok();
	}
	
	private List<CartItem> getCartList(HttpServletRequest request) {
		try {
			String json = CookieUtils.getCookieValue(request, "TT_CART", true);
			
			List<CartItem> list = JsonUtils.jsonToList(json, CartItem.class);
			if (list!=null) {
				return list;
			}
			return new ArrayList<>();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	@Override
	public TaotaoResult deleteCart(Long itemId, HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> items = getCartList(request);
		for(CartItem item : items) {
			if (item.getId().equals(itemId)) {
				items.remove(item);
				CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(items), COOKIE_EXPIRE, true);
				break;
			}
		}
		return TaotaoResult.ok();
	}
}
