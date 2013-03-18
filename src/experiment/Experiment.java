package experiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import baxtree.btr.ImportanceCalculator;
import baxtree.btr.NodeStatist;

import com.hp.hpl.jena.rdf.model.Model;

import RDFaAnnotator.main.FederatedRDFaAnnotator;
import RDFaAnnotator.main.RDFModelLoader;
import RDFaAnnotator.main.VocabRDFaAnnotator;


public class Experiment {
	
	public void experimentWithAnRdfHarvesterStartingPoint(){
		File file = new File("AnRdfHarvesterStartingPoint.txt");
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
					FederatedRDFaAnnotator fa = new FederatedRDFaAnnotator();
					Model model;
					model = RDFModelLoader.loadTriplesFromURL(url);
					NodeStatist ns = new NodeStatist(model);
					ArrayList<String> potential_topics = ImportanceCalculator.getTopNUriWithLargestImportance(1, ns.getUri_occurrence_in_sub(), ns.getUri_occurrence_in_obj(), 0.5);
					fa.addContextAndTopic(url, potential_topics.get(0));
					long begin = System.currentTimeMillis();
					System.out.println("<<<<"+url+">>>>");
					fa.generateRDFa("complete");
					long end = System.currentTimeMillis();
					System.out.println(end-begin+"ms");
					System.out.println();
				}
				catch(Exception fnfe){
					continue;
				}
			}
		}
		catch(UnsupportedEncodingException uee){
			uee.printStackTrace();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public void experimentWithFoafButtetinBoard(){
		File file = new File("foafbb.txt");
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
					FederatedRDFaAnnotator fa = new FederatedRDFaAnnotator();
					Model model;
					model = RDFModelLoader.loadTriplesFromURL(url);
					NodeStatist ns = new NodeStatist(model);
					ArrayList<String> potential_topics = ImportanceCalculator.getTopNUriWithLargestImportance(1, ns.getUri_occurrence_in_sub(), ns.getUri_occurrence_in_obj(), 0.5);
					fa.addContextAndTopic(url, potential_topics.get(0));
					long begin = System.currentTimeMillis();
					System.out.println("<<<<"+url+">>>>");
					fa.generateRDFa("complete");
					long end = System.currentTimeMillis();
					System.out.println(end-begin+"ms");
					System.out.println();
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
	
	public void experimentWithPrefixCC(){
		File file = new File("all.file.txt");
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
					url = url.split("\\t")[1];
					if(url.equalsIgnoreCase("???")){
						continue;
					}
					VocabRDFaAnnotator vrdfaa = new VocabRDFaAnnotator(url);
					//long begin = System.currentTimeMillis();
					System.out.println("<<<<"+url+">>>>");
					vrdfaa.gernerateRDFa("complete");
					System.out.println();
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

	public void experimentWithPTSW(){
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
	
	public void addLineNumber(String file_path){
		try{
			File file = new File(file_path);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String result = "";
			int i = 1;
			while((result = br.readLine()) != null){
				if(result.startsWith("#")) continue;
				System.out.println(i+" "+result);
				i++;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		Experiment exp = new Experiment();
		exp.addLineNumber("D:\\software\\gp425win32\\gnuplot\\bin\\WRDFHSP.dat");
	}
}
