package baxtree.apis;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import RDFaAnnotator.main.FederatedRDFaAnnotator;
import baxtree.btr.MyFunctions;

public class SmartTransformer extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		PrintWriter out = response.getWriter();
		try{
			String rdf_url = request.getParameter("rdfurl");
			FederatedRDFaAnnotator fa = new FederatedRDFaAnnotator();
			fa.addContextAndTopic(rdf_url, "");
			response.setCharacterEncoding("UTF-8");
			out.print(MyFunctions.cleanBeforeExporting(fa.generateRDFa("complete")));
		}
		catch(RuntimeException re){
			out.print("ERROR: RDFa<sup>2</sup> can not glean any triples from this document. <br/>");
			out.print("Please make sure the document is eithor a valid RDF or RDFa format.");
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		doGet(request, response);
	}
}
