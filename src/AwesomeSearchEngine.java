
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;


public class AwesomeSearchEngine {
	
	private static String websitesDir = "./websites/";
	public static Directory index;
	public static StandardAnalyzer analyzer;
	
	public static void main(String[] args){
		//Create instance of Directory
		index = new RAMDirectory();
		analyzer = new StandardAnalyzer();
		
		//Make a ASEIndexer to index files in dir
		new ASEIndexer(index, analyzer, websitesDir);
				
		//Make a ASESearcher for users to search through dir
		try{
		(new ASESearcher(index, analyzer)).startUserQuery();
		}catch(Exception e){}
		
	}

	
}