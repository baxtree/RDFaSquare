package RDFaAnnotator.main;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import baxtree.btr.LabelFinder;
import baxtree.btr.URINormalizer;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class RDFaAnnotatorForService extends RDFaAnnotator {
	
	public RDFaAnnotatorForService(String endpoint_url, String topic_uri, String endpoint_name){
		super(endpoint_url, topic_uri, endpoint_name);
		this.topic_uri = topic_uri;
		this.endpoint_url = endpoint_url;
		prefixes = new HashMap<String, String>();
		root = new HashMap<String, Map>();
	}
	
	public void createMapTreeForTopicURIFromEndpoint(){
		Map topic = new HashMap();
		root.put("topic", topic);
		topic.put("topicuri", URINormalizer.normalize(this.topic_uri));
		String querystr = 	
			"	SELECT ?p ?o" +
			"	{" +
			"		<"+this.topic_uri+"> ?p	?o." +
			"	}" +
			"	ORDER BY ?p";
		Query query = QueryFactory.create(querystr);
		QueryExecution qe = QueryExecutionFactory.sparqlService(this.endpoint_url, query);
		ResultSet results = qe.execSelect();
		qe.close();	
		createSubjectTopicTree(topic, results);
		
		String querystr2 = 	
			"	SELECT ?s ?p" +
			"	{" +
			"		?s ?p <"+this.topic_uri+">." +
			"	}" +
			"	ORDER BY ?p";
		Query query2 = QueryFactory.create(querystr2);
		QueryExecution qe2 = QueryExecutionFactory.sparqlService(this.endpoint_url, query2);
		ResultSet results2 = qe2.execSelect();
		qe2.close();	
		createObjectTopicTree(topic, results2);
	}
	
	public void createSubjectTopicTree(Map topic, ResultSet results){
		int count = 0;
		String preprouri = "";
		for(;results.hasNext();){
			QuerySolution qs = results.nextSolution();
			Resource property = (Resource) qs.get("p");
			RDFNode object = qs.get("o");
			if(object.isAnon())
				continue;
			Map<String, Map> property_node;
			String pro_node_name = (property.getURI() + "rel").replace("-", "$dash$");
			if(!property.getURI().equalsIgnoreCase(preprouri)){
				property_node = new HashMap<String, Map>();
				topic.put(pro_node_name, property_node);
				preprouri = property.getURI();
				count = 0;
//				if(!prefixes.containsKey(model.getNsURIPrefix(property.getNameSpace()))){
//					prefixes.put(model.getNsURIPrefix(property.getNameSpace()), property.getNameSpace());
//				}
			}
			else{
				property_node = (Map) topic.get(pro_node_name);
				count++;
			}
			Map<String, String> multivalues = new HashMap<String, String>();
			property_node.put("value_"+count, multivalues);
			if(object.isLiteral()){
				Literal literal = (Literal) object;
				multivalues.put("val", literal.getLexicalForm());
				if(literal.getDatatypeURI() != null && !literal.getDatatypeURI().equals(""))
					multivalues.put("dat", literal.getDatatypeURI());
				if(literal.getLanguage() != null && !literal.getLanguage().equals(""))
					multivalues.put("lan", literal.getLanguage());
			}
			else if(object.isResource()){
				Resource resource = (Resource) object;
				multivalues.put("uri", URINormalizer.normalize(resource.getURI()));
			}
		}
	}
	
	public void createObjectTopicTree(Map topic, ResultSet results){
		int count = 0;
		String preprouri = "";
		for(;results.hasNext();){
			QuerySolution qs = results.nextSolution();
			Resource subject = (Resource) qs.get("s");
			Resource property = (Resource) qs.get("p");
			if(subject.isAnon())
				continue;
			Map<String, Map> property_node;
			String pro_node_name = (property.getURI() + "rev").replace("-", "$dash$");
			if(!property.getURI().equalsIgnoreCase(preprouri)){
				property_node = new HashMap<String, Map>();
				topic.put(pro_node_name, property_node);
				preprouri = property.getURI();
				count = 0;
//				if(!prefixes.containsKey(model.getNsURIPrefix(property.getNameSpace()))){
//					prefixes.put(model.getNsURIPrefix(property.getNameSpace()), property.getNameSpace());
//				}
			}
			else{
				property_node = (Map) topic.get(pro_node_name);
				count++;
			}
			Map<String, String> multivalues = new HashMap<String, String>();
			if(property_node == null){continue;}// needs to be deleted.
			property_node.put("value_"+count, multivalues);
			multivalues.put("uri", URINormalizer.normalize(subject.getURI()));
		}
	}
	
	public String generateTemplateFromEndpoint(String endpoint_url, String template_name){
		TemplateGeneraterForService itg = new TemplateGeneraterForService(this.endpoint_url, this.topic_uri, this.endpoint_name);
		return itg.createTemplateFromEndpoint(endpoint_url, template_name);
	}
	
	public String generateRDFaFromEndpoint(String endpoint_url, String template_name){
		try{
			Configuration cfg = new Configuration();
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			cfg.setEncoding(cfg.getLocale(), "UTF-8");
//			cfg.setClassForTemplateLoading(this.getClass(), "/template");
			StringTemplateLoader stl = new StringTemplateLoader();
//			System.out.println(generateTemplate(template_name));
			stl.putTemplate(template_name, generateTemplateFromEndpoint(endpoint_url, template_name));
//			System.out.print(stl.findTemplateSource(template_name));
			cfg.setTemplateLoader(stl);
			Template temp = cfg.getTemplate(template_name);
			StringWriter sw = new StringWriter();
			temp.process(root, sw);
			String rdfa = "";
			if(RDFModelLoader.isValidURL(this.rdf_url))
				rdfa = LabelFinder.applyLabelToResource(this.endpoint_url, sw.toString()) + voiDGenerater.getvoiDDIV(this.topic_uri, this.rdf_url)+ "\r\n\r\n";
			else 
				rdfa = LabelFinder.applyLabelToResource(this.endpoint_url, sw.toString()) + "\r\n\r\n";
			return rdfa;
		} catch (TemplateException te) {
			te.printStackTrace();
			return null;
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}
	}
}
