package com.taotao.portal.service;

import com.taotao.portal.pojo.SearchResult;

public interface ItemSearchService {
	
	SearchResult itemSearch(String keyword, int page, int rows);
}
