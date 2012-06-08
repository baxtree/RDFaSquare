package baxtree.apis;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import RDFaAnnotator.main.TemplateGenerater;
import baxtree.btr.StrongestFoafRDFaPublisher;

public class TemplateCreator extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		String topic_uri = request.getParameter("topicuri");
		String rdf_url = request.getParameter("rdfurl");
		TemplateGenerater tg = new TemplateGenerater(rdf_url, topic_uri);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(tg.createTemplate(""));
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		doGet(request, response);
	}
}
