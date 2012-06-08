package RDFaAnnotator.main;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import baxtree.btr.ImportanceCalculator;
import baxtree.btr.LabelFinder;
import baxtree.btr.MyFunctions;
import baxtree.btr.NodeStatist;
import baxtree.btr.URINormalizer;

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

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class RDFaAnnotator {

	protected String topic_uri;
	protected String rdf_url;
	protected String endpoint_url;
	protected String endpoint_name;
	private Model model;
	protected Map<String, Map> root;
	protected Map<String, String> prefixes;
	
	public RDFaAnnotator(String endpoint_url, String topic_uri, String endpoint_name){
		this.topic_uri = topic_uri;
		this.endpoint_url = endpoint_url;
		this.endpoint_name = endpoint_name;
		prefixes = new HashMap<String, String>();
		root = new HashMap<String, Map>();
	}
	
	public RDFaAnnotator(String rdf_url, String topic_uri){
		this.topic_uri = topic_uri;
		model = ModelFactory.createDefaultModel();
		this.rdf_url = rdf_url;
		loadTriples();
		prefixes = new HashMap<String, String>();
		root = new HashMap<String, Map>();
	}
	
	public RDFaAnnotator(String rdf_url){
		model = ModelFactory.createDefaultModel();
		this.rdf_url = rdf_url;
		loadTriples();
		NodeStatist ns = new NodeStatist(this.model);
		this.topic_uri = ImportanceCalculator.getTopNUriWithLargestImportance(1, ns.getUri_occurrence_in_sub(), ns.getUri_occurrence_in_obj(), 0.5).get(0);
		prefixes = new HashMap<String, String>();
		root = new HashMap<String, Map>();
	}
	
	public RDFaAnnotator(Model model){
		this.model = model;
		NodeStatist ns = new NodeStatist(this.model);
		prefixes = new HashMap<String, String>();
		root = new HashMap<String, Map>();
	}
	
	public Map getPrefixes(){
		return this.prefixes;
	}
	
	public void setCurrentTopicURI(String topic_uri){
		this.topic_uri = topic_uri;
	}
	
	public String getCurrentTopicURI(){
		return this.topic_uri;
	}
	
	public void setCurrentContextURL(String rdf_url){
		this.rdf_url = rdf_url;
	}
	
	public String getCurrentContextURL(){
		return this.rdf_url;
	}

	public void loadTriples(){
		if(RDFModelLoader.isValidURL(rdf_url))
			this.model = RDFModelLoader.loadTriplesFromURL(this.rdf_url);
		else
		{
//			System.out.println(this.rdf_url);
			this.model = RDFModelLoader.loadTriplesFromString(this.rdf_url);
		}
	}
	
	public void createMapTreeForTopicURIFromContext(){
		
		// topic ?p ?o
		String querystr = 	
		"	SELECT ?p ?o" +
		"	{" +
		"		<"+this.topic_uri+"> ?p	?o." +
		"	}" +
		"	ORDER BY ?p";
		Query query = QueryFactory.create(querystr);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		qe.close();	
		
		Map topic = new HashMap();
		root.put("topic", topic);
		topic.put("topicuri", URINormalizer.normalize(this.topic_uri));
//		try{
//			topic.put("topicuri", URIEncoder.encode(this.topic_uri, "UTF-8"));
//		}
//		catch(UnsupportedEncodingException uee){
//			uee.printStackTrace();
//		}
		int count = 0;
		String preprouri = "";
//		Resource subject = model.getResource(this.topic_uri);
//		if(model.getNsURIPrefix(subject.getNameSpace())!=null)
//			prefixes.put(model.getNsURIPrefix(subject.getNameSpace()), subject.getNameSpace());
		for(;results.hasNext();){
			QuerySolution qs = results.nextSolution();
			Resource property = (Resource) qs.get("p");
			RDFNode object = qs.get("o");
			if(object.isAnon())
				continue;
			Map<String, Map> property_node;
			String pro_node_name = (model.getNsURIPrefix(property.getNameSpace())+"_"+property.getLocalName() + "rel").replace("-", "$dash$");
			if(!property.getURI().equalsIgnoreCase(preprouri)){
				property_node = new HashMap<String, Map>();
				topic.put(pro_node_name, property_node);
				preprouri = property.getURI();
				count = 0;
				if(!prefixes.containsKey(model.getNsURIPrefix(property.getNameSpace()))){
					prefixes.put(model.getNsURIPrefix(property.getNameSpace()), property.getNameSpace());
				}
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
//				try{
//					multivalues.put("uri", URLEncoder.encode(resource.getURI(), "UTF-8"));
//				}
//				catch(UnsupportedEncodingException uee){
//					uee.printStackTrace();
//				}
			}
			/*@TODO need test
			else if(object.isAnon()){
				Resource obj = (Resource) object;
				String alt = BNodeAltFinder.getAltForBlankNode(obj, model);
				if(alt.indexOf(";;") != -1){
					String[] altele = alt.split(";;");
					multivalues.put("bnojbpro", URINormalizer.normalize(altele[0]));
					multivalues.put("bnuri", URINormalizer.normalize(altele[1]));
				}
				else if(alt.indexOf("::") != -1){
					String[] altele = alt.split("::");
					multivalues.put("bndatpro", URINormalizer.normalize(altele[0]));
					multivalues.put("bnlex", altele[1]);
					if(altele[2] != null && !altele[2].equals("")){
						multivalues.put("bndat", altele[2]);
					}
					if(altele[3] != null && !altele[3].equals("")){
						multivalues.put("bnlan", altele[3]);
					}
				}
					
			}
			*/
		}
		
		
		//?s ?p topic			copied and pasted codes here and it needs refactoring
		String querystr2 = 	
		"	SELECT ?s ?p" +
		"	{" +
		"		?s ?p <"+this.topic_uri+">." +
		"	}" +
		"	ORDER BY ?p";
		Query query2 = QueryFactory.create(querystr2);
		QueryExecution qe2 = QueryExecutionFactory.create(query2, model);
		ResultSet results2 = qe2.execSelect();
		qe2.close();	
		
		count = 0;
		preprouri = "";
		for(;results2.hasNext();){
			QuerySolution qs = results2.nextSolution();
			Resource subject = (Resource) qs.get("s");
			Resource property = (Resource) qs.get("p");
			if(subject.isAnon())
				continue;
			Map<String, Map> property_node;
			String pro_node_name = (model.getNsURIPrefix(property.getNameSpace())+"_"+property.getLocalName() + "rev").replace("-", "$dash$");
			if(!property.getURI().equalsIgnoreCase(preprouri)){
				property_node = new HashMap<String, Map>();
				topic.put(pro_node_name, property_node);
				preprouri = property.getURI();
				count = 0;
				if(!prefixes.containsKey(model.getNsURIPrefix(property.getNameSpace()))){
					prefixes.put(model.getNsURIPrefix(property.getNameSpace()), property.getNameSpace());
				}
			}
			else{
				property_node = (Map) topic.get(pro_node_name);
				count++;
			}
			Map<String, String> multivalues = new HashMap<String, String>();
			if(property_node == null){continue;}// needs to be deleted.
			property_node.put("value_"+count, multivalues);
			multivalues.put("uri", URINormalizer.normalize(subject.getURI()));
//			try{
//				multivalues.put("uri", URLEncoder.encode(subject.getURI(), "UTF-8"));
//			}
//			catch(UnsupportedEncodingException uee){
//				uee.printStackTrace();
//			}
		}
//		System.out.println(root);
	}
	
	public void createMapTreeForTopicURIFromContext(String current_topic_uri){
		this.topic_uri = current_topic_uri;
		createMapTreeForTopicURIFromContext();
	}
	
	public String generateTemplateFromContext(String template_name){
		TemplateGenerater itg = new TemplateGenerater(this.model, this.topic_uri);
		return itg.createTemplateFromContext(template_name);
	}
	
	public String generateRDFa(String template_name){
		try{
			Configuration cfg = new Configuration();
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			cfg.setEncoding(cfg.getLocale(), "UTF-8");
//			cfg.setClassForTemplateLoading(this.getClass(), "/template");
			StringTemplateLoader stl = new StringTemplateLoader();
//			System.out.println(generateTemplate(template_name));
			stl.putTemplate(template_name, generateTemplateFromContext(template_name));
//			System.out.print(stl.findTemplateSource(template_name));
			cfg.setTemplateLoader(stl);
			Template temp = cfg.getTemplate(template_name);
			StringWriter sw = new StringWriter();
			temp.process(root, sw);
			String rdfa = "";
			if(RDFModelLoader.isValidURL(this.rdf_url))
				rdfa = LabelFinder.applyLabelToResource(this.model, sw.toString()) + voiDGenerater.getvoiDDIV(this.topic_uri, this.rdf_url)+ "\r\n\r\n";
			else 
				rdfa = LabelFinder.applyLabelToResource(this.model, sw.toString()) + "\r\n\r\n";
			return rdfa;
		} catch (TemplateException te) {
			te.printStackTrace();
			return null;
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}
	}
	
	public String applyTemplate(String template_name, String template){
		try{
			Configuration cfg = new Configuration();
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			cfg.setEncoding(cfg.getLocale(), "UTF-8");
//			cfg.setClassForTemplateLoading(this.getClass(), "/template");
			StringTemplateLoader stl = new StringTemplateLoader();
			stl.putTemplate(template_name, template);
//			System.out.print(stl.findTemplateSource(template_name));
			cfg.setTemplateLoader(stl);
			Template temp = cfg.getTemplate(template_name);
			StringWriter sw = new StringWriter();
			temp.process(root, sw);
//			System.out.println(sw.toString());
			String rdfa = "";
			if(RDFModelLoader.isValidURL(this.rdf_url))
				rdfa = LabelFinder.applyLabelToResource(this.model, sw.toString()) + voiDGenerater.getvoiDDIV(this.topic_uri, this.rdf_url) + "\r\n\r\n";
			else
				rdfa = LabelFinder.applyLabelToResource(this.model, sw.toString()) + "\r\n\r\n";
			return rdfa;
		} catch (TemplateException te) {
			te.printStackTrace();
			return null;
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}
	}
	
	public static String decorateRDFa(Map<String, String> prefixes, String body_content, String type){
		String template_str = "";
		if(type.equalsIgnoreCase("complete")){
			template_str = MyFunctions.DOCTYPE + "\r\n" + MyFunctions.DEFAULT_HTML_ATTRIBUTES +"\r\n";
			Set<String> pres = prefixes.keySet();
			String temp = "";
			for(String prefix : pres){
				temp += "xmlns:"+prefix + "=\"" + prefixes.get(prefix) + "\"\r\n"; 
			}
			template_str += temp + ">\r\n";
			template_str += "<head>\r\n"+MyFunctions.DEFAULT_META+MyFunctions.UI+"\r\n<title>change the title here ... </title>\r\n</head>\r\n<body>\r\n";
			template_str += body_content;
			template_str += "</body>\r\n</html>";
			return template_str;
		}
		else if(type.equalsIgnoreCase("partial")){
			template_str += "<div ";
			Set<String> pres = prefixes.keySet();
			String temp = "";
			for(String prefix : pres){
				temp += "xmlns:"+prefix + "=\"" + prefixes.get(prefix) + "\"\r\n"; 
			}
			template_str += temp + ">\r\n";
			template_str += body_content;
			template_str += "</div>";
			template_str = template_str.replaceAll(">[^<]+?<", "><");
			template_str = template_str.replaceAll("<img ", "<span ").replaceAll(" src=\"", " resource=\"").replaceAll(" alt=\"\" width=\"100px\">", ">").replaceAll("</img>", "</span>");
			template_str = MyFunctions.cleanBeforeExporting(template_str);
			return template_str;
		}
		else{
			return null;
		}
	}
	
	public static void main(String [] args){
		RDFaAnnotator rdfaa = new RDFaAnnotator("http://www.w3.org/TR/owl-guide/wine.rdf", "http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#Wine");
		rdfaa.createMapTreeForTopicURIFromContext();
		rdfaa.generateRDFa("");
	}
}
