package baxtree.apis;

import java.io.IOException;
import java.io.StringReader;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import baxtree.btr.MyFunctions;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import de.dfki.km.json.jsonld.impl.JenaJSONLDSerializer;

public class TriplesGleaner extends HttpServlet {

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
			rdfa_content = MyFunctions.cleanBeforeExporting(rdfa_content);
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
			else if(serialization.equalsIgnoreCase("JSON-LD")){
				response.setHeader("Content-Disposition", "attachment; filename=annotated.jsonld");
			}
			StringReader sr = new StringReader(rdfa_content);
			try{
				Class.forName("net.rootdev.javardfa.RDFaReader");
			}
			catch(ClassNotFoundException cnfe){
				cnfe.printStackTrace();
			}
			rdfa_content_model.read(sr, base, "XHTML");	
			if(serialization.equalsIgnoreCase("JSON-LD")){
				JenaJSONLDSerializer serializer = new JenaJSONLDSerializer();
				serializer.importModel(rdfa_content_model);
				String jsonldStr = serializer.asString();;
				op.write(jsonldStr.getBytes());
			}
			else{
				rdfa_content_model.write(op, serialization);
			}
			op.flush();
			op.close();
		}
	}
}
