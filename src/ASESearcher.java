import org.apache.lucene.store.Directory;


public class ASESearcher {
	
	private Directory contextDirectory;
	
	public ASESearcher(Directory context){
		this.contextDirectory = context;
	}
	
	public void startUserQuery(){
		//get user input in loop
		System.out.println("Hello, we are searching. ^^");
	}
}
