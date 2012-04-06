package com.fhdone.demo2012.utils.solr;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

/**
 * http://lucidworks.lucidimagination.com/display/solr/Using+SolrJ
 * http://wiki.apache.org/solr/Solrj
 */
public class SolrJTest {
	static SolrServer solr;
	
	public static void main(String[] args) throws Exception  {
		String urlString = "http://localhost:8983/solr";
		SolrJTest.solr = new CommonsHttpSolrServer(urlString);
		SolrJTest s = new SolrJTest();
		SolrJTest.del();
		SolrJTest.create();
//		SolrJTest.search();
	}
	
	
	public static void create() throws Exception {
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "552199");
		document.addField("name", "Gouda cheese wheel");
		document.addField("price", "99.99" );
//		UpdateResponse response = solr.add(document);
		solr.add(document);
		
		Item item = new Item();
		item.setId("552100");
		item.setName("Gouda cheese wheel2");
		item.setPrice( "9.99");
		solr.addBean(item);
		//Remember to commit your changes!
		solr.commit();
	}
	
	public static void search() throws Exception{ 
		SolrQuery parameters = new SolrQuery();
		parameters.set("q", "Gouda cheese wheel");
		QueryResponse response = solr.query(parameters);
		SolrDocumentList list = response.getResults();
		System.out.println(list.size());
		List<Item> list2 = response.getBeans(Item.class);
		System.out.println(list2.size());
		for(Item i : list2){
			System.out.println(i.getId());
			System.out.println(i.getName());
			System.out.println(i.getPrice());
		}
	}
	
	public static void del() throws Exception{
		solr.deleteById("552100");
		solr.deleteById("552199");
		solr.commit();
	}
	
}
