package RDFaAnnotator.main;

import java.util.ArrayList;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

public class VocabRDFaAnnotator {
	
	private Model model;
	private ArrayList<String> classes;
	private ArrayList<String> properties;
	private String vocab_url;
	
	public VocabRDFaAnnotator(String vocab_url){
		this.vocab_url = vocab_url;
		this.classes = new ArrayList<String>();
		this.properties = new ArrayList<String>();
		this.model = ModelFactory.createDefaultModel();
		if(RDFModelLoader.isValidURL(vocab_url))
			this.model = RDFModelLoader.loadTriplesFromURL(vocab_url);
		else
			this.model = RDFModelLoader.loadTriplesFromString(vocab_url);
	}

	public ArrayList<String> getVocabClasses(){
		return this.classes;
	}
	
	public ArrayList<String> getVocabProperties(){
		return this.properties;
	}
	
	public String gernerateRDFa(String type){
		long begin = System.currentTimeMillis();
		String querystr = 	"	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
		"	PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
		"	PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
		"	SELECT ?class ?property" +
		"	WHERE {" +
		"		{?class rdf:type rdfs:Class.}" +
		"		UNION" +
		"       {?class rdf:type owl:Class.}" +
		"		{?property rdf:type rdf:Property.}" +
		"		UNION" +
		"		{?property rdf:type owl:ObjectProperty.}" +
		"		UNION" +
		"		{?property rdf:type owl:DatatypeProperty.}" +
		"	}";
		Query query = QueryFactory.create(querystr);
		QueryExecution qe = QueryExecutionFactory.create(query, this.model);
		ResultSet results = qe.execSelect();
		for(;results.hasNext();){
			QuerySolution qs = results.nextSolution();
			Resource cla = (Resource) qs.get("class");
			Resource pro = (Resource) qs.get("property");
			if(cla.isURIResource() && this.classes.indexOf(cla.getURI()) == -1){
				this.classes.add(cla.getURI());
			}
			if(pro.isURIResource() && this.properties.indexOf(pro.getURI()) == -1){
				this.properties.add(pro.getURI());
			}
		}
		RDFaAnnotator rdfaa = new RDFaAnnotator(this.model);
		rdfaa.setCurrentContextURL(this.vocab_url);
		String xhtmlrdfa = "";
		String class_snippet = "";
		int class_count = 0;
		int property_count = 0;
		for(String cla : this.classes){
			rdfaa.createMapTreeForTopicURI(cla);
			class_snippet += "<br/><hr/>" + rdfaa.generateRDFa(cla);
			class_count++;
		}
		String property_snippet = "";
		for(String pro : this.properties){
			rdfaa.createMapTreeForTopicURI(pro);
			property_snippet += "<br/><hr/>" + rdfaa.generateRDFa(pro);
			property_count++;
		}
		long end = System.currentTimeMillis();
		if(!property_snippet.equals("")){
			System.out.println(end-begin+"ms");
			xhtmlrdfa = "<div id=\"classes\"><h3 style=\"margin:0;padding:0\">Classes ("+class_count+"):</h3><br/>" + class_snippet + "</div><p/><p/><div id=\"properties\"><h3 style=\"margin:0;padding:0\">Properties ("+property_count+"):</h3><br/>" + property_snippet + "</div>";
			return RDFaAnnotator.decorateRDFa(rdfaa.getPrefixes(), xhtmlrdfa, type);
		}
		else{
			System.out.print(end-begin+"ms ");
			System.err.println("No class or property information inside this file.");
			return "No class or property information inside this file.";
		}
	}
	
	public static void main(String[] args){
		VocabRDFaAnnotator vrdfaa = new VocabRDFaAnnotator("http://www.w3.org/TR/owl-guide/wine.rdf");
		System.out.println(vrdfaa.getVocabClasses());
		System.out.println(vrdfaa.getVocabProperties());
		System.out.println(vrdfaa.gernerateRDFa("complete"));
	}
}
