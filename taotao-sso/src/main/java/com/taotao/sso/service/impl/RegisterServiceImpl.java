package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private TbUserMapper userMapper;
	
	@Override
	public TaotaoResult validateUser(String param, Integer type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		if (type == 1) {
			criteria.andUsernameEqualTo(param);
		}
		else if (type == 2) {
			criteria.andPhoneEqualTo(param);
		}
		else if (type == 3) {
			criteria.andEmailEqualTo(param);
		}
		List<TbUser> list = userMapper.selectByExample(example);
		if (list!=null && list.size()>0) {
			return TaotaoResult.ok(false);
		}
		return TaotaoResult.ok(true);
	}

	@Override
	public TaotaoResult register(TbUser user) {
		if (StringUtils.isBlank(user.getPassword()) || 
				 StringUtils.isBlank(user.getUsername())) {
			return TaotaoResult.build(400, "用户名或密码不能为空");
		}
		
		TaotaoResult result = validateUser(user.getUsername(), 1);
		if (!(boolean) result.getData()) {
			return TaotaoResult.build(400, "用户名重复");
		}
		if (user.getPhone()!=null) {
			result = validateUser(user.getPhone(), 2);
			if (!(boolean) result.getData()) {
				return TaotaoResult.build(400, "电话重复");
			}
		}
		if (user.getEmail()!=null) {
			result = validateUser(user.getEmail(), 3);
			if (!(boolean) result.getData()) {
				return TaotaoResult.build(400, "邮箱重复");
			}
		}
		
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		Date date = new Date();
		user.setCreated(date);
		user.setUpdated(date);
		userMapper.insert(user);
		return TaotaoResult.ok();
	}

}
