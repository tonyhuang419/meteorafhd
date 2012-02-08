package com.fhdone.demo2012.utils.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


public class SearchUtils {
	
	@SuppressWarnings("deprecation")
	public static void search(String q) throws Exception {
		IndexReader reader = IndexReader.open(FSDirectory.open(Constants.INDEX_DIR), true);
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35); 
		QueryParser parser = new QueryParser(Version.LUCENE_35,"actionName", analyzer);
		Query query = parser.parse(q);
		
		
		TopDocs ts = searcher.search(query, 1000);
		int totalHits = ts.totalHits; 
		System.out.println("Amount:" + searcher.maxDoc() + "records，hit" + totalHits);
		ScoreDoc[] hits = ts.scoreDocs;
		for (int i = 0; i < hits.length; i++) {
			int DocId = hits[i].doc;
			Document hitDoc = searcher.doc(DocId);
//			System.out.println(DocId + ":" + hitDoc.get("filename"));
			System.out.println("companyCd:"+hitDoc.getField("companyCd").stringValue()); 
			System.out.println("userCd:"+hitDoc.getField("userCd").stringValue());
			System.out.println("actionName:"+hitDoc.getField("actionName").stringValue());
			System.out.println("parameterInfo:"+hitDoc.getField("parameterInfo").stringValue());
		}
	}
}
