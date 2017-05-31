package com.taotao.serch.test;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrTest {
	
	@Test
	public void solrSearch() throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://192.168.93.129:8080/solr");
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "22");
		solrServer.add(document);
		solrServer.commit();
	}
	
	@Test
	public void solrCloud() throws Exception {
		CloudSolrServer solrServer = new CloudSolrServer("192.168.93.129:2181,192.168.93.129:2182,192.168.93.129:2183");
		solrServer.setDefaultCollection("collection2");
		
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "222");
		document.addField("item_title", "主题");
		
		solrServer.add(document);
		solrServer.commit();
		
	}
	
}
