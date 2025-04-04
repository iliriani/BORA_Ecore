package RefClasses;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.jena.graph.Graph;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.sparql.graph.GraphFactory;

public class RDFMerge {
	
	public String fileName = "";
	
	public void graphMerge(String initial_model){


	    try {
	    	
	 	   File folder = new File("\\BusinessObjects");
		    Graph acc = GraphFactory.createDefaultGraph();
		    String inputFile = "BusinessObjects\\"+initial_model+".rdf";
		    RDFDataMgr.read(acc,"file:"+inputFile);
		    for (File file : folder.listFiles()) {
		        if (file.isFile())
		        	
		        	try {
		        		
		        	fileName = file.getName();
		            RDFDataMgr.read(acc, file.toURI().toString());}
		        catch(Exception e) {
		        	System.out.println("Problem with the file: "+fileName);
		        	continue;}
		        
		    }
	    	
	    	FileOutputStream out = new FileOutputStream("merged.rdf");
	        RDFDataMgr.write(out, acc, RDFFormat.RDFXML);
	    
	    }catch (Exception e) {
//			System.out.println("Problem with the file: "+fileName);
	    	e.printStackTrace();
		}
	    
	    System.out.println("Merged succefully!");
	    
	    return;
	    
	    
	}
}
