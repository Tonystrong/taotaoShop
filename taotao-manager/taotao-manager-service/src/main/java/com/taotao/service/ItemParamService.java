package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;

public interface ItemParamService {

	EasyUIDataGridResult getItemParamForPage(Integer page, Integer size);

	TaotaoResult getItemParamByCatId(Long itemCatId);

	TaotaoResult saveItemParamBlobs(Long cid, String paramData);

	TaotaoResult deleteItemParamByIds(String ids);

}
