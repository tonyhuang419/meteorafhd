package com.fhdone.demo2012.service;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.PhraseQuery;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fhdone.demo2012.BaseTest;
import com.fhdone.demo2012.utils.lucene.SearchUtils;

public class SearchServiceTest extends BaseTest {  

	private Logger logger = LoggerFactory.getLogger(SearchServiceTest.class);  

	@Test
	public void testSearch() throws Exception{  
		logger.info("start testSearchLoginfo");
		SearchUtils.search("actionName","action");
		SearchUtils.search("file_contents","的");
		SearchUtils.search("file_name","a.txt");
		logger.info("end testSearchLoginfo");
	} 
	
	/**
	 * http://www.cnblogs.com/bysshijiajia/archive/2008/01/24/1051401.html
	 */
	@Test
	public void testPhraseQuery() throws Exception{
		logger.info("start testPhraseQuery");
		PhraseQuery query = new PhraseQuery();
		query.setSlop(5);
		query.add(new Term("file_contents", "上午"));
		query.add(new Term("file_contents", "下午"));
		SearchUtils.search( query );
		logger.info("end testPhraseQuery");
	}
	
	@Test
	public void testFuzzyQuery() throws Exception{ 
		logger.info("start testPhraseQuery");
		FuzzyQuery query = new FuzzyQuery(new Term("file_contents", "IndexWriterX"));
		SearchUtils.search( query );
		logger.info("end testPhraseQuery");
	}
	
}
