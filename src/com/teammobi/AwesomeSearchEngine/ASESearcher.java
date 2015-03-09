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
import org.apache.lucene.search.PhraseQuery;
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
		
		String delims = "[ ]+";
		String[] queryTokens = queryString.split(delims);

	    BooleanQuery booleanQuery = new BooleanQuery();
	    PhraseQuery query1 = new PhraseQuery();
	    query1.setSlop(1000);
	    for(int i = 0 ; i < queryTokens.length ; i++){
	    	query1.add(new Term("title", queryTokens[i]));
	    }
	    //new Term("title", queryString));
	    PhraseQuery query2 = new PhraseQuery();
	    query2.setSlop(1000);
	    for(int i = 0 ; i < queryTokens.length ; i++){
	    	query2.add(new Term("body", queryTokens[i]));
	    }
	    //new Term("body", queryString));
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
	      //currResult.put("body", "here is some text that will go in place of the body");
	      //currResult.put("body", d.get("body"));
	      resultsArr.put(currResult);
	      //System.out.println((i + 1) + ". "+ "\t" + d.get("title"));
	    }
	    reader.close();
	    return resultJSON;
		   
	}
	
}
