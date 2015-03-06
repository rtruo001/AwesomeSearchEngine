import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.store.Directory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class ASEIndexer {
	
	private Directory contextDirectory;
	private String dirPath;
	
	public ASEIndexer(Directory context, String dirPath){
		this.contextDirectory = context;
		this.dirPath = dirPath;
		start();
	}
	
	public void start(){
		try{
			Files.walk(Paths.get(dirPath)).forEach(filePath -> {
			    if (Files.isRegularFile(filePath)) {
			        //System.out.println(filePath);
			    	parseAndIndex(filePath.toString());
			    }
			});
		
		}catch(IOException e){}
	}
	
	private void parseAndIndex(String filePath){
		try{
			File input = new File(filePath);
			Document doc = Jsoup.parse(input, "UTF-8", "");
			System.out.println(doc.title());
			Elements titles = doc.select("p");
			//System.out.println("asdf");
			for(Element title : titles){
				System.out.println(title);
			}
			
		}catch(IOException e){System.out.println(e.getMessage());}
	}
	
	
	
	
	
}
