package baxtree.apis;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class TriplesGleaner extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		Model rdfa_content_model = ModelFactory.createDefaultModel();
		String rdfa_content = request.getParameter("rdfacontent");
		String base = "http://base_uri"; //request.getParameter("baseuri");
		String serialization = request.getParameter("format");
		System.out.println(rdfa_content);
		StringReader sr = new StringReader(rdfa_content);
		try{
			Class.forName("net.rootdev.javardfa.RDFaReader");
		}
		catch(ClassNotFoundException cnfe){
			cnfe.printStackTrace();
		}
		rdfa_content_model.read(sr, base, "XHTML");		
		PrintWriter out = response.getWriter();
		rdfa_content_model.write(out, serialization);
	}
}
