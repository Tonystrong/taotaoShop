package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.ItemSearchService;

@Controller
public class ItemSearchController {

	@Autowired
	private ItemSearchService searchService;
	
	@RequestMapping("/search")
	public String itemSearch(@RequestParam("q") String keyword, 
			@RequestParam(defaultValue="1") int page, 
			@RequestParam(defaultValue="60") int rows,
			Model model) {
		try {
			keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
		} catch (Exception e) {
			keyword = "";
			e.printStackTrace();
		}
		SearchResult searchResult = searchService.itemSearch(keyword, page, rows);
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", searchResult.getPageCount());
		model.addAttribute("itemList", searchResult.getItemList());
		model.addAttribute("page", searchResult.getCurPage());
		return "search";
	}
}
