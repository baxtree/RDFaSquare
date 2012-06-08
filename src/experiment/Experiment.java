package experiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import RDFaAnnotator.main.VocabRDFaAnnotator;


public class Experiment {

	public void experimentWithPublishingVocab(){
		File file = new File("pingthesemanticweb.txt");
		try{
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String url = "";
			while((url = br.readLine()) != null){
				try{
					if(url.startsWith("#")){
						continue;
					}
					VocabRDFaAnnotator vrdfaa = new VocabRDFaAnnotator(url);
					//long begin = System.currentTimeMillis();
					vrdfaa.gernerateRDFa("complete");
					//long end = System.currentTimeMillis();
					//System.out.println(end-begin+"ms");
				}
				catch(Exception fnfe){
					continue;
				}
			}
		}
//		catch(FileNotFoundException fnfe){
//			fnfe.printStackTrace();
//		}
		catch(UnsupportedEncodingException uee){
			uee.printStackTrace();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		Experiment exp = new Experiment();
		exp.experimentWithPublishingVocab();
	}
}
