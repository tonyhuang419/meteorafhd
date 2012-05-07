package com.fhdone.demo2012.utils.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SearchUtils {

	private static Logger logger = LoggerFactory.getLogger(SearchUtils.class);  
	private static int HIT_SPER_PAGE = 5000;
	
	public static void search(String fieldName , String key) throws Exception {
		Analyzer analyzer = LuceneUtils.getAnalyzer(1);
		Query q = new QueryParser(Version.LUCENE_35, fieldName , analyzer).parse(key);
		IndexReader reader = IndexReader.open(FSDirectory.open(Constants.INDEX_DIR), true);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(HIT_SPER_PAGE, true);
		searcher.search(q, collector);
		
		TopDocs td = collector.topDocs();
		int totalHits = td.totalHits;
		logger.info("Amount:" + searcher.maxDoc() + " records, hit " + totalHits);

//		ScoreDoc[] hits = td.scoreDocs;
//		for(int /i=0;i<hits.length;++i) {
//			logger.info(i);
//			int docId = hits[i].doc;
//			Document d = searcher.doc(docId);
//			System.out.println(d.get("contents"));
//			logger.info("id" + d.get("id"));
//			logger.info("companyCd:" + d.get("companyCd"));
//			logger.info("userCd:" + d.get("userCd"));
//			logger.info("actionName:" + d.get("actionName"));
//			logger.info("operationTime:" + d.get("operationTime"));
//			logger.info("parameterInfo:" + d.get("parameterInfo"));
//			logger.info("===============================");
//		}
		searcher.close();
	}
	
	public static void search(String fieldName , Query  query) throws Exception {
		IndexReader reader = IndexReader.open(FSDirectory.open(Constants.INDEX_DIR), true);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(HIT_SPER_PAGE, true);
		searcher.search(query, collector);
		TopDocs td = collector.topDocs();
		int totalHits = td.totalHits;
		logger.info("Amount:" + searcher.maxDoc() + " records, hit " + totalHits);
		searcher.close();
	}

}
