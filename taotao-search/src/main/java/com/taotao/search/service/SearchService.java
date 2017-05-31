package com.taotao.search.service;

import com.taotao.search.pojo.SearchResult;

public interface SearchService {

	SearchResult SearchQuery(String keyword, int page, int rows) throws Exception;
}
