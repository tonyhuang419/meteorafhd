package com.fhdone.demo2012.service.impl.lucene;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fhdone.demo2012.service.luncene.IndexDocumentService;
import com.fhdone.demo2012.utils.lucene.LuceneUtils;
import com.fhdone.demo2012.utils.lucene.SearchUtils;


@Service("indexDocumentService")
public class IndexDocumentImpl implements IndexDocumentService {

	private Logger logger = LoggerFactory.getLogger(IndexDocumentImpl.class); 
	
	public int indexDocument( ) throws Exception {
		File dataFile = SearchUtils.getDataDir();
		IndexWriter writer = LuceneUtils.getWriter();
//		writer.setInfoStream(System.out);
		indexDirectory(writer, dataFile);
//		int numIndexed = writer.maxDoc();
		int numIndexed = writer.numDocs();
//		writer.optimize();
		logger.info("MaxDoc:{}  NumDocs:{} ",
				new Object[]{ (Integer)writer.maxDoc() , (Integer)writer.numDocs() } );
		writer.close();
		return numIndexed;
	}

	private void indexDirectory(IndexWriter writer, File dir) throws IOException {
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

	private void indexFile(IndexWriter writer, File f) throws IOException {
		if (f.isHidden() || !f.exists() || !f.canRead()) {
			return;
		}
		String filePath = f.getCanonicalPath();
		String fileName = f.getName();
		logger.info("Indexing " + fileName);
		writer.deleteDocuments(new Term("file_name",fileName));
		logger.info("MaxDoc:{}  NumDocs:{} ",
				new Object[]{ (Integer)writer.maxDoc() , (Integer)writer.numDocs() } );
		Document doc = new Document();
		doc.add(new Field("file_name", fileName, Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("file_path", filePath, Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("file_contents", new FileReader(f)));
		writer.addDocument(doc);
	}	

}
