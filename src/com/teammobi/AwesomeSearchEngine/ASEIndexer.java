package com.teammobi.AwesomeSearchEngine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.document.Field;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ASEIndexer {
	
	private Directory contextDirectory;
	private String dirPath;
	
	StandardAnalyzer contextAnalyzer;
    IndexWriterConfig config;
    IndexWriter indexWriter;
	
	public ASEIndexer(Directory context, StandardAnalyzer stdAnalyzer, String dirPath){
		this.contextDirectory = context;
		this.contextAnalyzer = stdAnalyzer;
		this.dirPath = dirPath;
		start();
	}
	
	public void start(){
		try{
		    config = new IndexWriterConfig(contextAnalyzer);
		    indexWriter = new IndexWriter(contextDirectory, config);
			//System.out.println(dirPath);
			if(!Files.exists(Paths.get(dirPath)) ){
				System.out.println("not even there bro");
			}
			Files.walk(Paths.get(dirPath)).forEach(filePath -> {
				
			    if (Files.isRegularFile(filePath)) {
			        //System.out.println(filePath);
			    	parseAndIndex(filePath.toString());
			    }
			});
			indexWriter.close();
		}catch(IOException e){}
	}
	
	private void parseAndIndex(String filePath){
	    
		try{
			File input = new File(filePath);
			Document doc = Jsoup.parse(input, "UTF-8", "");
			//System.out.println(doc.title());
			//System.out.println(doc.body().text());
			addDoc(doc.title(), doc.body().text(), "193398817");
			
		}catch(IOException e){System.out.println(e.getMessage());}
	}

	
    private void addDoc(String title, String body, String id) throws IOException {
    	org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
		doc.add(new TextField("title", title, Field.Store.YES));
		doc.add(new TextField("body", body, Field.Store.YES));
		
		// use a string field for isbn because we don't want it tokenized
		doc.add(new StringField("id", id, Field.Store.YES));
		indexWriter.addDocument(doc);
	}

}
