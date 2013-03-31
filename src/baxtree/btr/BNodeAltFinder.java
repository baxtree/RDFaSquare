package baxtree.btr;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class BNodeAltFinder {
	
	private static String[] altproperty_uris = {
		"http://www.w3.org/2000/01/rdf-schema#seeAlso",
		"http://xmlns.com/foaf/0.1/name", 
		"http://www.w3.org/2004/02/skos/core#prefLabel",
		"http://www.w3.org/2000/01/rdf-schema#label",
		"http://purl.org/dc/terms/title",
		"http://purl.org/dc/terms/subtitle",
		"http://purl.org/dc/elements/1.1/title"
	};
	
	public static String getAltForBlankNode(Resource bnode, Model model){
		String alt = null; 
		for(String property_uri : altproperty_uris){
			Property property = model.getProperty(property_uri);
			if(model.contains(bnode, property)){
				Statement statement = model.getProperty(bnode, property);
				RDFNode object = (Resource) statement.getObject();
				if(object.isURIResource()){
					Resource obj_resource = (Resource) object;
					alt = property_uri + ";;" + obj_resource.getURI();
					break;
				}
				else if(object.isLiteral()){
					Literal obj_literal = (Literal) object;
					String lexicalform = obj_literal.getLexicalForm();
					String datatype = obj_literal.getDatatypeURI();
					String language = obj_literal.getLanguage();
					alt = property_uri + "::" + lexicalform + "::" + datatype + "::" + language;
					break;
				}
				continue;
			}
		}
		return alt;
	}
}
