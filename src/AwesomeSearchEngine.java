import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;


public class AwesomeSearchEngine {
	
	private static String websitesDir = "./websites/";
	public static Directory index;
	
	public static void main(String[] args){
		
		//Create instance of Directory
		index = new RAMDirectory();
		
		//Make a ASEIndexer to index files in dir
		new ASEIndexer(index,websitesDir);
				
		//Make a ASESearcher for users to search through dir
		(new ASESearcher(index)).startUserQuery();
		
	}

	
}