package baxtree.btr;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class LabelFinder {
	
	private static String[] labeling_uris = {"http://xmlns.com/foaf/0.1/name", 
										"http://www.w3.org/2004/02/skos/core#prefLabel",
										"http://www.w3.org/2000/01/rdf-schema#label",
										"http://purl.org/dc/terms/title",
										"http://purl.org/dc/terms/subtitle"};

	public static String getPreferredLabelFromEndpoint(String endpoint_url, String uri){
		//TODO need implement
		return null;
	}
	
	public static String getPreferredLabel(Model model, String uri){
//		model.write(System.out, "RDF/XML-ABBREV");
		String label = null;
		Resource resource = model.getResource(uri);
		for(String labeling_uri : labeling_uris){
			Property property = model.getProperty(labeling_uri);
			if(model.contains(resource, property)){
				Statement statement = model.getProperty(resource, property);
				Literal literal =  (Literal) statement.getObject().as(Literal.class);
				label = literal.getLexicalForm();
				break;
			}
		}
		if(label == null && resource.getLocalName() != null && !resource.getLocalName().equals(""))
			label = resource.getLocalName();
			
		return label;
	}
	
	public static String applyLabelToResource(String endpoint_url, String str){
		String result = str;
		Pattern pattern = Pattern.compile(">((http://|https://){1}[^<>]+)<");//Find the urls which are node texts. This is a little fragile. 
		Matcher matcher = pattern.matcher(str);
		while(matcher.find()){
			String uri = matcher.group(1);
			String preferred_label = getPreferredLabelFromEndpoint(endpoint_url, uri);
			if(preferred_label != null){
				result = result.replace(">"+uri+"<", ">"+preferred_label+"<");
			}
		}
		return result;
	}
	
	public static String applyLabelToResource(Model model, String str){
		String result = str;
		Pattern pattern = Pattern.compile(">((http://|https://){1}[^<>]+)<");//Find the urls which are node texts. This is a little fragile. 
		Matcher matcher = pattern.matcher(str);
		while(matcher.find()){
			String uri = matcher.group(1);
			String preferred_label = getPreferredLabel(model, uri);
			if(preferred_label != null){
				result = result.replace(">"+uri+"<", ">"+preferred_label+"<");
			}
		}
		return result;
	}
}
