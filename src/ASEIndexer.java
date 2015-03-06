import org.apache.lucene.store.Directory;


public class ASEIndexer {
	
	private Directory contextDirectory;
	private String dirPath;
	
	public ASEIndexer(Directory context, String dirPath){
		this.contextDirectory = context;
		this.dirPath = dirPath;
	}
	
	
	
}
