package com.taotao.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	TbContentMapper contentMapper;
	
	@Override
	public TaotaoResult saveTbContent(TbContent tbContent) {
		Date date = new Date();
		tbContent.setCreated(date);
		tbContent.setUpdated(date);
		contentMapper.insert(tbContent);
		return TaotaoResult.ok();
	}

}
