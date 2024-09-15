package github2rdf;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.jena.query.Dataset;
import org.xml.sax.SAXException;

import no.acando.xmltordf.Builder;

public class Xml2Rdf{
	
	private static String filepath;
	
	
	public void convertAll() {
		
		File folder = new File("C:\\Users\\Admin\\Desktop\\repotest");
//		Model kg = ModelFactory.createDefaultModel().read("C:\\Users\\Admin\\Desktop\\KG\\Orders.rdf");

		File[] listOfFiles = folder.listFiles(); // Interating through the directory
		for (File file : listOfFiles) {
			if (file.isFile()) {
				convert(file);

			}
		}
	}
	

	public void convert(File file) {
	
    try {
    	
		this.filepath = file.getAbsolutePath();
		String fileout = filepath.substring(0, filepath.length() - 3)+"rdf";
//		System.out.println(filepath.substring(0, filepath.length() - 3)+"rdf");
    	
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(filepath));
//        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("C:\\Users\\Admin\\eclipse\\epsilon-2-0\\workspace\\GitHub2RDF\\src\\main\\java\\github2rdf\\note.ttl"));
//        Builder.getAdvancedBuilderStream().build().convertToStream(in, out);
        Dataset dataset = Builder.getAdvancedBuilderJena().build().convertToDataset(in);
        
        dataset.getDefaultModel().write(new FileOutputStream(fileout),"RDF/XML");
        
//        Repository repository = Builder.getAdvancedBuilderRDF4J().build().convertToRepository(in);
        
        
    } catch (IOException e) {
    	System.out.println("File ne gabim eshte: "+filepath); e.printStackTrace(); 
    } catch (ParserConfigurationException e) {
    	System.out.println("File ne gabim eshte: "+filepath);
		e.printStackTrace();
	} catch (SAXException e) {
		System.out.println("File ne gabim eshte "+filepath);
		e.printStackTrace();
	}
	System.out.println(filepath);
	}
	

}
