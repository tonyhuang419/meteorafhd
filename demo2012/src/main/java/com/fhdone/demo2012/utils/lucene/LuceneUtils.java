package com.fhdone.demo2012.utils.lucene;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

public class LuceneUtils {


	public static Reader getMyEnglishStopWords(){
		InputStream is = LuceneUtils.class.getClassLoader().getResourceAsStream("my.dic");
		Reader r = new  InputStreamReader(is);
		return  r;
	}

	static public Analyzer getAnalyzer(int i){
		Reader r = LuceneUtils.getMyEnglishStopWords();
		Analyzer analyzer;
		try {
			switch(i){
			case 0:
				analyzer = new StandardAnalyzer(Version.LUCENE_35 , r );
				break;
			default:
				analyzer = new StandardAnalyzer(Version.LUCENE_35 );
			}
		} catch (IOException e) {
			analyzer = new StandardAnalyzer(Version.LUCENE_35);
			e.printStackTrace();
		}
		return analyzer;
	}


}
