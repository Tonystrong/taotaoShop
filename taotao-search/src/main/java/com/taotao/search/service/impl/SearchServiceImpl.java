package com.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao searchDao;

	@Override
	public SearchResult SearchQuery(String keyword, int page, int rows) throws Exception {
		SolrQuery query = new SolrQuery();
		query.setQuery(keyword);
		query.setStart((page-1)*rows);
		query.setRows(rows);
		query.setHighlight(true);
		//设置默认搜索域
		query.set("df", "item_title");
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<font class=\"skcolor_ljg\">");
		query.setHighlightSimplePost("</font>");
		SearchResult result = searchDao.querySolr(query);
		result.setCurPage(page);
		Long recordCount = result.getRecordCount();
		int pageCount = (int) (recordCount/rows);
		if (recordCount % rows > 0 ) {
			pageCount++;
		}
		result.setPageCount(pageCount);
		return result;
	}
}
