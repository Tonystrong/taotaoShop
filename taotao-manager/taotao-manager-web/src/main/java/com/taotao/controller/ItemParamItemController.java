package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemDescService;
import com.taotao.service.ItemParamItemService;

@Controller
@RequestMapping("/rest/item/param/item/")
public class ItemParamItemController {
	
	@Autowired
	private ItemParamItemService itemService;
	@Autowired
	private ItemDescService descService;
	
	@RequestMapping("query/{itemId}")
	@ResponseBody
	public TbItemParamItem getItemParamItem(@PathVariable Long itemId) {
		return itemService.getItemParamItemByItemId(itemId);
	}
	
	@RequestMapping("desc/{itemId}")
	@ResponseBody
	public TbItemDesc getItemDesc(@PathVariable Long itemId) {
		return descService.getItemDescByCid(itemId);
	}
}
