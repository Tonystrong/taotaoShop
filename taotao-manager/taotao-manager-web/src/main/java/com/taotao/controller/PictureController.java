package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.pojo.PictureResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.service.PictureService;

@Controller
public class PictureController {
	
	@Autowired
	private PictureService pictureService;
	
	@RequestMapping("pic/upload")
	@ResponseBody
	public String uploadPicture(MultipartFile uploadFile) {
		PictureResult result = pictureService.uploadPic(uploadFile);
		//对于火狐浏览器，不能直接传json数据过去，这里将对象转换为String类型
		//相当于传过去的是Context-type为text/plain
		String json = JsonUtils.objectToJson(result);
		return json;
	}
}
