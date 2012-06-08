package baxtree.btr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bibtex.dom.BibtexEntry;
import bibtex.dom.BibtexFile;
import bibtex.parser.BibtexParser;
import bibtex.parser.ParseException;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;


/**
 * 
 * @author Stefan Bischof
 */
public class MyBibtexRDFaPublisher {

	public static void main(String args[]){
		BibtexFile bibtex_file = new BibtexFile();
		BibtexParser parser = new BibtexParser(false);
		try{
			FileInputStream fis = new FileInputStream(new File("bibtex.bib"));
			InputStreamReader isr = new InputStreamReader(fis);
			parser.parse(bibtex_file, isr);
			fis.close();
			isr.close();
			Map root = new HashMap();
			Map publications = new HashMap();
			
			
			List entries = bibtex_file.getEntries();
			int count = 0;
			for(Object o : entries){
				if(o instanceof BibtexEntry){
					Map publication = new HashMap();
					
					BibtexEntry entry = (BibtexEntry) o; 
					publication.put("type", entry.getEntryType());
					Map temp = entry.getFields();
					Set fields = temp.keySet();
					for(Object field : fields){
						if(temp.get(field) != null){
							publication.put(field.toString(), temp.get(field).toString());
						}
						else{
							continue;
						}
					}
					publications.put("publication_"+count, publication);
					count++;
				}
			}
			root.put("publications", publications);
			try {
				Configuration cfg = new Configuration();
				cfg.setDirectoryForTemplateLoading(new File("."));
				cfg.setObjectWrapper(new DefaultObjectWrapper());
				Template temp = cfg.getTemplate("publicationsInRDFa.ftl");
				Writer out = new OutputStreamWriter(System.out);
				temp.process(root, out);
				out.flush();
			} catch (TemplateException te) {
				te.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			
		}
		catch (ParseException ex) {
			ex.printStackTrace();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
}


