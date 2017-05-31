package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/query")
	@ResponseBody
	public TaotaoResult searchQuery(@RequestParam(defaultValue="") String keyword, @RequestParam(defaultValue="1") int page, @RequestParam(defaultValue="30") int rows) {
		try {
			String kString = new String(keyword.getBytes("iso8859-1"), "utf-8");
			SearchResult result = searchService.SearchQuery(kString, page, rows);
			return TaotaoResult.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
	}
}
