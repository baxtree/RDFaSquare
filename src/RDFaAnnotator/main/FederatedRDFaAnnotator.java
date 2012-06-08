package RDFaAnnotator.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FederatedRDFaAnnotator {

	private ArrayList<String[]> context_topic;
	private String template_str;
	private String body_content;
	private Map<String, String> prefixes;
	
	public FederatedRDFaAnnotator(){
		context_topic = new ArrayList();
		template_str = "";
		body_content = "";
		prefixes = new HashMap<String, String>();
	}
	
	public ArrayList addContextAndTopic(String context_url, String topic_uri){
		String[] ct = {context_url, topic_uri};
		if(context_topic.contains(ct)){
			
		}
		else{
			context_topic.add(ct);
		}
		return context_topic;
	}
	
	public String generateRDFaBodyFromMultiContext(){
		for(String[] ct : context_topic){
			String rdf_url = ct[0];
			String topic_uri = ct[1];
			RDFaAnnotator rdfaa;
			if(topic_uri == null || topic_uri.equals(""))
				rdfaa = new RDFaAnnotator(rdf_url);
			else
				rdfaa = new RDFaAnnotator(rdf_url, topic_uri);
			rdfaa.createMapTreeForTopicURI();
			String temp = rdfaa.generateRDFa("");
			Map<String, String> prefixes = rdfaa.getPrefixes();
			Set<String> pres = prefixes.keySet();
			for(String prefix : pres){
				if(this.prefixes.containsKey(prefix)&&(this.prefixes.get(prefix).equalsIgnoreCase(prefixes.get(prefix)))){
					continue;
				}
				else if(this.prefixes.containsKey(prefix)&&!(this.prefixes.get(prefix).equalsIgnoreCase(prefixes.get(prefix)))){
					this.prefixes.put(prefix+"a", prefixes.get(prefix));
					temp.replace(prefix+":", prefix+"a:");
				}
				else{
					this.prefixes.put(prefix, prefixes.get(prefix));
				}
			}
			body_content += temp;
		}
//		System.out.println(body_content);
		return body_content;
	}
	
	public String generateRDFa(String type){
		if(context_topic.isEmpty()){
			System.err.println("Tell me the object your are going to annotate and the context RDF file.");
			return null;
		}
		else{
			return RDFaAnnotator.decorateRDFa(this.prefixes, generateRDFaBodyFromMultiContext(), type);
		}
	}
	
	public static void main(String [] args){
		FederatedRDFaAnnotator fa = new FederatedRDFaAnnotator();
		fa.addContextAndTopic("http://www.w3.org/TR/owl-guide/wine.rdf", "http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#Wine");
		fa.addContextAndTopic("http://richard.cyganiak.de/foaf.rdf", "http://richard.cyganiak.de/foaf.rdf#cygri");
		fa.addContextAndTopic("http://www.deri.ie/fileadmin/scripts/foaf.php?id=273", "http://www.deri.ie/about/team/member/Giovanni_Tummarello#me");
		System.out.println(fa.generateRDFa("complete"));
	}
}
