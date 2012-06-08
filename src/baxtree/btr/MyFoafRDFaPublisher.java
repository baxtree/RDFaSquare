package baxtree.btr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class MyFoafRDFaPublisher {
	
	private String firstname;
	private String lastname;
	private String mboxsha1sum;
	private String homepage;
	private String depiction;
	private String topic_uri;
	
	public MyFoafRDFaPublisher(){
		firstname = "";
		lastname = "";
		mboxsha1sum = "";
		homepage = "";
		depiction = "";
		topic_uri = "";
	}
	
	public void generateRDFa(String topic_uri){
		this.topic_uri = topic_uri;
		Model model = ModelFactory.createOntologyModel();
		try{
			FileInputStream fis = new FileInputStream(new File("myfoaf.rdf"));
			InputStreamReader isr = new InputStreamReader(fis);
			model.read(isr, this.topic_uri, null);
//			model.write(new PrintWriter(System.out));
			String querystr = 	"	PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
							"	PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
							"	SELECT ?firstname ?lastname ?mboxsha1sum ?homepage ?depiction ?people ?name ?knowedmbox ?knoweduri" +
							"	WHERE {" +
							"		<"+this.topic_uri+">		foaf:firstName		?firstname." +
							"		<"+this.topic_uri+">		foaf:familyName 	?lastname." +
							"		<"+this.topic_uri+">		foaf:mbox_sha1sum	?mboxsha1sum." +
							"		<"+this.topic_uri+">		foaf:homepage		?homepage." +
							"		<"+this.topic_uri+">		foaf:depiction 		?depiction." +
							"		<"+this.topic_uri+">		foaf:knows			?people." +
							"		?people				foaf:name 			?name." +
							"		?people 			foaf:mbox_sha1sum	?knowedmbox." +
							"		?people				rdfs:seeAlso		?knoweduri." +
							"	}";
			Query query = QueryFactory.create(querystr);
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			ResultSet results = qe.execSelect();
			qe.close();
			Map root = new HashMap();
			Map personal = new HashMap();
			Map knows = new HashMap();
			root.put("personal", personal);
			root.put("knows", knows);
			boolean flag = true;
			int id = 0;
			for(;results.hasNext();){
				QuerySolution qs = results.nextSolution();
				if(true){
					this.firstname = qs.get("firstname").toString();
					
					this.lastname = qs.get("lastname").toString();
					this.mboxsha1sum = qs.get("mboxsha1sum").toString();
					this.homepage = qs.get("homepage").toString();
					this.depiction = qs.get("depiction").toString();
					personal.put("topicuri", this.topic_uri);
					personal.put("firstname", this.firstname);
					personal.put("lastname", this.lastname);
					personal.put("mboxsha1sum", this.mboxsha1sum);
					personal.put("homepage", this.homepage);
					personal.put("depiction", this.depiction);
					flag = false;
				}
				Map person = new HashMap();
				person.put("name", qs.get("name").toString());
				person.put("mboxsha1sum", qs.get("knowedmbox").toString());
				person.put("seealso", qs.get("knoweduri").toString());
				knows.put("person_"+id, person);
				qs.get("name").toString();
				id++;
			}
			try {
				Configuration cfg = new Configuration();
				cfg.setDirectoryForTemplateLoading(new File("."));
				cfg.setObjectWrapper(new DefaultObjectWrapper());
				Template temp = cfg.getTemplate("foafInRDFa.ftl");
				Writer out = new OutputStreamWriter(System.out);
				temp.process(root, out);
				out.flush();
			} catch (TemplateException te) {
				te.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		MyFoafRDFaPublisher mfrdfap = new MyFoafRDFaPublisher();
		mfrdfap.generateRDFa("http://homepages.inf.ed.ac.uk/s0896253#me");
	}
}
