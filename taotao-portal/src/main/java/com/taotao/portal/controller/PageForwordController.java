package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.portal.service.ContentService;

@Controller
public class PageForwordController {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/index")
	public String toIndex(Model model) {
		String json = contentService.getAdNode();
		model.addAttribute("ad1", json);
		return "index";
	}
}
