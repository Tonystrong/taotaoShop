package com.taotao.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchItem;
import com.taotao.search.pojo.SearchResult;

@Repository
public class SearchDaoImpl implements SearchDao {
	
	@Autowired
	private SolrServer solrServer;
	
	@Override
	public SearchResult querySolr(SolrQuery query) throws Exception{
		QueryResponse response = solrServer.query(query);
		SolrDocumentList documentList = response.getResults();
		List<SearchItem> itemList = new ArrayList<>();
		for(SolrDocument document:documentList) {
			SearchItem item = new SearchItem();
			item.setCategory_name((String) document.get("category_name"));
			item.setId((String) document.get("id"));
			item.setImage((String) document.get("image"));
			item.setItem_desc((String) document.get("item_desc"));
			item.setPrice((Long) document.get("price"));
			item.setSell_point((String) document.get("sell_point"));
			//这里的title查询出来后需要高亮显示
			Map<String, Map<String, List<String>>> highLighting = response.getHighlighting();
			List<String> list = highLighting.get(document.get("id")).get("item_title");
			if (list!=null && list.size()>0) {
				item.setTitle(list.get(0));
			} else {
				item.setTitle((String) document.get("item_title"));
			}
			itemList.add(item);
		}
		SearchResult result = new SearchResult();
		result.setItemList(itemList);
		result.setRecordCount(documentList.getNumFound());
		return result;
	}
}
