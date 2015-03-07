package com.teammobi.AwesomeSearchEngine;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ASESearcher { 
	
	private Directory contextDirectory;
	private StandardAnalyzer contextAnalyzer;
	
	public ASESearcher(Directory context, StandardAnalyzer stdAnalyzer){
		this.contextDirectory = context;
		this.contextAnalyzer = stdAnalyzer;
	}
	
	public JSONObject query(String queryString) throws IOException, ParseException, JSONException{
		
		JSONObject resultJSON = new JSONObject();
		

		resultJSON.put("query", queryString);
		//get user input in loop
		//System.out.println("Hello, we are searching. ^^");
		
		//Scanner in = new Scanner(System.in);
		//String queryString;
		//while(true){
		//System.out.println("Enter a string");
	    //queryString = in.nextLine();
	    //System.out.println("here is your string: " + queryString);
	    
	    BooleanQuery booleanQuery = new BooleanQuery();
	    Query query1 = new TermQuery(new Term("title", queryString));
	    Query query2 = new TermQuery(new Term("body", queryString));
	    booleanQuery.add(query1, BooleanClause.Occur.SHOULD);
	    booleanQuery.add(query2, BooleanClause.Occur.SHOULD);

	    //Query q = new QueryParser("title", contextAnalyzer).parse(queryString);
	    int hitsPerPage = 10;
		IndexReader reader = DirectoryReader.open(contextDirectory);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
		searcher.search(booleanQuery, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		//System.out.println("Found " + hits.length + " hits.");
	    
		resultJSON.put("results", new JSONArray());
		JSONArray resultsArr = resultJSON.getJSONArray("results");
		for(int i=0;i<hits.length;++i) {
	      int docId = hits[i].doc;
	      Document d = searcher.doc(docId);
	      JSONObject currResult = new JSONObject();
	      System.out.println(d.get("title"));
	      currResult.put("title", d.get("title"));
	      currResult.put("body", "here is some text that will go in place of the body");
	      //currResult.put("body", d.get("body"));
	      resultsArr.put(currResult);
	      //System.out.println((i + 1) + ". "+ "\t" + d.get("title"));
	    }
	    reader.close();
	    return resultJSON;
		   
	}
	
}
