package com.teammobi.AwesomeSearchEngine;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.json.JSONObject;

/**
 * Servlet implementation class ASEQuery
 */
@WebServlet("/ASEQuery")
public class ASEQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AwesomeSearchEngine searchEngine;
	
	private static String websitesDir;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ASEQuery() {
        super();
        // TODO Auto-generated constructor stub
        searchEngine = new AwesomeSearchEngine();
        websitesDir = null;
        
//        if(getServletContext() == null){
//        	System.out.println("wtf");
//        }
//        websitesDir = getServletContext().getRealPath("/WEB-INF/");
//        System.out.println(websitesDir);
//        searchEngine.index(websitesDir);
//        try{
//        PrintWriter writer = new PrintWriter("hehe.txt", "UTF-8");
//        writer.println("The first line");
//        writer.println("The second line");
//        writer.close();
//        }catch(Exception e){System.out.println("cant do it bro"); e.printStackTrace();}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//HttpServletRequest getParameter
		PrintWriter out = response.getWriter();
		
		if(websitesDir == null){
			websitesDir = getServletContext().getRealPath("/WEB-INF/websites/");
			System.out.println("New instance: " + websitesDir);
			searchEngine.index(websitesDir);
		}else{
			System.out.println("Previous instance: " + websitesDir);
		}
		
		//String query = "ucr alumni";
		String query = request.getQueryString();
		System.out.println("Query String: " + query);
		JSONObject result = searchEngine.search(query);
		
		if(result == null){
			out.println("damn");
		}else{
			out.println(result.toString());
		}
		
		out.close();
		
		//new BooleanQuery();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
