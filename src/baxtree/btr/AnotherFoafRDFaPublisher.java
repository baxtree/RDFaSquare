package baxtree.btr;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class AnotherFoafRDFaPublisher {

	private String topic_uri;
	private PrintWriter out;
	
	public AnotherFoafRDFaPublisher(PrintWriter out){
		this.topic_uri = "";
		this.out = out;
	}
	
	public void generateRDFa(String topic_uri, String foaf_url){
		this.topic_uri = topic_uri;
		Model model = ModelFactory.createOntologyModel();
		try{
			URL url = new URL(foaf_url);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			InputStream is = con.getInputStream();
//			FileInputStream fis = new FileInputStream(new File("myfoaf.rdf"));
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			model.read(isr, this.topic_uri, null);
//			model.write(new PrintWriter(System.out));
			String querystr = 	"	PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
							"	PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
							"	SELECT ?p ?o" +
							"	WHERE {" +
							"		<"+this.topic_uri+"> ?p	?o." +
							"	}" +
							"	ORDER BY ?p";
			Query query = QueryFactory.create(querystr);
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			ResultSet results = qe.execSelect();
			qe.close();
			Map root = new HashMap();
			Map personal = new HashMap();
			Map knows = new HashMap();
			root.put("personal", personal);
			personal.put("topicuri", this.topic_uri);
			root.put("knows", knows);
			int id = 0;
			for(;results.hasNext();){
				QuerySolution qs = results.nextSolution();
				Resource property = (Resource) qs.get("p");
				RDFNode object = qs.get("o");
				if(!property.getURI().equalsIgnoreCase("http://xmlns.com/foaf/0.1/knows")){
					if(object.isLiteral()){
						Literal literal = (Literal) object;
						personal.put(property.getLocalName(), literal.getValue().toString());
					}
					else if(object.isResource()){
						Resource resource = (Resource) object;
						personal.put(property.getLocalName(), resource.getURI());
					}
				}
				else{
					Resource resource = (Resource) object;
					Map person = new HashMap();
					person.put("knoweduri", resource.getURI());
					knows.put("person_"+id, person);
					String querystr2 = "SELECT ?o ?p" +
							"			WHERE {" +
							"			<" + resource.getURI() +">	?p ?o" +
							"}";
					Query query2 = QueryFactory.create(querystr2);
					QueryExecution qe2 = QueryExecutionFactory.create(query2, model);
					ResultSet rs2 = qe2.execSelect();
					qe2.close();
					for(;rs2.hasNext();){
						QuerySolution qs2 = rs2.nextSolution();
						Resource property2 = (Resource) qs2.get("p");
						RDFNode object2 = qs2.get("o");
						if(!property2.getURI().equalsIgnoreCase("http://xmlns.com/foaf/0.1/knows")){
							if(object2.isLiteral()){
								Literal literal2 = (Literal) object2;
								person.put(property2.getLocalName(), literal2.getValue().toString());
							}
							else if(object2.isResource()){
								Resource resource2 = (Resource) object2;
								person.put(property2.getLocalName(), resource2.getURI());
							}
						}
					}
				}
				id++;
			}
			try {
				Configuration cfg = new Configuration();
				cfg.setObjectWrapper(new DefaultObjectWrapper());
				cfg.setDefaultEncoding("UTF-8");
				cfg.setClassForTemplateLoading(this.getClass(), "/template");
				Template temp = cfg.getTemplate("annotherFoafInRDFa.ftl");
//				Writer out = new OutputStreamWriter(System.out);
//				System.out.println(root);
				temp.process(root, this.out);
				this.out.flush();
			} catch (TemplateException te) {
				te.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		} catch (MalformedURLException murle){
			murle.printStackTrace();
		}
		catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		AnotherFoafRDFaPublisher mfrdfap = new AnotherFoafRDFaPublisher(new PrintWriter(System.out));
		mfrdfap.generateRDFa("http://richard.cyganiak.de/foaf.rdf#cygri", "http://richard.cyganiak.de/foaf.rdf"); 
	}
}
