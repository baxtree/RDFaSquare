package baxtree.apis;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import RDFaAnnotator.main.RDFaAnnotator;

public class SingleCotextTransformer extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		String topic_uris = request.getParameter("topicuris");
		topic_uris = topic_uris.substring(0, topic_uris.length()-16);
		String[] topicuris = topic_uris.split(",rdfa2delimiter,");
		String rdf_url = request.getParameter("rdfurl");
		String type = request.getParameter("type");
		RDFaAnnotator rdfaa = new RDFaAnnotator(rdf_url, "");
		String xhtmlrdfa = "";
		for(String topicuri : topicuris){
			rdfaa.setCurrentTopicURI(topicuri);
			rdfaa.createMapTreeForTopicURIFromContext();
			xhtmlrdfa += rdfaa.generateRDFa("");
		}
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
//		Long start = System.currentTimeMillis();
		out.print(rdfaa.decorateRDFa(rdfaa.getPrefixes(), xhtmlrdfa, type));
//		Long end = System.currentTimeMillis();
//		System.out.print(end-start);
	}
}
