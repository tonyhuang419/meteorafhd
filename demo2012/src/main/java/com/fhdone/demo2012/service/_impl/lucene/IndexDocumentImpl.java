package com.fhdone.demo2012.service._impl.lucene;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fhdone.demo2012.service.luncene.IndexDocumentService;
import com.fhdone.demo2012.utils.lucene.LuceneUtils;


@Service("indexDocumentService")
public class IndexDocumentImpl implements IndexDocumentService {

	private Logger logger = LoggerFactory.getLogger(IndexDocumentImpl.class); 
	
	final public static File INDEX_DIR = new File("src\\main\\resources\\luceneData\\index");
	final public static File DATA_DIR = new File("src\\main\\resources\\luceneData\\data");
	
	public int indexDocument( ) throws Exception {
		IndexWriter writer = LuceneUtils.getWriter();
		indexDirectory(writer, DATA_DIR);
		int numIndexed = writer.maxDoc();
		writer.optimize();
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
		logger.info("Indexing " + f.getCanonicalPath());
		Document doc = new Document();
		doc.add(new Field("contents", new FileReader(f)));
		doc.add(new Field("contents", f.getName(), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("filename", f.getCanonicalPath(), Field.Store.YES, Field.Index.ANALYZED));
		writer.addDocument(doc);
	}	

}
