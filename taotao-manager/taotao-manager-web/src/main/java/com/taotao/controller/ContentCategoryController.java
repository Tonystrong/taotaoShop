package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryService ccService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCategory(@RequestParam(value="id", defaultValue="0") Long parentId) {
		return ccService.getContentCategoryByPId(parentId);
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult saveContentCategory(Long parentId, String name) {
		return ccService.saveContentCategory(parentId, name);
	}
}
