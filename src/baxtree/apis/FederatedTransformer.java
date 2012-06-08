package baxtree.apis;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import RDFaAnnotator.main.FederatedRDFaAnnotator;
import baxtree.btr.StrongestFoafRDFaPublisher;

public class FederatedTransformer extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		String topic_uris = request.getParameter("topicuris");
		String rdf_urls = request.getParameter("rdfurls");
		String type = request.getParameter("type");
		String[] topics = topic_uris.substring(0, topic_uris.length()-1).split(",");
		String[] rdfurls = rdf_urls.substring(0, rdf_urls.length()-1).split(",");
		FederatedRDFaAnnotator fa = new FederatedRDFaAnnotator();
		for(int i = 0; i < topics.length; i++){
			fa.addContextAndTopic(rdfurls[i], topics[i]);
		}
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Long start = System.currentTimeMillis();
		if(type.equalsIgnoreCase("complete"))
			out.print(fa.generateRDFa("complete"));
		else if(type.equalsIgnoreCase("partial"))
			out.print(fa.generateRDFa("partial"));
		Long end = System.currentTimeMillis();
		System.out.print(end-start);
	}
}