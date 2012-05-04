package com.fhdone.demo2012.utils.lucene;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class LuceneUtils {

	private static Logger logger = LoggerFactory.getLogger(LuceneUtils.class); 


	public static Reader getMyEnglishStopWords() throws IOException{
		InputStream is = LuceneUtils.class.getClassLoader().getResourceAsStream("my.dic");
		Reader r = new  InputStreamReader(is);
		//r.close();
		//is.close();
		return  r;
	}

	public static IndexWriter getWriter() throws IOException{
		if (! Constants.INDEX_DIR.exists() || ! Constants.DATA_DIR.isDirectory()) {
			throw new IOException();
		}
		Directory  dir = FSDirectory.open( Constants.INDEX_DIR);

		//IndexWriter writer = new IndexWriter(dir, 
		//new StandardAnalyzer(Version.LUCENE_35,StopAnalyzer.ENGLISH_STOP_WORDS_SET) , 
		//true, IndexWriter.MaxFieldLength.LIMITED);
		//Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);  
		Analyzer analyzer = LuceneUtils.getAnalyzer(1);
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_35, analyzer);
		indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
		IndexWriter writer = new IndexWriter(dir,indexWriterConfig);
		//writer.setUseCompoundFile(false);
		return writer;
	}

	public static Analyzer getAnalyzer(int i){
		Analyzer analyzer;
		try {
			switch(i){
			case 0:
				Reader r = LuceneUtils.getMyEnglishStopWords();
				analyzer = new StandardAnalyzer(Version.LUCENE_35 , r );
				logger.info("EnglishStopWords created");
				break;
			case 1:
				analyzer = new IKAnalyzer();
				logger.info("IKAnalyzer created");
				break;
			default:
				analyzer = new StandardAnalyzer(Version.LUCENE_35 );
				logger.info("default Analyzer created");
			}
		} catch (IOException e) {
			analyzer = new StandardAnalyzer(Version.LUCENE_35);
			e.printStackTrace();
		}
		return analyzer;
	}


}
