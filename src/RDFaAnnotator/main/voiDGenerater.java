package RDFaAnnotator.main;

public class voiDGenerater {
	public static String getvoiDDIV(String topic_uri, String rdf_url){
		String div = 	"<div about=\""+topic_uri+"\" xmlns:void=\"http://rdfs.org/ns/void#\" xmlns:dcterms=\"http://purl.org/dc/terms/\">" +
						"	<span rel=\"dcterms:isPartOf\">" +
						"		<span typeof=\"void:Dataset\">" +
						"			<span rel=\"void:dataDump\" resource=\""+rdf_url+"\"/>" +
						"		</span>" +
						"	</span>" +
						"</div>";
		return div;
	}
}
