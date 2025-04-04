package ParsersEncoders;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

public class EcoreParser {

	public  String header="";
	public  String clAttr="";
	public String attFooter="</Attributes>\t\n </j.0:hasChild>\t\n";
	
	public String conHeder = "  <j.0:hasChild>\r\n "
			+ " <Associations>\r\n";
	public  String con="";
	public String conFooter = "</Associations>";
	
	public  String clHeader="";
	public  String clFoot="";
	public  String footer="";
	
	public  String rdfFile="";
	public String MName = "";
	
	
	public EncoderToRDF encode = new EncoderToRDF();

//	public static String ModelName="";

	public  void encodeToRDF(File ecoreFile, String name) {

		try {

//			HashMap<String, ArrayList<String>> allFiles = getECoreInfo(ecoreFile);
			String file_string = getECoreInfo(ecoreFile);
			
			FileWriter myWriter = new FileWriter(
					"C:\\Users\\Admin\\Desktop\\Files\\ecores\\" + name + ".rdf");

			myWriter.write(file_string);
			myWriter.close();
//			
			
//			  System.out.println(allFiles.get("ModelElement").get(0));

//			for (Entry<String, String> entry : file_string.entrySet()) {
//				String name = entry.getKey();
//				String fileEncoded = entry.getValue();
//
////					  EncoderToRDF encode = new EncoderToRDF();
////					  String header = encode.getHeader();
////					  String clHeader = encode.getClassHeader();
//
////				System.out.println(key + "  : " + value);
////				System.out.println("-----------------------------");
//////				    
//////				      Create the RDF File
//				FileWriter myWriter = new FileWriter(
//						"C:\\Users\\Admin\\Desktop\\Files\\ecores\\" + name + ".rdf");
//
//				myWriter.write(fileEncoded);
//				myWriter.close();

//			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public  String getECoreInfo(File ecoreFile) throws IOException {
//    public static HashMap<String,HashMap<String,HashMap<String, String>>> getEcoreInfo(File ecoreFile) throws IOException{

		// Read the ecore file
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore",
				new EcoreResourceFactoryImpl());
		Resource myMetaModel = null;



		
		ArrayList<String> classes = new ArrayList<String>();
		HashMap<String, ArrayList<String>> all_elements = new HashMap<String, ArrayList<String>>();

//		HashMap<String, HashMap<String, HashMap<String, String>>> ecoreElements = new HashMap<String, HashMap<String, HashMap<String, String>>>();
//		HashMap<String,String> fileData = new HashMap<String,String>();

		try {
			myMetaModel = resourceSet.getResource(URI.createFileURI(String.valueOf(ecoreFile)), true);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println(ecoreFile);
		}

		if (myMetaModel != null) {
			EList<EObject> contents = myMetaModel.getContents();
			try {
				for (int i = 0; i < contents.size(); i += 1) {
					EPackage univEPackage = (EPackage) myMetaModel.getContents().get(i);
					// Add the ModelName
					ArrayList<String> ModelName = new ArrayList<String>(Arrays.asList(univEPackage.getName()));
//               elements.add(univEPackage.getName());
					all_elements.put("ModelElement", ModelName);
					
					MName = univEPackage.getName();
					
					header = encode.getHeader(univEPackage.getName());
					System.out.println("Header is "+univEPackage.getName()+"-----------------------------");
					
					clHeader = encode.getClassHeader();

					ArrayList<EPackage> results = new ArrayList<EPackage>();
					ArrayList<EPackage> packages = new ArrayList<EPackage>();

					packages = exploreEcore(univEPackage, results);

					for (EPackage sub : packages) {

						for (EClassifier eClassifier : sub.getEClassifiers()) {

							if (eClassifier instanceof EClass) {
								EClass clazz = (EClass) eClassifier;

								if (clazz.getEAttributes().isEmpty()) {
									if (clazz.getEReferences().isEmpty()) {
										continue;
									}
								}

//	                        Adding classes
								classes.add(clazz.getName());
								clFoot = encode.getClassBody(univEPackage.getName(), clazz.getName());

//	                        System.out.println("Classes: "+clazz.getName());

								HashMap<String, HashMap<String, String>> categoryElements = new HashMap<String, HashMap<String, String>>();

								HashMap<String, String> attributes = new HashMap<String, String>();
								ArrayList<String> attr = new ArrayList<String>();

								for (EAttribute eAttribute : clazz.getEAttributes()) {
									attributes.put(eAttribute.getName(), eAttribute.getEType().getName());

//	                            Adding attribute to a list
									attr.add(eAttribute.getName());
									attr.add(eAttribute.getEType().getName());
									clAttr = clAttr + encode.getAttributes(eAttribute.getName(),
											eAttribute.getEType().getName()) + "\r\n";
//	                            System.out.println(eAttribute.getName()+" i tipit "+eAttribute.getEType().getName());

								}
								categoryElements.put("attributes", attributes);
//	                        Adding classes with the respetive attributes
								all_elements.put(clazz.getName(), attr);

								HashMap<String, String> reference = new HashMap<String, String>();
								ArrayList<String> connections = new ArrayList<String>();
								for (EReference eReference : clazz.getEReferences()) {
									reference.put(eReference.getName(), eReference.getEType().getName());

//	                            Addin connection informations

									connections.add(clazz.getName());
									connections.add(eReference.getEType().getName());
									connections.add(eReference.getName());
//	                            System.out.println(clazz.getName()+" me reference "+eReference.getName()+" e tipit "+eReference.getEType().getName());
									con = con + encode.getAssociation(univEPackage.getName(), clazz.getName(),
											eReference.getEType().getName()) + "\r\n";

									all_elements.put("Connections", connections);
								}
								categoryElements.put("reference", reference);
//								ecoreElements.put(clazz.getName(), categoryElements);
							}

						}

//            	   all_elements.put("Classes", classes);
					}

				}
				
				
			} catch (Exception e) {
				System.out.println(e);
				System.out.println(ecoreFile);
			}

		}
		
		footer = encode.getFooter();
		
//		String rdf = String.join(header, clHeader,clAttr,clFoot,con,footer);
		rdfFile = header+ "\t\n" + clHeader+ "\t\n"+ clAttr+ "\t\n" +attFooter+"\t\n" +clFoot+ "\t\n" +conHeder + "\t\n" +con+ "\t\n"+ conFooter + "\t\n"+ footer;
		
//		fileData.put(MName, rdfFile);
		
		String file = rdfFile;
//		System.out.println(rdfFile);
				
		header="";clHeader="";clAttr="";clFoot="";con="";footer = "";
		
		
		return file;
//        return ecoreElements;
	}

	public static void getEcoreData(HashMap<String, HashMap<String, HashMap<String, String>>> ecoreList, File filename,
			String label) {
		try {
			FileWriter fw = new FileWriter(filename);

			for (String key : ecoreList.keySet()) {
				HashMap<String, HashMap<String, String>> elem = ecoreList.get(key);

				StringBuilder sb = new StringBuilder();
				sb.append(label + "\t" + key + " ");

				HashMap<String, String> attributeMap = elem.get("attributes");

				for (String attrName : attributeMap.keySet()) {
					String typeAttr = attributeMap.get(attrName);

					sb.append('(' + attrName + ',' + typeAttr + ',' + "attribute)" + " ");
				}

				HashMap<String, String> refMap = elem.get("reference");

				for (String refName : refMap.keySet()) {
					String typeRef = refMap.get(refName);

					sb.append('(' + refName + ',' + typeRef + ',' + "ref)" + " ");
				}

				fw.write(sb.toString() + "\n");
			}

			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<EPackage> exploreEcore(EPackage univEPackage, ArrayList<EPackage> results) {
		EList<EPackage> subPackages = getSubPackage(univEPackage);

		if (results.size() < 1 && subPackages.size() < 1) {
			results.add(univEPackage);
		}

		for (int i = 0; i < subPackages.size(); i += 1) {

			if (getSubPackage(subPackages.get(i)).size() > 0) {
				exploreEcore(subPackages.get(i), results);
			} else {
				results.add(subPackages.get(i));

			}

		}
		return results;

	}

	public static EList<EPackage> getSubPackage(EPackage pack) {
		return pack.getESubpackages();
	}

}
