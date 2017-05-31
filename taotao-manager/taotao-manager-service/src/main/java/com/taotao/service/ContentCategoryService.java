package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

public interface ContentCategoryService {

	List<EasyUITreeNode> getContentCategoryByPId(Long parentId);
	
	TaotaoResult saveContentCategory(Long parentId, String name);
	
	
}
