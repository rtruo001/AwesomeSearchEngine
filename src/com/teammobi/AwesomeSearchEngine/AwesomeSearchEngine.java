package com.teammobi.AwesomeSearchEngine;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.json.JSONObject;


public class AwesomeSearchEngine {

	public static Directory index;
	public static StandardAnalyzer analyzer;
	
	public AwesomeSearchEngine(){
		//Create instance of Directory
		index = new RAMDirectory();
		analyzer = new StandardAnalyzer();
	}
	
	public void index(String websitesDir){
		new ASEIndexer(index, analyzer, websitesDir);
	}
	
	public JSONObject search(String query){
		try{
			return (new ASESearcher(index, analyzer)).query(query);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}	
	}
	
}