package com.teammobi.AwesomeSearchEngine;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.json.JSONObject;


public class AwesomeSearchEngine {
	
	private static String websitesDir = "./websites/";
	public static Directory index;
	public static StandardAnalyzer analyzer;
	//private String query;
	
	public AwesomeSearchEngine(){
		//Create instance of Directory
		index = new RAMDirectory();
		analyzer = new StandardAnalyzer();
		
		//Make a ASEIndexer to index files in dir
		new ASEIndexer(index, analyzer, websitesDir);
		
	}
	
	public JSONObject search(String query){
		try{
			return (new ASESearcher(index, analyzer)).query(query);
		}catch(Exception e){
			e.printStackTrace();
			return null;}
		
	}
	
}