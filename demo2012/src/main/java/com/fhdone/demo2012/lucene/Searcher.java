package com.fhdone.demo2012.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Searcher {
	public static void main(String[] args) throws Exception {
		File indexDir = new File("src\\main\\resources\\luceneData\\index");
		String q = "的";
		if (!indexDir.exists() || !indexDir.isDirectory()) {
			throw new IOException();
		}
		search(indexDir, q);
	}

	public static void search(File indexDir, String q) throws Exception {
		IndexReader reader = IndexReader.open(FSDirectory.open(indexDir), true);
		IndexSearcher searcher = new IndexSearcher(reader);
//		Directory directory = FSDirectory.open(indexDir);
//		IndexSearcher searcher = new IndexSearcher(directory, true); 
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35); 
		QueryParser parser = new QueryParser(Version.LUCENE_35,"contents", analyzer);
		Query query = parser.parse(q);
		
//		IndexSearcher isearcher = null;
//		Directory directory = null;
//		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT); // 创建一个查詢语法分析器
//		directory = FSDirectory.open(indexDir);
//		QueryParser parser = new QueryParser(Version.LUCENE_CURRENT,
//				"contents", analyzer);
//		Query query = parser.parse(q);// 获取查询对象
//		IndexSearcher searcher = new IndexSearcher(directory, true); // 创建索引搜索器
		
		TopDocs ts = searcher.search(query, 100);
		int totalHits = ts.totalHits; // 获取命中数
		System.out.println("共有" + searcher.maxDoc() + "条索引，命中" + totalHits + "条");
		ScoreDoc[] hits = ts.scoreDocs;
//		System.out.println("共有" + searcher.maxDoc() + "条索引，命中" + hits.length + "条");
		for (int i = 0; i < hits.length; i++) {
			int DocId = hits[i].doc;
			Document hitDoc = searcher.doc(DocId);
			System.out.println(DocId + ":" + hitDoc.get("filename"));
			System.out.println(hitDoc.getField("contents").stringValue()); 
		}
	}
}
