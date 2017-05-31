package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ItemParamService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {
	
	@Autowired
	private ItemParamService itemParamService;
	
	@RequestMapping("/list")
	@ResponseBody
	public EasyUIDataGridResult getItemParamPage(Integer page, Integer rows) {
		EasyUIDataGridResult result = itemParamService.getItemParamForPage(page, rows);
		return result;
	}
	
	@RequestMapping("/query/itemcatid/{cid}")
	@ResponseBody
	public TaotaoResult getItemParamData(@PathVariable Long cid) {
		return itemParamService.getItemParamByCatId(cid);
	}
	
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public TaotaoResult saveItemParam(@PathVariable Long cid,String paramData) {
		return itemParamService.saveItemParamBlobs(cid, paramData);
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult deleteItemParam(String ids) {
		return itemParamService.deleteItemParamByIds(ids);
	}
	
}
