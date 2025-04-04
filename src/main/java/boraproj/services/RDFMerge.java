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
		File folder = new File("C:\\Users\\Ibrahimi\\Desktop\\10-fold evaluation ZappDev models\\2\\test");
		Graph acc = GraphFactory.createDefaultGraph();

		// Reading the initial file separately
		try {
			RDFDataMgr.read(acc, "file:C:\\Users\\Ibrahimi\\Desktop\\10-fold evaluation ZappDev models\\2\\test\\AccessComponentFunction8.bo.rdf");
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
		try (FileOutputStream out = new FileOutputStream("C:\\Users\\Ibrahimi\\Desktop\\10-fold evaluation ZappDev models\\2\\test\\repository\\merged.rdf")) {
			RDFDataMgr.write(out, acc, RDFFormat.RDFXML);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
