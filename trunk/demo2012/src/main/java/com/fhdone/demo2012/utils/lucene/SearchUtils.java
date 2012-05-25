package com.fhdone.demo2012.utils.lucene;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SearchUtils {

	private static Logger logger = LoggerFactory.getLogger(SearchUtils.class);  
	private static int HIT_SPER_PAGE = 500;
	
	public static File getIndexDir(){
		return new File(Constants.INDEX_DIR);
	}
	
	public static File getDataDir(){
		return new File(Constants.DATA_DIR);
	}
	
	public static void search(String fieldName , String key) throws Exception {
		File indexFile = SearchUtils.getIndexDir();
		Analyzer analyzer = LuceneUtils.getAnalyzer(1);
		Query q = new QueryParser(Version.LUCENE_35, fieldName , analyzer).parse(key);
		IndexReader reader = IndexReader.open(FSDirectory.open(indexFile), true);
		
		//cache field value
		String[] actionNameValueCache = FieldCache.DEFAULT.getStrings(reader, "actionName");
		
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(HIT_SPER_PAGE, true);
		searcher.search(q, collector);
		TopDocs td = collector.topDocs();
		ScoreDoc[] hits = td.scoreDocs;
		logger.info("MaxDoc:{}  TotalHits:{}  HitsLength:{}",
			new Object[]{(Integer)searcher.maxDoc() , (Integer)td.totalHits , (Integer)hits.length } );
		for(int i=0;i<hits.length;++i) {
			logger.info( "{}" , (i) );
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
//			System.out.println(d.get("contents"));
			logger.info("id: {}" , d.get("id"));
			logger.info("companyCd: {}" , d.get("companyCd"));
			logger.info("userCd: {}" , d.get("userCd"));
			logger.info("actionName: {}" , d.get("actionName") );
			logger.info("get actionName from cache:{}" , actionNameValueCache[ docId ]  );
			logger.info("operationTime: {}" , d.get("operationTime") );
			logger.info("parameterInfo: {}" , d.get("parameterInfo") );
			logger.info("===============================");
		}
		searcher.close();
	}
	
	public static void search(Query  query) throws Exception {
		File indexFile = SearchUtils.getIndexDir();
		IndexReader reader = IndexReader.open(FSDirectory.open(indexFile), true);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(HIT_SPER_PAGE, true);
		searcher.search(query, collector);
		TopDocs td = collector.topDocs();
		ScoreDoc[] hits = td.scoreDocs;
		logger.info("MaxDoc:{}  TotalHits:{}  HitsLength:{}",
			new Object[]{(Integer)searcher.maxDoc() , (Integer)td.totalHits , (Integer)hits.length } );
		searcher.close();
	}

}
