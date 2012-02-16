package com.fhdone.demo2012.utils.lucene;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

public class LuceneUtils {


	public static Reader getMyEnglishStopWords() throws IOException{
		InputStream is = LuceneUtils.class.getClassLoader().getResourceAsStream("my.dic");
		Reader r = new  InputStreamReader(is);
//		r.close();
//		is.close();
		return  r;
	}

	static public Analyzer getAnalyzer(int i){
		Analyzer analyzer;
		try {
			switch(i){
			case 0:
				Reader r = LuceneUtils.getMyEnglishStopWords();
				analyzer = new StandardAnalyzer(Version.LUCENE_35 , r );
				System.out.println("use MyEnglishStopWords");
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
