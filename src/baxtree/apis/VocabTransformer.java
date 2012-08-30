package baxtree.apis;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import RDFaAnnotator.main.FederatedRDFaAnnotator;
import RDFaAnnotator.main.VocabRDFaAnnotator;

public class VocabTransformer extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		String rdf_url = request.getParameter("rdfurl");
		String type = request.getParameter("type");
		VocabRDFaAnnotator vrdfaa = new VocabRDFaAnnotator(rdf_url);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
//		Long start = System.currentTimeMillis();
		out.print(vrdfaa.gernerateRDFa(type));
//		Long end = System.currentTimeMillis();
//		System.out.print(end-start);
	}
}
