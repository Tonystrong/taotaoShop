package com.taotao.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.pojo.PictureResult;
import com.taotao.fastdfs.FastDFSClient;
import com.taotao.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService{
	
	@Value("${NGINX_IP_URL}")
	private String NGINX_IP_URL;
	
	@Override
	public PictureResult uploadPic(MultipartFile pFile) {
		
		PictureResult result = new PictureResult();
		if (pFile.isEmpty()) {
			result.setError(1);
			result.setMessage("图片为空");
			result.setUrl(null);
			return result;
		}
		try {
			String originalFileName = pFile.getOriginalFilename();
			String extName = originalFileName.substring(
						originalFileName.indexOf(".")+1);
			System.out.println(originalFileName);
			FastDFSClient client = new FastDFSClient("classpath:properties/client.conf");
			String url = client.uploadFile(pFile.getBytes(), extName);
			url = NGINX_IP_URL + url;
			result.setError(0);
			result.setUrl(url);
			result.setMessage("成功");
		} catch (Exception e) {
			result.setError(1);
			result.setMessage("保存失败");
		}
		return result;
	}
}
