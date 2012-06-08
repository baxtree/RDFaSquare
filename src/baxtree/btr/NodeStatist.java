package baxtree.btr;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class NodeStatist {
	private Model model = ModelFactory.createDefaultModel();
	private int num_of_triple;
	private ArrayList<Statement> statement_buffer;
	private HashMap<String, String> uri_occurrence_in_sub = new HashMap<String, String>();
	private HashMap<String, String> uri_occurrence_in_obj = new HashMap<String, String>();
	private HashMap<String, RDFNode> anonymous_resource = new HashMap<String, RDFNode>();
	private String obj;
	private ArrayList<Property> properties = new ArrayList<Property>();
	private ArrayList<String> representative_properties_uri = new ArrayList<String>();

	
	public NodeStatist(Model model){
		this.model = model;
		this.num_of_triple = 0;
		this.statement_buffer = new ArrayList<Statement>();
		calculateNodeOccurence();
	}
	
	private void calculateNodeOccurence() {
		StmtIterator iter = model.listStatements();	
		while(iter.hasNext()){
			this.num_of_triple++;
			Statement stmt = (Statement)iter.next();
			if(!statement_buffer.contains(stmt)){
				statement_buffer.add(stmt);
			}
			else
				continue;
			Resource subject = stmt.getSubject();
			Property pro = stmt.getPredicate();
			RDFNode object = stmt.getObject();
			if(!properties.contains(pro))
				properties.add(pro);
			if(pro.getLocalName().equalsIgnoreCase("type"))
				continue;
			if(subject.canAs(Class.class))
				continue;
			if(subject.isAnon())
				anonymous_resource.put(subject.toString(), subject);
			if(object.isAnon())
				anonymous_resource.put(object.toString(), object);
			if(subject.isURIResource()){
				if(!uri_occurrence_in_sub.containsKey(subject.getURI()))
					uri_occurrence_in_sub.put(subject.getURI(), "1");
				else{
					int occurrence = Integer.parseInt(uri_occurrence_in_sub.get(subject.getURI()))+1;
					uri_occurrence_in_sub.remove(subject.getURI());
					uri_occurrence_in_sub.put(subject.getURI(), Integer.toString(occurrence));
				}
			}
			if(object.isURIResource()){
				Resource obj_resource = (Resource) object.as(Resource.class);
				if(!uri_occurrence_in_obj.containsKey(obj_resource.getURI()))
					uri_occurrence_in_obj.put(obj_resource.getURI(), "1");
				else{
					int occurrence = Integer.parseInt(uri_occurrence_in_obj.get(obj_resource.getURI()))+1;
					uri_occurrence_in_obj.remove(obj_resource.getURI());
					uri_occurrence_in_obj.put(obj_resource.getURI(), Integer.toString(occurrence));
				}
			}
			else if(object.isLiteral()){
				if(!uri_occurrence_in_obj.containsKey(object.toString()))
					uri_occurrence_in_obj.put(object.toString(), "1");
				else{
					int occurrence = Integer.parseInt(uri_occurrence_in_obj.get(object.toString()))+1;
					uri_occurrence_in_obj.remove(object.toString());
					uri_occurrence_in_obj.put(object.toString(), Integer.toString(occurrence));
				}
			}
		}
	}
	
	public String getNameOfResource(String uri){
		String result = "";
		Resource uri_temp;
		boolean flag = false;
		if(anonymous_resource.containsKey(uri)){
			uri_temp = (Resource)anonymous_resource.get(uri).as(Resource.class);
		}
		else{
			uri_temp = model.getResource(uri);
		}
//		 revise the code and add in to the reasoning 
		Property label = model.getProperty("http://www.w3.org/2000/01/rdf-schema#label");
		Iterator<Statement> labels = uri_temp.listProperties(label);
		if(labels.hasNext()){
			Statement stat = labels.next();
			RDFNode object = stat.getObject();
			String object_string = "";
			if(object.canAs(Literal.class)){
				Literal object_literal = (Literal) object.as(Literal.class);
				object_string = object_literal.getLexicalForm();
			}
			else
				object_string = object.toString();
			return object_string;
		}
		else{
			try{
			for(String top_uri : representative_properties_uri){
				Property pro = model.getProperty(top_uri);
				Iterator<Statement> temp = uri_temp.listProperties(pro);
				if(temp.hasNext()){
					Statement stat = temp.next();
					RDFNode object = stat.getObject();
					String object_string = "";
					if(object.canAs(Literal.class)){
						Literal object_literal = (Literal) object.as(Literal.class);
						object_string = object_literal.getLexicalForm();
					}
					else
						object_string = object.toString();
					result = object_string;
					flag = true;
					break;
				}
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			if(flag)
				return (result);
			else if(uri_temp.isAnon()&&uri_temp.canAs(Individual.class)){
				Individual anon = (Individual) uri_temp.as(Individual.class);
				if(anon.getRDFType() != null)
					result = anon.getRDFType().getLocalName();
				else
					result = "Noname";
				return result;
			}
			else if(uri.toLowerCase().contains("http://")&&uri.contains("#")){
				result = uri.split("#")[1];
				return result;
			}
			else if(uri.toLowerCase().contains("http://")){
				String[] temp = uri.split("/");
				result = temp[temp.length-1];
				return result;
			}
			else
				return uri;
		}
	}

	public HashMap<String, String> getUri_occurrence_in_obj() {
		return uri_occurrence_in_obj;
	}

	public HashMap<String, String> getUri_occurrence_in_sub() {
		return uri_occurrence_in_sub;
	}
}
