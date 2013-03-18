package baxtree.btr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xibai
 *
 */
public class ImportanceCalculator {
	private static double Lamda = 0.65;
//	private int
	private static HashMap<String, String> uri_occurrence_in_sub;
	private static HashMap<String, String> uri_occurrence_in_obj;
	private static HashMap<String, Double> uri_importance;
	private static HashMap<String, Integer> uri_occurence;
	private static ArrayList<String> uris;
	private static ArrayList<String> top_uris;

	/**
	 * calculate the importaces of the resources
	 * @param sub_map the occurence of the resources in subject position
	 * @param obj_map the occurence of the resources in object position
	 * @return importaces of the resources
	 */
	public static HashMap<String, Double> getUriImportanceMap(HashMap<String, String> sub_map, HashMap<String, String> obj_map){
		return getUriImportanceMap(sub_map, obj_map, Lamda);
	}
	
	/**
	 * calculate the importaces of the resources
	 * @param sub_map the occurence of the resources in subject position
	 * @param obj_map the occurence of the resources in object position
	 * @param lamda threshold
	 * @return importaces of the resources
	 */
	public static HashMap<String, Double> getUriImportanceMap(HashMap<String, String> sub_map, HashMap<String, String> obj_map, double lamda){
		uri_occurrence_in_sub = sub_map;
		uri_occurrence_in_obj = obj_map;
		uri_importance = new HashMap<String, Double>();
		Set<String> temp1 =  uri_occurrence_in_sub.keySet();
		Set<String> temp2 = uri_occurrence_in_obj.keySet();
		uris = new ArrayList<String>();
		for(String s1 : temp1)
			if(!uris.contains(s1))
				uris.add(s1);
		for(String s2 : temp2)
			if(!uris.contains(s2))
				uris.add(s2);
		int out_degree;
		int in_degree;
		for(String uri : uris){
			if(uri_occurrence_in_sub.containsKey(uri))
				out_degree = Integer.parseInt(uri_occurrence_in_sub.get(uri));
			else
				out_degree = 0;
			if(uri_occurrence_in_obj.containsKey(uri))
				in_degree = Integer.parseInt(uri_occurrence_in_obj.get(uri));
			else
				in_degree = 0;
			uri_importance.put(uri, calculateImportance(out_degree, in_degree, lamda));
		}
		return uri_importance;
	}
	
	/**
	 * find the resource with the largest importance
	 * @param sub_map the occurence of the resources in subject position
	 * @param obj_map the occurence of the resources in object position
	 * @return the uri of the resource
	 */
	public static String getUriWithLargestImportance(HashMap<String, String> sub_map, HashMap<String, String> obj_map){
		return getUriWithLargestImportance(sub_map, obj_map, Lamda);
	}
	
	/**
	 * find the resource with the largest importance
	 * @param sub_map the occurence of the resources in subject position
	 * @param obj_map the occurence of the resources in object position
	 * @param lamda threshold
	 * @return the uri of the resource
	 */
	public static String getUriWithLargestImportance(HashMap<String, String> sub_map, HashMap<String, String> obj_map, double lamda){
		HashMap<String, Double> temp = getUriImportanceMap(sub_map, obj_map, lamda);
		Set<String> uri_temp = temp.keySet();
		String important_uri = "";
		double max_importance = 0.0;
		for(String uri : uri_temp){
			if(temp.get(uri) > max_importance){
				important_uri = uri;
				max_importance = temp.get(uri);
			}
//			System.out.println(uri+"\t"+temp.get(uri));
		}
		return important_uri;
	}
	
	
	/**
	 * find the resource with the largest importance
	 * @param sub_map the occurence of the resources in subject position
	 * @param obj_map the occurence of the resources in object position
	 * @param url the original url of the RDF document
	 * @param top_num the number of the candidates
	 * @return the uri of the resource
	 */
	public static String getUriWithLargestOccurrence(HashMap<String, String> sub_map, HashMap<String, String> obj_map, String url, int top_num){
		double threshold = 0.8;
		top_uris = new ArrayList<String>(top_num);
		uri_occurrence_in_sub = sub_map;
		uri_occurrence_in_obj = obj_map;
		uri_occurence = new HashMap<String, Integer>();
		Set<String> temp1 =  uri_occurrence_in_sub.keySet();
		Set<String> temp2 = uri_occurrence_in_obj.keySet();
		uris = new ArrayList<String>();
		for(String s1 : temp1)
			if(!uris.contains(s1))
				uris.add(s1);
		for(String s2 : temp2)
			if(!uris.contains(s2))
				uris.add(s2);
		int out_degree;
		int in_degree;
		for(String uri : uris){
			if(uri_occurrence_in_sub.containsKey(uri))
				out_degree = Integer.parseInt(uri_occurrence_in_sub.get(uri));
			else
				out_degree = 0;
			if(uri_occurrence_in_obj.containsKey(uri))
				in_degree = Integer.parseInt(uri_occurrence_in_obj.get(uri));
			else
				in_degree = 0;
			uri_occurence.put(uri, calculateOccurrence(out_degree, in_degree));
		}
		HashMap<String, Integer> backup = new HashMap<String, Integer>();
		Set<String> keys = uri_occurence.keySet();
		for(String key : keys)
			backup.put(key, uri_occurence.get(key));
		for(int i = 0; i < top_num; i++){
			Set<String> uri_temp = uri_occurence.keySet();
			String uri = "";
			int max_occurence = 0;
			for(String s : uri_temp){
				if(uri_occurence.get(s) > max_occurence){
					uri = s;
					max_occurence = uri_occurence.get(s);
				}
			}
			top_uris.add(uri);
			uri_occurence.remove(uri);
		}
		if(top_uris.get(0) == null || top_uris.get(0).equals("") || top_uris.size() == 1){
			return getUriWithLargestOccurrence(sub_map, obj_map);
		}
		else{
			String key = top_uris.get(0);
			int max = backup.get(key);
			ArrayList<String> temp = new ArrayList<String>();
			for(String uri : top_uris)
				temp.add(uri);
			for(String uri : temp){
				if(((double)backup.get(uri)/(double)max) < threshold)
					top_uris.remove(uri);
			}
			int maxmax = 0;
			String topic = top_uris.get(0);
			for(String uri : top_uris){
//				System.out.println(uri+"******"+url);
				if(MyFunctions.LCSLenght(uri, url)>max){
					maxmax = MyFunctions.LCSLenght(uri, url);
					topic = uri;
				}
				else
					continue;
			}
			return topic;
		}
	}
	
	/**
	 * find the resource with the largest occurence
	 * @param sub_map the occurence of the resources in subject position
	 * @param obj_map the occurence of the resources in object position
	 * @return the uri of the resource
	 */
	public static String getUriWithLargestOccurrence(HashMap<String, String> sub_map, HashMap<String, String> obj_map){
		uri_occurrence_in_sub = sub_map;
		uri_occurrence_in_obj = obj_map;
		uri_occurence = new HashMap<String, Integer>();
		Set<String> temp1 =  uri_occurrence_in_sub.keySet();
		Set<String> temp2 = uri_occurrence_in_obj.keySet();
		uris = new ArrayList<String>();
		for(String s1 : temp1)
			if(!uris.contains(s1))
				uris.add(s1);
		for(String s2 : temp2)
			if(!uris.contains(s2))
				uris.add(s2);
		int out_degree;
		int in_degree;
		for(String uri : uris){
			if(uri_occurrence_in_sub.containsKey(uri))
				out_degree = Integer.parseInt(uri_occurrence_in_sub.get(uri));
			else
				out_degree = 0;
			if(uri_occurrence_in_obj.containsKey(uri))
				in_degree = Integer.parseInt(uri_occurrence_in_obj.get(uri));
			else
				in_degree = 0;
			uri_occurence.put(uri, calculateOccurrence(out_degree, in_degree));
		}
		Set<String> uri_temp = uri_occurence.keySet();
		String uri = "";
		int max_occurence = 0;
		for(String s : uri_temp){
			if(uri_occurence.get(s) > max_occurence){
				uri = s;
				max_occurence = uri_occurence.get(s);
			}
		}
		return uri;
	}
	
	/**
	 * find the resource with the largest in-degree or out-degree
	 * @param obj_or_sub_map the occurence of the resources in subject position or object position
	 * @return the uri of the resource
	 */
	public static String getUriWithLargestInOrOutDegree(HashMap<String, String> obj_or_sub_map){
		uri_occurrence_in_obj = obj_or_sub_map;
		Set<String> uri_temp = uri_occurrence_in_obj.keySet();
		String uri = "";
		int max_occurence = 0;
		for(String s : uri_temp){
			int num = Integer.parseInt(uri_occurrence_in_obj.get(s));
			if(num > max_occurence){
				uri = s;
				max_occurence = num;
			}
		}
		return uri;
	}
	
	
	/**
	 * calculate the sum of the out-degree and the in-degree
	 * @param out_degree out-degree
	 * @param in_degree in-degree
	 * @return the sum
	 */
	public static int calculateOccurrence(int out_degree, int in_degree){
		return out_degree + in_degree;
	}
	
	
	/**
	 * calculate the importance of the node
	 * @param out_degree out-degree of the node
	 * @param in_degree in-degree of the node
	 * @return the importance
	 */
	public static double calculateImportance(int out_degree, int in_degree){
		return calculateImportance(out_degree, in_degree, Lamda);
	}
	
	/**
	 * calculate the importance of the node
	 * @param out_degree out-degree of the node
	 * @param in_degree in-degree of the node
	 * @param lamda threshold
	 * @return the importance
	 */
	public static double calculateImportance(int out_degree, int in_degree, double lamda){
		Lamda = lamda;
		return Lamda * out_degree + (1-Lamda) * in_degree;
	}
	
	public static List<Map.Entry<String, Double>> compare(HashMap map){
		
		List<Map.Entry<String, Double>> infoIds =
		    new ArrayList<Map.Entry<String, Double>>(map.entrySet());

		Collections.sort(infoIds, new Comparator<Map.Entry<String, Double>>() {   
		    public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {      
		        return Double.compare(o2.getValue(), o1.getValue()); 
		        //return (o1.getKey()).toString().compareTo(o2.getKey());
		    }
		}); 
		return infoIds;
//		for(int i = 0; i < infoIds.size(); i++){
//			System.out.println(infoIds.get(i).toString());
//		}
	}
	
	public static ArrayList<String> getTopNUriWithLargestImportance(int n, HashMap<String, String> sub_map, HashMap<String, String> obj_map, double lamda){
		ArrayList<String> potential_topics = new ArrayList<String>();
		HashMap<String, Double> importances = getUriImportanceMap(sub_map, obj_map, lamda);
		List<Map.Entry<String, Double>> infoIds = compare(importances);
		int k = infoIds.size();
		int count = 1;
		for(int i = 0; i < k; i++){
			String[] temp = infoIds.get(i).toString().split("=");
			if(!temp[0].startsWith("http://")){
				continue;
			}
			else{
				potential_topics.add(temp[0]);
				if(count == n)
					break;
				else
					count++;
			}
		}
		return potential_topics;
	}
}
