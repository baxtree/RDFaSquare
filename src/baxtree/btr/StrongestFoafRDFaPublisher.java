package baxtree.btr;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class StrongestFoafRDFaPublisher {

	private String topic_uri;
	private PrintWriter out;
	
	public StrongestFoafRDFaPublisher(PrintWriter out){
		this.topic_uri = "";
		this.out = out;
	}
	
	public void generateRDFa(String topic_uri, String foaf_url){
		
		Model model = ModelFactory.createOntologyModel();
		
		Map root = new HashMap();
		Map personal = new HashMap();
		root.put("personal", personal);
		
		Map root_errormsg = new HashMap();
		Map errs = new HashMap();
		root_errormsg.put("errormsg", errs);
		try{
			URL url = new URL(foaf_url);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestProperty("Accept", "application/rdf+xml");
			InputStream is = con.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			model.read(isr, foaf_url, null);
//			model.write(new PrintWriter(System.out));
			this.topic_uri = topic_uri;
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
			
			personal.put("topicuri", this.topic_uri);
			
			
			int count = 0;
			String preprouri = "";
			for(;results.hasNext();){
				QuerySolution qs = results.nextSolution();
				Resource property = (Resource) qs.get("p");
				RDFNode object = qs.get("o");
				Map property_node;
				if(!property.getURI().equalsIgnoreCase("http://xmlns.com/foaf/0.1/knows")){
					if(!property.getURI().equalsIgnoreCase(preprouri)){
						property_node = new HashMap();
						personal.put(property.getLocalName(), property_node);
						preprouri = property.getURI();
						count = 0;
					}
					else{
						property_node = (Map) personal.get(property.getLocalName());
						count++;
					}
					Map multivalues = new HashMap();
					property_node.put("value_"+count, multivalues);
					if(object.isLiteral()){
						Literal literal = (Literal) object;
						multivalues.put("val", literal.getValue().toString());
					}
					else if(object.isResource()){
						Resource resource = (Resource) object;
						multivalues.put("uri", resource.getURI());
					}
				}
				else{
					continue;
				}
				
			}
			
			Map knows = new HashMap();
			root.put("knows", knows);
			
			String querystr2 = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
			"			SELECT ?person ?p ?o" +
			"			{" +
			"				<" + this.topic_uri +">	foaf:knows ?person." +
			"				?person ?p ?o." +
			"			}" +
			"			ORDER BY ?person ?p" ;
			Query query2 = QueryFactory.create(querystr2);
			QueryExecution qe2 = QueryExecutionFactory.create(query2, model);
			ResultSet rs2 = qe2.execSelect();
			qe2.close();
			int count2 = 0;
			String preprouri2 = "";
			ArrayList<String> person_uris = new ArrayList<String>();
			Map person;
			Map person_uri_id = new HashMap();
			int id = 0;
			for(;rs2.hasNext();){
				QuerySolution qs2 = rs2.nextSolution();
				Resource subject2 = qs2.getResource("person");
				Resource property2 = (Resource) qs2.get("p");
				RDFNode object2 = qs2.get("o");
				if(!property2.getURI().equalsIgnoreCase("http://xmlns.com/foaf/0.1/knows")){
					String person_uri = "";
					if(subject2.isURIResource()){
						person_uri = subject2.getURI();
					}
					else if(subject2.isAnon()){
						person_uri = subject2.getId().getLabelString();
					}
					Map property_node2;
					if(!person_uri_id.containsKey(person_uri)){
						person = new HashMap();
						knows.put("person_"+id, person);
						if(subject2.isURIResource())
							person.put("knoweduri", subject2.getURI());
						person_uri_id.put(person_uri, id);
						id++;
						preprouri2 = "";
					}
					else{
						person = (Map) knows.get("person_"+person_uri_id.get(person_uri));
					}
					property_node2 = new HashMap();
					if(!property2.getURI().equalsIgnoreCase(preprouri2)){
						property_node2 = new HashMap();
						person.put(property2.getLocalName(), property_node2);
						preprouri2 = property2.getURI();
						count2 = 0;
					}
					else{
						property_node2 = (Map) person.get(property2.getLocalName());
						count2++;
					}
					Map multivalues = new HashMap();
					property_node2.put("value_"+count2, multivalues);
					if(object2.isLiteral()){
						Literal literal2 = (Literal) object2;
						multivalues.put("val", literal2.getValue().toString());
					}
					else if(object2.isResource()){
						Resource resource2 = (Resource) object2;
						multivalues.put("uri", resource2.getURI());
					}
				}
			}
			transform(root, this.out);
		}
		catch (MalformedURLException murle){
			murle.printStackTrace();
			
			Map err = new HashMap();
			errs.put("murle", err);
			err.put("msg", murle.getMessage());
			transform(root_errormsg, this.out);
		}
		catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
			
			Map err = new HashMap();
			errs.put("fnfe", err);
			err.put("msg", fnfe.getMessage());
			transform(root_errormsg, this.out);
		}
		catch(IOException ioe){
			ioe.printStackTrace();
			
			Map err = new HashMap();
			errs.put("ioe", err);
			err.put("msg", ioe.getMessage());
			transform(root_errormsg, this.out);
		}
	}
	
	public void generateRDFa(String foaf_url){
		try{
			Model model = ModelFactory.createOntologyModel();
			URL url = new URL(foaf_url);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestProperty("Accept", "application/rdf+xml");
			InputStream is = con.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			model.read(isr, foaf_url, null);
			NodeStatist ns = new NodeStatist(model);
			this.topic_uri= ImportanceCalculator.getUriWithLargestImportance(ns.getUri_occurrence_in_sub(), ns.getUri_occurrence_in_obj());
	//		ImportanceCalculator.compare(ImportanceCalculator.getUriImportanceMap(ns.getUri_occurrence_in_sub(), ns.getUri_occurrence_in_obj()));
			generateRDFa(this.topic_uri, foaf_url);
		}
		catch (MalformedURLException murle){
			murle.printStackTrace();
		}
		catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public void transform(Map root, PrintWriter pw){
		try{
			Configuration cfg = new Configuration();
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			cfg.setEncoding(cfg.getLocale(), "UTF-8");
			cfg.setClassForTemplateLoading(this.getClass(), "/template");
			Template temp = cfg.getTemplate("StrongestFoafInRDFa.ftl");
			temp.process(root, pw);
			out.flush();
		} catch (TemplateException te) {
			te.printStackTrace();
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	
	public static void main(String[] args){
		StrongestFoafRDFaPublisher mfrdfap = new StrongestFoafRDFaPublisher(new PrintWriter(System.out));
		mfrdfap.generateRDFa("http://richard.cyganiak.de/foaf.rdf");
	}
}
