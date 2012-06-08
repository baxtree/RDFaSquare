package baxtree.apis;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import baxtree.btr.StrongestFoafRDFaPublisher;

public class SmartFoafTransformer {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
//		String topic_uri = request.getParameter("topic");
		String foaf_url = request.getParameter("foaf");
//		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		StrongestFoafRDFaPublisher publisher = new StrongestFoafRDFaPublisher(out);
		publisher.generateRDFa(foaf_url);
		//out.println("cool");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		doGet(request, response);
	}
}
