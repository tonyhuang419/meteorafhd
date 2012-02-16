package com.fhdone.demo2012.utils.lucene;


import java.io.IOException;
import java.io.Reader;

import org.junit.Assert;
import org.junit.Test;

public class LuceneUtilsTest {

	@Test  
	public void testGetMyEnglishStopWordsFile(){
		Reader r = null;
		try {
			r = LuceneUtils.getMyEnglishStopWords();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(r);
	}
	
}
