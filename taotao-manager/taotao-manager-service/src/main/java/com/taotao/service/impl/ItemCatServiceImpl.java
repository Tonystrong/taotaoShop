package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public List<EasyUITreeNode> getTreeNode(Long itemId) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(itemId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		List<EasyUITreeNode> treeList = new ArrayList<EasyUITreeNode>();
		for(TbItemCat itemCat:list) {
			EasyUITreeNode treeNode = new EasyUITreeNode();
			treeNode.setId(itemCat.getId());
			treeNode.setState(itemCat.getIsParent()?"closed":"open");
			treeNode.setText(itemCat.getName());
			treeList.add(treeNode);
		}
		return treeList;
	}
}
