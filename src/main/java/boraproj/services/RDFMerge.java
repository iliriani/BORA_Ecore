
package boraproj.services;

import java.io.*;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.jena.graph.Graph;
import org.apache.jena.rdf.model.Model;

import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.sparql.graph.GraphFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.VCARD;

public class RDFMerge {

	private static File folder;
	private static Model kg;

	public static void iterate() {
		File folder = new File("C:\\Users\\Ibrahimi\\Desktop\\10-fold evaluation ZappDev models\\1\\train");
		Graph acc = GraphFactory.createDefaultGraph();

		// Reading the initial file separately
		try {
			RDFDataMgr.read(acc, "file:C:\\Users\\Ibrahimi\\Desktop\\10-fold evaluation ZappDev models\\1\\train\\MRFMessage8.bo.rdf");
		} catch (Exception e) {
			System.err.println("Error reading initial RDF file: BO8.bo.rdf - " + e.getMessage());
		}

		// Iterate over all files in the folder
		for (File file : folder.listFiles()) {
			if (file.isFile() && file.getName().endsWith(".rdf")) {  // Ensuring it's an RDF file
				try {
					System.out.println("Reading file: " + file.toURI().toString());
					RDFDataMgr.read(acc, file.toURI().toString());
				} catch (Exception e) {
					System.err.println("Error reading file: " + file.getName() + " - " + e.getMessage());
				}
			}
		}

		// Write the merged output to RDF/XML format
		try (FileOutputStream out = new FileOutputStream("C:\\Users\\Ibrahimi\\Desktop\\10-fold evaluation ZappDev models\\1\\train\\repository\\merged.rdf")) {
			RDFDataMgr.write(out, acc, RDFFormat.RDFXML);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}


//package github2rdf;
//
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//import org.apache.commons.io.output.ByteArrayOutputStream;
//import org.apache.jena.rdf.model.Model;
//
//import org.apache.jena.rdf.model.ModelFactory;
//import org.apache.jena.rdf.model.Resource;
//import org.apache.jena.util.FileManager;
//import org.apache.jena.vocabulary.VCARD;
//
//public class RDFMerge {
//
//	private static File folder;
//	private static Model kg;
////	private static OutputStream out;
//
//	public static void iterate() {
//
//		folder = new File("C:\\Users\\Admin\\Desktop\\ZD Ev\\1\\test");
//		kg = ModelFactory.createDefaultModel().read("C:\\Users\\Admin\\Desktop\\ZD Ev\\1\\test\\AccessComponent.bo.rdf");
//
//		File[] listOfFiles = folder.listFiles(); // Interating through the directory
//		for (File file : listOfFiles) {
//
//			if (file.isFile()) {
//
////				System.out.println(file.getName());
//
////			  Model other = ModelFactory.createDefaultModel().read(file.getAbsolutePath());
//
//				// Converting OutputStream from merge() to Model OutputStream o; try { o =
//				kg = merge(kg, file);
//
////			  ByteArrayOutputStream buffer = (ByteArrayOutputStream) o; byte[] bytes =
////			  buffer.toByteArray(); InputStream inputStream = new
////			  ByteArrayInputStream(bytes);
////			  
////			  Model model = ModelFactory.createDefaultModel(); // InputStream in =
////			  FileManager.get().open("base.rdf");
////			  
////			  
////			  kg = model.read(inputStream, "");
//			}
//
//		}
//		
//		
//		  OutputStream out1;
//		  try { out1 = new FileOutputStream( new File("C:\\Users\\Admin\\Desktop\\ZD Ev\\1\\test\\kg-test.rdf")); 
//		  kg.write( out1, "RDF/XML", null ); 
//		  System.out.println("RDFs merged successfully!"); } 
//		  catch(FileNotFoundException e) { e.printStackTrace(); }
//		 
//
//	}
//
////	public static OutputStream merge(Model k, Model o) throws Exception {
//
//	public static Model merge(Model k, File i) {
//
////				Model kg_in = ModelFactory.createDefaultModel().read(k.getAbsolutePath());
//		Model other_in = ModelFactory.createDefaultModel().read(i.getAbsolutePath());
//
////					dcterms.write(System.out);
//		// If you only have two models, you can use Union model. Model union =
//		Model union = ModelFactory.createUnion(k, other_in);
//		return union;
//
//		/*
//		 * OutputStream out1; try { out1 = new FileOutputStream( new
//		 * File("C:\\Users\\Admin\\Desktop\\merged.rdf")); union.write( out1, "RDF/XML",
//		 * null ); System.out.println("RDFs merged successfully!"); } catch
//		 * (FileNotFoundException e) { e.printStackTrace(); }
//		 */
//
//		// In general, though, it's probably better to just create a new model to hold
//		// all the triples, and to add the triples to that model.
//		/*
//		 * final Model blob = ModelFactory.createDefaultModel(); for ( final Model part
//		 * : new Model[] { dcterms, foafIndex } ) { blob.add( part ); } try ( final
//		 * OutputStream out2 = new FileOutputStream( new File( "/tmp/purl_foaf2.rdf" ))
//		 * ) { blob.write( out2, "RDF/XML-ABBREV", null ); }
//		 */
//
////		return out1;
//	}
//
//}
