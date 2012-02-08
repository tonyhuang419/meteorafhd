package com.fhdone.demo2012.utils.lucene.test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Indexer {
	
	public static void main(String[] args) throws IOException {
		int numIndexed = index();
		System.out.println(numIndexed);
	}

	public static int index() throws IOException {
		File indexDir = new File("src\\main\\resources\\luceneData\\index");
		File dataDir = new File("src\\main\\resources\\luceneData\\data");
		
		if (!indexDir.exists() || !dataDir.isDirectory()) {
			throw new IOException();
		}
		Directory  dir = FSDirectory.open(indexDir);
//		IndexWriter writer = new IndexWriter(dir, 
//				new StandardAnalyzer(Version.LUCENE_35,StopAnalyzer.ENGLISH_STOP_WORDS_SET) , 
//				true, IndexWriter.MaxFieldLength.LIMITED);
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);  
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_35, analyzer);
		indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
		IndexWriter writer = new IndexWriter(dir,indexWriterConfig);
//		writer.setUseCompoundFile(false);
		indexDirectory(writer, dataDir);
		int numIndexed = writer.maxDoc();
		writer.optimize();
		writer.close();
		return numIndexed;
	}

	private static void indexDirectory(IndexWriter writer, File dir) throws IOException {
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (f.isDirectory()) {
				indexDirectory(writer, f);
			} else if (f.getName().toLowerCase().endsWith(".txt")) {
				indexFile(writer, f);
			}
		}
	}

	public static void indexFile(IndexWriter writer, File f) throws IOException {
		if (f.isHidden() || !f.exists() || !f.canRead()) {
			return;
		}
		System.out.println("Indexing " + f.getCanonicalPath());
		Document doc = new Document();
		doc.add(new Field("contents", new FileReader(f)));
		doc.add(new Field("contents", f.getName(), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("filename", f.getCanonicalPath(), Field.Store.YES, Field.Index.ANALYZED));
		writer.addDocument(doc);
	}	
}
