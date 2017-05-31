package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTROLLER_URL}")
	private String REST_CONTROLLER_URL;
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult saveContent(TbContent tbContent) {
		TaotaoResult result = contentService.saveTbContent(tbContent);
		HttpClientUtil.doGet(REST_BASE_URL+REST_CONTROLLER_URL+tbContent.getCategoryId());
		System.out.println(REST_BASE_URL+REST_CONTROLLER_URL);
		return result;
	}
}
