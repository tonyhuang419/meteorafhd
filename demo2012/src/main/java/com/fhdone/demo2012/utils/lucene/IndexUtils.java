package com.fhdone.demo2012.utils.lucene;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

import com.fhdone.demo2012.entity.UserLog;

public class IndexUtils {

	public static int index(  List<UserLog> userLogList ) throws IOException {
		if (!Constants.INDEX_DIR.exists() ) {
			throw new IOException();
		}
		Directory  dir = FSDirectory.open(Constants.INDEX_DIR);
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);  
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_35, analyzer);
		indexWriterConfig.setOpenMode(OpenMode.CREATE);
		IndexWriter writer = new IndexWriter(dir,indexWriterConfig);
		//writer.setUseCompoundFile(false);
		indexUserLog(writer, userLogList);
		int numIndexed = writer.maxDoc();
		writer.optimize();
		writer.close();
		return numIndexed;
	}

	private static void indexUserLog(IndexWriter writer, List<UserLog> userLogList ) throws IOException {
		for(UserLog u:userLogList){
			Document doc = new Document();
			if(StringUtils.isNotBlank(u.getId().toString())){
				doc.add(new Field("companyCd", u.getId().toString(), Field.Store.YES, Field.Index.ANALYZED));
			}
			if(StringUtils.isNotBlank(u.getCompanyCd())){
				doc.add(new Field("companyCd", u.getCompanyCd(), Field.Store.YES, Field.Index.ANALYZED));
			}
			if(StringUtils.isNotBlank(u.getUserCd())){
				doc.add(new Field("userCd", u.getUserCd(), Field.Store.YES, Field.Index.ANALYZED));
			}
			if(StringUtils.isNotBlank(u.getActionName())){
				doc.add(new Field("actionName", u.getActionName(), Field.Store.YES, Field.Index.ANALYZED));
			}
			if(StringUtils.isNotBlank(u.getParameterInfo())){
				doc.add(new Field("parameterInfo", u.getParameterInfo(), Field.Store.YES, Field.Index.ANALYZED));
			}
			writer.addDocument(doc);
		}
	}	

}
