package ParsersEncoders;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

public class EncodeAll {

	public static void encode(String[] args) throws IOException {

		// TODO Auto-generated method stub

		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore",
				new EcoreResourceFactoryImpl());
		String srcPath = "input_ecores\\";
		String destPath = "out_ecores\\";
		File rootDir = new File(srcPath);
		System.out.println("Exctracting features from ecore files...");
		for (File fold : rootDir.listFiles()) {
			File dir = new File(fold + "\\");
			File[] directoryListing = dir.listFiles();

			for (File f : directoryListing) {
				EcoreParser parser = new EcoreParser();
				parser.encodeToRDF(f,f.getName());

			}
		}
		System.out.println("Done.");

	}

}
