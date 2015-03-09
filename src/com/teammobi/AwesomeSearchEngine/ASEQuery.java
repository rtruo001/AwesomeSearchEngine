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
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class ASEQuery
 */
@WebServlet("/ASEQuery")
public class ASEQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AwesomeSearchEngine searchEngine;
	
	private static String websitesDir;
	private static JSONObject errorJSON;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ASEQuery() {
        super();
        // TODO Auto-generated constructor stub
        searchEngine = new AwesomeSearchEngine();
        websitesDir = null;
        
        errorJSON = new JSONObject();
        try{ errorJSON.put("error", "No query given."); }
        catch(JSONException e){ e.printStackTrace(); }
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		if(websitesDir == null){
			websitesDir = getServletContext().getRealPath("/WEB-INF/websites/");
			System.out.println("New instance: " + websitesDir);
			searchEngine.index(websitesDir);
		}
		else System.out.println("Previous instance: " + websitesDir);
		
		PrintWriter out = response.getWriter();
		String query = request.getParameter("query");
		if(query == null){
			out.println(errorJSON.toString());
		}else{
			System.out.println("Query String: " + query);
			JSONObject result = searchEngine.search(query);
			if(result == null) out.println(errorJSON.toString());
			else out.println(result.toString());
			out.close();
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
