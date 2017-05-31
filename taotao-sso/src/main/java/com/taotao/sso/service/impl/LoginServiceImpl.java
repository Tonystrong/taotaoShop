package com.taotao.sso.service.impl;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.component.RedisClient;
import com.taotao.sso.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private RedisClient redisClient;
	
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	@Value("${REDIS_SESSION_KEY}")
	private String REDIS_SESSION_KEY;
	@Value("${COOKIE_NAME}")
	private String COOKIE_NAME;
	
	@Override
	public TaotaoResult login(String userName, String password, HttpServletRequest request,
			HttpServletResponse response) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(userName);
		List<TbUser> list = userMapper.selectByExample(example);
		if (list.isEmpty() || list==null) {
			return TaotaoResult.build(400, "用户名或密码错误");
		}
		TbUser user = list.get(0);
		if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
			return TaotaoResult.build(400, "用户名或密码错误");
		}
		String token = UUID.randomUUID().toString();
		user.setPassword(null);
		
		redisClient.set(REDIS_SESSION_KEY+":"+token, JsonUtils.objectToJson(user));
		CookieUtils.setCookie(request, response, COOKIE_NAME, token);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult checkToken(String token) {
		String json = redisClient.get(REDIS_SESSION_KEY+":"+token);
		if (StringUtils.isBlank(json)) {
			return TaotaoResult.build(400, "用户session已经过期");
		}
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
		redisClient.expire(REDIS_SESSION_KEY+":"+token, SESSION_EXPIRE);
		
		return TaotaoResult.ok(user);
	}
}
