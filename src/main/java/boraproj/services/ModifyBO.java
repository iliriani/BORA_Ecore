package github2rdf;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ModifyBO {

	public void modAll() {

		File folder = new File("C:\\Users\\Admin\\Desktop\\Forms\\FormModels\\BusinessObjects");
//		Model kg = ModelFactory.createDefaultModel().read("C:\\Users\\Admin\\Desktop\\KG\\Orders.rdf");

		File[] listOfFiles = folder.listFiles(); // Interating through the directory
		for (File file : listOfFiles) {
			if (file.isFile()) {
				modify(file.getAbsolutePath());

			}
		}

	}

	public void modify(String filepath) {

		try {

//             filepath = "C:\\Users\\Admin\\Desktop\\BOs\\AccessComponent.bo.xml";

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			// Get the root element Node data= doc.getFirstChild();

//			  System.out.println(data.getNodeValue().toString());

			Element bo = (Element) doc.getElementsByTagName("BusinessObject").item(0);
			bo.setAttribute("xmlns", "http://www.w3.org/TR/html4/");

			// System.out.println(bo.getAttributes().getNamedItem("DateCreated"));

			// write the content into xml file TransformerFactory transformerFactory =
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);

//			  System.out.println("Done");

			/*
			 * DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			 * dbf.setValidating(false); DocumentBuilder db = dbf.newDocumentBuilder();
			 * 
			 * Document doc1 = db.parse(new FileInputStream(new File(filepath)));
			 * 
			 * Element element = (Element)
			 * doc1.getElementsByTagName("BusinessObject").item(0);
			 * 
			 * // Adds a new attribute. If an attribute with that name is already present //
			 * in the element, its value is changed to be that of the value parameter
			 * element.setAttribute("xmlns", "\"http://www.w3.org/TR/html4/\\");
			 * 
			 * prettyPrint(doc1);
			 * 
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//        public static final void prettyPrint(Document xml) throws Exception {
//            Transformer tf = TransformerFactory.newInstance().newTransformer();
//            tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//            tf.setOutputProperty(OutputKeys.INDENT, "yes");
//            Writer out = new StringWriter();
//            tf.transform(new DOMSource(xml), new StreamResult(out));
//            System.out.println(out.toString());}

}