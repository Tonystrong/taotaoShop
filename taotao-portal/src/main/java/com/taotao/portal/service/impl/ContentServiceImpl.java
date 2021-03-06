package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.AdNode;
import com.taotao.portal.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_URL}")
	private String REST_CONTENT_URL;
	@Value("${REST_CONTENT_AD1_CID}")
	private String REST_CONTENT_AD1_CID;
	
	@Override
	public String getAdNode() {
		String json = HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_URL+REST_CONTENT_AD1_CID);
		//将json数据转换为java对象，这里吧TbContent放入List中
		TaotaoResult taotaoResult = TaotaoResult.formatToList(json, TbContent.class);
		List<AdNode> resultList = new ArrayList<>();
		List<TbContent> contentList = (List<TbContent>) taotaoResult.getData();
		
		for(TbContent content: contentList) {
			AdNode node = new AdNode();
			node.setHeight(240);
			node.setWidth(670);
			node.setSrc(content.getPic());
			
			node.setHeightB(240);
			node.setWidthB(550);
			node.setSrcB(content.getPic2());
			
			node.setAlt(content.getSubTitle());
			node.setHref(content.getUrl());
			
			resultList.add(node);
		}
		String result = JsonUtils.objectToJson(resultList);
		return result;
	}
	
}
