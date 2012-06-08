package baxtree.btr;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Converter {

	public static void main(String args[]) {

		// Create the root hash
		Map root = new HashMap();
		// Put string ``user'' into the root
		root.put("user", "Big Joe");
		// Create the hash for ``latestProduct''
		Map latest = new HashMap();
		// and put it into the root
		root.put("latestProduct", latest);
		// put ``url'' and ``name'' into latest
		latest.put("url", "products/greenmouse.html");
		latest.put("name", "green mouse");

//		System.out.println(root);
		
		try {
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File("e:\\FTL"));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			Template temp = cfg.getTemplate("fmtemplate.ftl");
			Writer out = new OutputStreamWriter(System.out);
			temp.process(root, out);
			out.flush();
		} catch (TemplateException te) {
			te.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
