package com.fhdone.demo2012.utils.lucene;


import java.io.Reader;

import org.junit.Assert;
import org.junit.Test;

public class LuceneUtilsTest {

	@Test  
	public void testGetMyEnglishStopWordsFile(){
		Reader r = LuceneUtils.getMyEnglishStopWords();
		Assert.assertNotNull(r);
	}
	
}
