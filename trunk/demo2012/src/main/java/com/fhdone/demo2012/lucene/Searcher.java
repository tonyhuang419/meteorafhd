package com.fhdone.demo2012.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
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
		String q = "1";
		if (!indexDir.exists() || !indexDir.isDirectory()) {
			throw new IOException();
		}
		search(indexDir, q);
	}

	public static void search(File indexDir, String q) throws Exception {
		Directory fsDir = FSDirectory.open(indexDir);
		IndexSearcher searcher = new IndexSearcher(fsDir);
		QueryParser parser = new QueryParser( Version.LUCENE_35 ,"contents" 
				,new StandardAnalyzer(Version.LUCENE_35,StopAnalyzer.ENGLISH_STOP_WORDS_SET));
		Query query = parser.parse(q);

		TopDocs topDocs = searcher.search(query, 100);//100是显示队列的Size
		ScoreDoc[] hits = topDocs.scoreDocs;
		System.out.println("共有" + searcher.maxDoc() + "条索引，命中" + hits.length + "条");
		for (int i = 0; i < hits.length; i++) {
			int DocId = hits[i].doc;
			Document document = searcher.doc(DocId);
			System.out.println(DocId + ":" + document.get("filename"));
		}
	}
}
