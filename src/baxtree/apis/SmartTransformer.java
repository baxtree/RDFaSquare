package baxtree.apis;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import RDFaAnnotator.main.FederatedRDFaAnnotator;
import RDFaAnnotator.main.RDFaAnnotator;

public class SmartTransformer extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		String rdf_url = request.getParameter("rdfurl");
		FederatedRDFaAnnotator fa = new FederatedRDFaAnnotator();
		fa.addContextAndTopic(rdf_url, "");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(fa.generateRDFa("complete"));
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		doGet(request, response);
	}
}
