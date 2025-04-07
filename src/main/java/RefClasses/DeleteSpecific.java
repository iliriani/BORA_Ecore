package RefClasses;

import java.io.File;

public class DeleteSpecific {
	
	public void deleteSpecific() {
		
		
		File folder = new File("C:\\Users\\Admin\\Desktop\\10-fold evaluation\\10\\train");
//		Model kg = ModelFactory.createDefaultModel().read("C:\\Users\\Admin\\Desktop\\KG\\Orders.rdf");

		File[] listOfFiles = folder.listFiles(); // Interating through the directory
		for (File file : listOfFiles) {
			if (file.isFile()) {
				String filepath = file.getAbsolutePath();
				String fileType = filepath.substring(filepath.length()-3, filepath.length());
				if(fileType.equals("xml")) {file.delete();}

			}
		}
		
		System.out.println("Done! Deleted!");
	}
	

}
