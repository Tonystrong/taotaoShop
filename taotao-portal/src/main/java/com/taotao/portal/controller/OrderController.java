package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.pojo.TbUser;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.OrderInfo;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order-cart")
	public String confirmOrder(Model model, HttpServletRequest request) {
		List<CartItem> cartItems = cartService.getCartItems(request);
		model.addAttribute("cartList", cartItems);
		return "order-cart";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String createOrder(Model model, OrderInfo orderInfo, HttpServletRequest request) {
		TbUser user = (TbUser) request.getAttribute("user");
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		String orderId = orderService.createOrder(orderInfo);
		model.addAttribute("orderId", orderId);
		model.addAttribute("payment", orderInfo.getPayment());
		DateTime dateTime = new DateTime();
		dateTime = dateTime.plusDays(3);
		model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
		return "success";
	}
	
}
