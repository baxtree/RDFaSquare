package baxtree.apis;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import RDFaAnnotator.main.FederatedRDFaAnnotator;
import RDFaAnnotator.main.RDFaAnnotator;

public class TemplateApplier extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		String template = request.getParameter("template");
		String topic_uri = request.getParameter("topic");
		String rdf_url = request.getParameter("rdfurl");
		RDFaAnnotator rdfaa = new RDFaAnnotator(rdf_url, topic_uri);
		rdfaa.createMapTreeForTopicURIFromContext();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		FederatedRDFaAnnotator fa = new FederatedRDFaAnnotator();
		fa.addContextAndTopic(rdf_url, topic_uri);
		out.println(fa.generateRDFa("complete", template));
	}
}
