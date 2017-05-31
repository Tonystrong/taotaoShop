package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface RegisterService {
	
	TaotaoResult validateUser(String param, Integer type);

	TaotaoResult register(TbUser user);
	
}
