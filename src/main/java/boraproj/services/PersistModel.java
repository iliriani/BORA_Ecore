package boraproj.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import RefClasses.ModifyBO;
import RefClasses.RDFMerge;
import RefClasses.Xml2Rdf;

@Service
public class PersistModel {
	
	@Autowired
	RepoConnect repo;
	ModifyBO modify;
	Xml2Rdf xmlRdf;
	RDFMerge merge;
	AddWeights weights;
	
	public PersistModel() {
		repo = new RepoConnect("repository");
		modify = new ModifyBO();
		xmlRdf = new Xml2Rdf();
		merge = new RDFMerge();
		weights = new AddWeights();
	}
	
	
	public void persist(String name, String XMLmodel) {
		
		createFile(name, XMLmodel);
		modify.modAll();
		xmlRdf.convertAll();
		merge.graphMerge(name);
		File dir = new File("repository");
		dellDir(dir);
		repo.createDataset("merged.rdf", "repository");
		weights.insertAllWeight();
		
		
	}
	
	public void createFile(String name, String text) {

	    BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(name));
		    writer.write(text);
		    
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Problem me ruajtjen e file-it");
			e.printStackTrace();
		}

	}
	
	public void dellDir(File dir) {
		
		for (File file: dir.listFiles())
		    if (!file.isDirectory())
		        file.delete();
	}

}
