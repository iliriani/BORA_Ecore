package github2rdf;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.jena.graph.Graph;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.sparql.graph.GraphFactory;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFHandlerException;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.RDFWriter;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;

public class JsonToRdf {

	public static void automatizeConvertionzAppDevRDF() {
		
		String inputFolder = "C:\\Users\\Admin\\Desktop\\Forms\\FormModels\\Webforms\\";
		String outFolder = "C:\\Users\\Admin\\Desktop\\Forms\\zappdev forms\\";
		String rdfs = "C:\\Users\\Admin\\Desktop\\Forms\\FormModels\\RDFs\\";
		
//		remove comments
//		removeComments(inputFolder,outFolder);
		
//		add @Context
//		addContext(inputFolder,outFolder);
		
//		Convert JSON to JSON-LD
//		jsonToJsonld(inputFolder, outFolder);
		
//		Convert JSON-LD to RDF
		convert(outFolder,rdfs);
		
//		try {
//
//			File folder = new File("C:\\Users\\Admin\\Desktop\\Forms\\FormModels\\Webforms");
//			for (File file : folder.listFiles()) {
//				if (file.isFile()) {
//
//					String fileContent = Files.readString(file.toPath());
//
//					FileOutputStream out = new FileOutputStream(
//							"C:\\Users\\Admin\\Desktop\\Forms\\FormModels\\Webforms\\" + file.getName());
//					byte[] strToBytes = fileContent.getBytes();
//					out.write(strToBytes);
//					out.close();
//				}
//			}
//		}
//
//		catch (Exception e) {
//			e.printStackTrace();
//		}

	}

	public static void convert(String in, String outFolder) {

		try {

//			String data = "/artist.ttl";
			
			File folder = new File(in);
			for (File file : folder.listFiles()) {
				if (file.isFile()) {

//			String documentUrl = "C:\\Users\\Admin\\Desktop\\hakathon\\ElectricalEnergyConsumtionForm.form  with DataList.json";
//			String documentUrl = "C:\\Users\\Admin\\Desktop\\Forms\\FormModels\\Webforms\\ApplicationSettingForm.form.json";
//			String documentUrl = "C:\\Users\\Admin\\Desktop\\Forms\\zappdev forms\\ApplicationSettingForm.form.json";
			FileInputStream inputStream = new FileInputStream(file.getAbsolutePath());

//			Model results = Rio.parse(inputStream, "", RDFFormat.TURTLE);
//			results.forEach(System.out::println);
//			  Model model =
//			  Rio.parse(JsonToRdf.class.getResourceAsStream(data),"",RDFFormat.TURTLE);
//			  
//			  model.forEach(System.out::println);

			Model model = Rio.parse(inputStream, "",
					Rio.getParserFormatForFileName(file.getAbsolutePath()).orElse(RDFFormat.JSONLD));

//			FileOutputStream out = new FileOutputStream("C:\\Users\\Admin\\Desktop\\Forms\\FormModels\\Webforms\\BenBen.form.rdf");
			FileOutputStream out = new FileOutputStream(outFolder + file.getName().substring(0,file.getName().length()-5)+".rdf");

			Rio.write(model, out, RDFFormat.RDFXML);
				}}

//			  RDFParser parser =
//			  Rio.createParser(Rio.getParserFormatForFileName(documentUrl).orElse(RDFFormat.TURTLE
//			  )); RDFWriter writer = Rio.createWriter(RDFFormat.RDFJSON, System.out);
//			  parser.setRDFHandler(writer);
//			  parser.parse(inputStream, "");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void testForYourCode() {
//        String data = "{\"name\" : \"Andreas\",\"age\" : 20,\"profession\" : \"student\", \"personalWebsite\" : \"example.com\",\"@context\": \"http://schema.org/\"}";

		String path = "C:\\Users\\Admin\\Desktop\\Forms\\test\\stack.jsonld";
		String file = readAllBytesJava7(path);
//        System.out.println(file);

		try (InputStream in = new ByteArrayInputStream(file.getBytes("utf-8"))) {
			String dataAsRdf = readRdfToString(in, RDFFormat.JSONLD, RDFFormat.NTRIPLES, "");
			System.out.println(dataAsRdf);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param in      a rdf input stream
	 * @param inf     the rdf format of the input stream
	 * @param outf    the output format
	 * @param baseUrl usually the url of the resource
	 * @return a string representation
	 */
	public static String readRdfToString(InputStream in, RDFFormat inf, RDFFormat outf, String baseUrl) {
		Collection<Statement> myGraph = null;
		myGraph = readRdfToGraph(in, inf, baseUrl);
		return graphToString(myGraph, outf);
	}

	/**
	 * @param inputStream an Input stream containing rdf data
	 * @param inf         the rdf format
	 * @param baseUrl     see sesame docu
	 * @return a Graph representing the rdf in the input stream
	 */
	public static Collection<Statement> readRdfToGraph(final InputStream inputStream, final RDFFormat inf,
			final String baseUrl) {
		try {
			final RDFParser rdfParser = Rio.createParser(inf);
			final StatementCollector collector = new StatementCollector();
			rdfParser.setRDFHandler(collector);
			rdfParser.parse(inputStream, baseUrl);
			return collector.getStatements();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Transforms a graph to a string.
	 * 
	 * @param myGraph a sesame rdf graph
	 * @param outf    the expected output format
	 * @return a rdf string
	 */
	public static String graphToString(Collection<Statement> myGraph, RDFFormat outf) {
		StringWriter out = new StringWriter();
		RDFWriter writer = Rio.createWriter(outf, out);
		try {
			writer.startRDF();
			for (Statement st : myGraph) {
				writer.handleStatement(st);
			}
			writer.endRDF();
		} catch (RDFHandlerException e) {
			throw new RuntimeException(e);
		}
		return out.getBuffer().toString();
	}

	private static String readAllBytesJava7(String filePath) {
		String content = "";

		try {
			content = new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}

	public static void jsonToJsonld(String in, String outFolder) {

		try {
			File folder = new File(in);
			for (File file : folder.listFiles()) {
				if (file.isFile()) {
			
//			String file = "C:\\\\Users\\\\Admin\\\\Desktop\\\\Forms\\\\FormModels\\\\Webforms\\\\ApplicationSettingForm.form.xml";

			// Open a valid json(-ld) input file
			InputStream inputStream = new FileInputStream(file);

			Object jsonObject = JsonUtils.fromInputStream(inputStream);
			// Create a context JSON map containing prefixes and definitions
			Map context = new HashMap();
//			System.out.println(jsonObject);

			// Create an instance of JsonLdOptions with the standard JSON-LD options
			JsonLdOptions options = new JsonLdOptions();

			// Convert to JSONLD
			Object compact = JsonLdProcessor.compact(jsonObject, context, options);
//    	System.out.println("Compact is: "+compact);
//    	Object compact = JsonLdProcessor.compact(jsonObject, new HashMap<>(), new JsonLdOptions());

			// Print out the result (or don't, it's your call!)
//			System.out.println(JsonUtils.toPrettyString(compact));

//			FileWriter myWriter = new FileWriter(
//					"C:\\Users\\Admin\\Desktop\\Forms\\FormModels\\Webforms\\ApplicationSettingForm.form.json");
//			myWriter.write(JsonUtils.toPrettyString(compact));
//			myWriter.close();
			String text = JsonUtils.toPrettyString(compact);
			FileOutputStream out = new FileOutputStream(outFolder + file.getName().substring(0,file.getName().length()-4)+".json");
			byte[] strToBytes = text.getBytes();
			out.write(strToBytes);
			out.close();
			
				}
				}

		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void removeComments(String in, String outFolder) {

		try {

//			File folder = new File("C:\\Users\\Admin\\Desktop\\test");
			File folder = new File(in);
			for (File file : folder.listFiles()) {
				if (file.isFile())

					try {

						String fileContent = Files.readString(file.toPath());

						ObjectMapper mapper = new ObjectMapper();
						mapper.enable(JsonParser.Feature.ALLOW_COMMENTS);
						String withoutComment = mapper.writeValueAsString(mapper.readTree(fileContent));

//			        	System.out.println(withoutComment);

//						FileOutputStream out = new FileOutputStream("C:\\Users\\Admin\\Desktop\\test\\" + file.getName());
						FileOutputStream out = new FileOutputStream(outFolder + file.getName());
						byte[] strToBytes = withoutComment.getBytes();
						out.write(strToBytes);
						out.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

//				    System.out.println("-----------------------------------------------");    
			}

//		    	FileOutputStream out = new FileOutputStream("C:\\Users\\Admin\\Desktop\\formRDF.rdf");

		} catch (Exception e) {
//				System.out.println("Problem with the file: "+fileName);
			e.printStackTrace();
		}
	}

	public static void addContext(String in, String outFolder) {

		try {

//			File folder = new File("C:\\Users\\Admin\\Desktop\\Forms\\FormModels\\Webforms");
			File folder = new File(in);
			for (File file : folder.listFiles()) {
				if (file.isFile())

					try {

						String fileContent = Files.readString(file.toPath());

						for (int i = 0; i < fileContent.length(); i++) {
							if (fileContent.charAt(i) == '{') {

								String context = "{\r\n" + "  \"@context\": [\r\n"
										+ "    \"https://openactive.io/\",\r\n"
										+ "    \"https://openactive.io/ns-beta\"\r\n" + "  ],\r\n";

								fileContent = fileContent.substring(0, i) + context + fileContent.substring(i + 1);
								break;

							}
						}

						System.out.println(fileContent);

//						FileOutputStream out = new FileOutputStream("C:\\Users\\Admin\\Desktop\\Forms\\FormModels\\Webforms\\" + file.getName());
						FileOutputStream out = new FileOutputStream(outFolder + file.getName());
						byte[] strToBytes = fileContent.getBytes();
						out.write(strToBytes);
						out.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

//				    System.out.println("-----------------------------------------------");    
			}

//		    	FileOutputStream out = new FileOutputStream("C:\\Users\\Admin\\Desktop\\formRDF.rdf");

		} catch (Exception e) {
//				System.out.println("Problem with the file: "+fileName);
			e.printStackTrace();
		}

	}

}
