package RDFaAnnotator.main;

import java.util.HashMap;
import java.util.Map;

public class ServiceDrivenRDFaAnnotator {

	public String generateRDFaFromEndpoint(String endpoint_url, String topic_uri, String endpoint_name, String type){
		RDFaAnnotatorForService rdfaa = new RDFaAnnotatorForService(endpoint_url, topic_uri, endpoint_name);
		rdfaa.createMapTreeForTopicURIFromEndpoint();
		Map<String, String> prefixes = new HashMap<String, String>();
		return rdfaa.decorateRDFa(prefixes, rdfaa.generateRDFaFromEndpoint(endpoint_url, ""), type);
	}
	
	public static void main(String [] args){
		ServiceDrivenRDFaAnnotator sdrdfaa = new ServiceDrivenRDFaAnnotator();
		System.out.println(sdrdfaa.generateRDFaFromEndpoint("http://dbpedia.org/sparql", "http://dbpedia.org/resource/University_of_Edinburgh", "", "complete"));
		
	}
}
