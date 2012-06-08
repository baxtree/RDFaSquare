package baxtree.btr;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.arp.JenaReader;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sparql.algebra.Algebra;
import com.hp.hpl.jena.sparql.algebra.Op;
import com.hp.hpl.jena.sparql.algebra.OpWalker;
import com.hp.hpl.jena.sparql.core.BasicPattern;
import com.hp.hpl.jena.sparql.engine.optimizer.core.BasicPatternVisitor;
import com.hp.hpl.jena.sparql.syntax.Element;

public class MySPARQLParser {
	public static Model loadTriplesFromURL(String rdf_url) {
		Model model = ModelFactory.createDefaultModel();
		try {
			URL url = new URL(rdf_url);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("Accept", "application/rdf+xml");
			con.connect();
			// String accept = con.getRequestProperty("Accept");
			InputStream is = con.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			// if(accept.equalsIgnoreCase("application/rdf+xml")){
			// con.setRequestProperty("Accept", "application/rdf+xml");
			try {
				Class.forName("net.rootdev.javardfa.RDFaReader");
				model.read(isr, "XHTML");
			} catch (ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			}
			if (model.isEmpty()) {
				try {
					Class.forName("com.hp.hpl.jena.rdf.arp.JenaReader");
				} catch (ClassNotFoundException cnfe) {
					cnfe.printStackTrace();
				}
				JenaReader jreader = new JenaReader();
				jreader.read(model, rdf_url);
//				FileManager fm = new FileManager();
//				model = fm.loadModel(rdf_url, "RDF/XML");
			}
//			model.write(new PrintWriter(System.out), "RDF/XML");
			return model;
		} catch (MalformedURLException murle) {
			murle.printStackTrace();
			return null;
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
			return null;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args){
		MySPARQLParser msp = new MySPARQLParser();
		String querystr = 	
			"	PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
			"	SELECT ?name ?o" +
			"	{" +
			"		?s foaf:name ?name." +
			"		?s foaf:knows ?o." +
			"	}" +
			"	ORDER BY ?s";
			Query query = QueryFactory.create(querystr);
			BasicPatternVisitor visitor = new BasicPatternVisitor() ;
			Element el = query.getQueryPattern() ;
			Op op = Algebra.compile(el) ;
			OpWalker.walk(op, visitor) ;
			List patterns = visitor.getPatterns() ;
			int count = 0;
			for (Iterator iter = patterns.iterator(); iter.hasNext();){
				BasicPattern pattern = (BasicPattern)iter.next() ;
				for(Iterator iter2 = pattern.iterator(); iter2.hasNext();){
					Triple triple = (Triple) iter2.next();
					System.out.println("subject: "+triple.getSubject());
					System.out.println("predicate: "+triple.getPredicate().toString(query.getPrefixMapping()));
					System.out.println("object: "+triple.getObject().toString(query.getPrefixMapping()));
				}
			}
			
			QueryExecution qe = QueryExecutionFactory.create(query, msp.loadTriplesFromURL("http://www.w3.org/People/Berners-Lee/card"));
			ResultSet results = qe.execSelect();
			qe.close();	
		while(results.hasNext()){
			QuerySolution qs = results.nextSolution();
			Iterator vnames = qs.varNames();
			while(vnames.hasNext()){
				String vname = (String)vnames.next();
				System.out.print(vname + ", ");
			}
			System.out.println();
		}
		
	}
}
