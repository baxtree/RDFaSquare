package baxtree.apis;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class TriplesGleaner extends HttpServlet {
	
	private static String UI = "<link rel=\"stylesheet\" type=\"text/css\" href=\"http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.1/themes/base/jquery-ui.css\"/><script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js\"></script><script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.1/jquery-ui.min.js\"></script><script type=\"text/javascript\" src=\"javascript/rdfaui.js\"></script>";
	
	private String cleanBeforeExporting(String rdfa_content){
		rdfa_content = rdfa_content.replaceAll(UI, "");
		rdfa_content = rdfa_content.replaceAll(" onclick=\"javascript:return false;\"", "");
		rdfa_content = rdfa_content.replaceAll("<iframe name=\"dereference\" style=\"display:none\"></iframe>", "");
		System.out.println(rdfa_content);
		return rdfa_content;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {//TODO encoding errors need to be erased.
		Model rdfa_content_model = ModelFactory.createDefaultModel();
		String rdfa_content = request.getParameter("rdfacontent");
		String base = "http://base_uri"; //request.getParameter("baseuri");
		String serialization = request.getParameter("format");
		System.out.println(serialization);
		if(serialization.equalsIgnoreCase("XHTML + RDFa")){
			ServletOutputStream op = response.getOutputStream();
			response.setContentType("application/xhtml+xml");
			response.setContentLength(rdfa_content.length());
//			response.addHeader("Content-Type", "application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=annotated.html");
			//DataInputStream in = new DataInputStream(new InputStream());
			rdfa_content = cleanBeforeExporting(rdfa_content);
			op.write(rdfa_content.getBytes());
			op.flush();
			op.close();
		}
		else if(serialization.equalsIgnoreCase("JSON")){
			//TODO this functionality needs to be implemented.
		}
		else{
			ServletOutputStream op = response.getOutputStream();
			response.setContentType("application/rdf+xml");
			//response.setContentLength();
			if(serialization.equalsIgnoreCase("RDF/XML") || serialization.equalsIgnoreCase("RDF/XML-ABBREV")){
				response.setHeader("Content-Disposition", "attachment; filename=annotated.rdf");
			}
			else if(serialization.equalsIgnoreCase("N-TRIPLE")){
				response.setHeader("Content-Disposition", "attachment; filename=annotated.nt");
			}
			else if(serialization.equalsIgnoreCase("TURTLE")){
				response.setHeader("Content-Disposition", "attachment; filename=annotated.ttl");
			}
			else if(serialization.equalsIgnoreCase("N3")){
				response.setHeader("Content-Disposition", "attachment; filename=annotated.n3");
			}
			StringReader sr = new StringReader(rdfa_content);
			try{
				Class.forName("net.rootdev.javardfa.RDFaReader");
			}
			catch(ClassNotFoundException cnfe){
				cnfe.printStackTrace();
			}
			rdfa_content_model.read(sr, base, "XHTML");		
			rdfa_content_model.write(op, serialization);
			op.flush();
			op.close();
		}
	}
}
