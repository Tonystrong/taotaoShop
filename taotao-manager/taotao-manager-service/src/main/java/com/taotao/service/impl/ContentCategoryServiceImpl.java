package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper cCategoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCategoryByPId(Long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> ccList = cCategoryMapper.selectByExample(example);
		List<EasyUITreeNode> result = new ArrayList<>();
		for(TbContentCategory contentCategory : ccList) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(contentCategory.getId());
			node.setState(contentCategory.getIsParent()?"closed":"open");
			node.setText(contentCategory.getName());
			result.add(node);
		}
		return result;
	}

	@Override
	public TaotaoResult saveContentCategory(Long parentId, String name) {
		TbContentCategory category = new TbContentCategory();
		Date date = new Date();
		category.setParentId(parentId);
		category.setName(name);
		category.setIsParent(false);
		category.setCreated(date);
		category.setUpdated(date);
		category.setSortOrder(1);
		category.setStatus(1);
		cCategoryMapper.insert(category);
		//获得添加后获得的id
		Long id = category.getId();
		
		//找到id等于此时传入为parentId的数据应该吧isparent更新为1（true）
		TbContentCategory contentCategory = cCategoryMapper.selectByPrimaryKey(parentId);
		if (!contentCategory.getIsParent()) {
			contentCategory.setIsParent(true);
			cCategoryMapper.updateByPrimaryKey(contentCategory);
		}
		return TaotaoResult.ok(id);
	}
}
