package lucene;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;

public class Indexer {
	public static void main(String[] args) throws IOException {
//		System.out.println(ClassPath.getClassPath());
		
		File indexDir = new File("src\\main\\resources\\luceneTest\\index");
		File dataDir = new File("src\\main\\resources\\luceneTest\\data");

		int numIndexed = index(indexDir, dataDir);

		System.out.println(numIndexed);
	}

	public static int index(File indexDir, File dataDir) throws IOException {

		if (!indexDir.exists() || !dataDir.isDirectory()) {
			throw new IOException();
		}

		IndexWriter writer = new IndexWriter(indexDir, new StandardAnalyzer(), true, IndexWriter.MaxFieldLength.LIMITED);
		writer.setUseCompoundFile(false);

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
		doc.add(new Field("filename", f.getCanonicalPath(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("contents", new FileReader(f)));
		writer.addDocument(doc);
	}	
}
