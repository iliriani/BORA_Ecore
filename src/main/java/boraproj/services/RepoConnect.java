package boraproj.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.jena.atlas.lib.StrUtils;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.core.Quad;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.tdb.TDBLoader;
import org.apache.jena.update.GraphStore;
import org.apache.jena.update.GraphStoreFactory;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.stereotype.Service;

//public class StoretoTDB implements Runnable{

@Service
public class RepoConnect {
	private String directory = "../BORA/src/main/resources/repository";
	
//	For linkux path
//	private String directory = "repository";
	
	public static ArrayList<String> retrievedItems;

	
	public RepoConnect() {}
	
	public RepoConnect(String directory) {

		this.directory = directory;
	}

	/*
	 * public StoretoTDB(String directory, String entity) {
	 * 
	 * this.directory = directory; this.entity = entity; }
	 */

	public Dataset createDataset(String file, String directory) {
		try {
			Dataset dataset = TDBFactory.createDataset(directory);
			dataset.begin(ReadWrite.WRITE);
			Model model = dataset.getDefaultModel();
			TDBLoader.loadModel(model, file);
			dataset.commit();
			dataset.end();
			return dataset;
		} catch (Exception ex) {
			System.out.println("##### Error Function: createDataset #####");
			System.out.println(ex.getMessage());
			return null;
		}
	}

	public void read() {

		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.READ);
		try {
			Iterator<Quad> iter = d.asDatasetGraph().find();
			int i = 0;
			System.out.println("begin ");
			while (iter.hasNext() && i < 20) {
				Quad quad = iter.next();
				System.out.println("iteration " + i);
				System.out.println(quad);
				i++;
			}
		} finally {
			d.end();
		}
		d.close();
		System.out.println("finish ...");

	}
	
	
	
	
	// Getting all connected classes in the repository	
		public ArrayList getAllClasses() {

			ArrayList<String> items = new ArrayList<String>();
			Dataset d = TDBFactory.createDataset(directory);
			Model model = d.getDefaultModel();
			d.begin(ReadWrite.READ);

			String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> \r\n"
					+ "base <http://www.w3.org/TR/html4/> \r\n"
					+ "SELECT  DISTINCT ?classes\r\n"
					+ "WHERE { ?s ?o  ?p;\r\n"
					+ "           <Class1> | <Class2> ?classes . } ";
		
			Query query = QueryFactory.create(sparqlQueryString);
			QueryExecution qexec = QueryExecutionFactory.create(query, d);
//			System.out.println("Query executed!");
			try {
				ResultSet results = qexec.execSelect();
				for (; results.hasNext();) {
					QuerySolution soln = results.nextSolution();
					String whole = soln.toString();
					String core = whole.substring(14, whole.length() - 3);
					items.add(core);

				}

				d.commit();
			} finally {
				qexec.close();
				// Close the dataset.
				d.end();
			}

			return items;

		}
		
		
		// Getting all connected non-distinct classes in the repository	
		public ArrayList getAllNonDistinctClasses() {

			ArrayList<String> items = new ArrayList<String>();
			Dataset d = TDBFactory.createDataset(directory);
			Model model = d.getDefaultModel();
			d.begin(ReadWrite.READ);

			String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> \r\n"
					+ "base <http://www.w3.org/TR/html4/> \r\n"
					+ "SELECT  ?classes\r\n"
					+ "WHERE { ?s <Class1> | <Class2> ?classes . } ";
		
			Query query = QueryFactory.create(sparqlQueryString);
			QueryExecution qexec = QueryExecutionFactory.create(query, d);
//			System.out.println("Query executed!");
			try {
				ResultSet results = qexec.execSelect();
				for (; results.hasNext();) {
					QuerySolution soln = results.nextSolution();
					String whole = soln.toString();
					String core = whole.substring(14, whole.length() - 3);
					items.add(core);

				}

				d.commit();
			} finally {
				qexec.close();
				// Close the dataset.
				d.end();
			}

			return items;

		}
	
	
	
	public ArrayList getRelatedClasses(String cl) {

		ArrayList<String> items = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.READ);
		String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> \r\n"
				+ "base <http://www.w3.org/TR/html4/> \r\n"
				+ "SELECT DISTINCT ?p\r\n"
				+ "WHERE { ?s ?o  \"" +cl+ "\" ;\r\n"
				+ "		 <Class1> | <Class2> ?p . } ";
		// See http://incubator.apache.org/jena/documentation/query/app_api.html
		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, d);
//		System.out.println("Query executed!");
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				String whole = soln.toString();
				String core = whole.substring(8, whole.length() - 3);
				items.add(core);
//				System.out.println(core);
			}

			d.commit();
		} finally {
			qexec.close();
			// Close the dataset.
			d.end();
		}

		return items;

	}
	
	public ArrayList<String> getRelatedClassesForNLP(String cl) {

		ArrayList<String> items = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.READ);
		String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#>\r\n"
				+ "base <http://www.w3.org/TR/html4/>\r\n"
				+ "	SELECT DISTINCT ?p\r\n"
				+ "	WHERE { ?s ?o  ?cl ;\r\n"
				+ "	 <Class1> | <Class2> ?p . \r\n"
				+ "FILTER regex(?cl,\""+cl+"\", \"i\").\r\n"
				+ "}  ";
		// See http://incubator.apache.org/jena/documentation/query/app_api.html
		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, d);
//		System.out.println("Query executed!");
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				String whole = soln.toString();
				String core = whole.substring(8, whole.length() - 3);
				items.add(core);
//				System.out.println(core);
			}

			d.commit();
		} finally {
			qexec.close();
			// Close the dataset.
			d.end();
		}

		return items;

	}
	

	
	public ArrayList<String> getTokenMatchedClasses(String cl) {

		ArrayList<String> items = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.READ);
		String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#>\r\n"
				+ "base <http://www.w3.org/TR/html4/>\r\n"
				+ "	SELECT DISTINCT ?p\r\n"
				+ "{ ?s <Class1> | <Class2> ?p \r\n"
				+ "  FILTER (regex(?p,\""+cl+"\", \"i\")).\r\n"
				+ "} ";
		// See http://incubator.apache.org/jena/documentation/query/app_api.html
		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, d);
//		System.out.println("Query executed!");
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				String whole = soln.toString();
				String core = whole.substring(8, whole.length() - 3);
				items.add(core);
//				System.out.println(core);
			}

			d.commit();
		} finally {
			qexec.close();
			// Close the dataset.
			d.end();
		}

		return items;

	}
	
	
	
	
// Getting all classes from a given domain	
	public ArrayList getRelatedDomainClasses(String domain) {

		ArrayList<String> items = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.READ);

		String sparqlQueryString = "prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> base <http://www.w3.org/TR/html4/> \r\n"
				+ "\r\n"
				+ "SELECT DISTINCT ?classes\r\n"
				+ "WHERE {\r\n"
				+ "  		 ?s  <ModelName> \"" +domain+ "\";\r\n"
				+ "       		 <Class1> | <Class2> ?classes.\r\n"
				+ "}";
	
		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, d);
//		System.out.println("Query executed!");
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				String whole = soln.toString();
				String core = whole.substring(14, whole.length() - 3);
				items.add(core);

			}

			d.commit();
		} finally {
			qexec.close();
			// Close the dataset.
			d.end();
		}

		return items;

	}
	
	
	public ArrayList<String> getRelatedDomainClassesForNLP(String domain) {

		ArrayList<String> items = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.READ);

		String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> \r\n"
				+ "base <http://www.w3.org/TR/html4/> \r\n"
				+ "	SELECT DISTINCT ?classes\r\n"
				+ "		WHERE {\r\n"
				+ "			?s  <ModelName> ?domain;\r\n"
				+ "			<Class1> | <Class2> ?classes.\r\n"
				+ "		FILTER regex(?domain, \""+domain+"\", \"i\").	\r\n"
				+ "	} ";
	
		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, d);
//		System.out.println("Query executed!");
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				String whole = soln.toString();
				String core = whole.substring(14, whole.length() - 3);
				items.add(core);

			}

			d.commit();
		} finally {
			qexec.close();
			// Close the dataset.
			d.end();
		}

		return items;

	}
	
	
	
	
	public ArrayList<String> getClasses() {

//		this.entity = entity;
		ArrayList<String> items = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.READ);
		String sparqlQueryString = "SELECT DISTINCT ?o\r\n"
				+ "WHERE {\r\n"
				+ "		?s ?p ?o .\r\n"
				+ "		?s <http://www.w3.org/TR/html4/Class1> |\r\n"
				+ "		<http://www.w3.org/TR/html4/Class2> ?o . }";
		
		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, d);
//		System.out.println("Query executed!");
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				String whole = soln.toString();
				String core = whole.substring(8, whole.length() - 3);
				items.add(core);
//				System.out.println(soln);
//				int count = soln.getLiteral("count").getInt();
//				System.out.println("count = " + count);
			}

			d.commit();
		} finally {
			qexec.close();
			// Close the dataset.
			d.end();
		}

		return items;

	}
	
	
	public ArrayList<String> getNonDistinctClasses() {

//		this.entity = entity;
		ArrayList<String> items = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.READ);
		String sparqlQueryString = "SELECT ?o\r\n"
				+ "WHERE {\r\n"
				+ "		?s ?p ?o .\r\n"
				+ "		?s <http://www.w3.org/TR/html4/Class1> |\r\n"
				+ "		<http://www.w3.org/TR/html4/Class2> ?o . }";
		
		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, d);
//		System.out.println("Query executed!");
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				String whole = soln.toString();
				String core = whole.substring(8, whole.length() - 3);
				items.add(core);
//				System.out.println(soln);
//				int count = soln.getLiteral("count").getInt();
//				System.out.println("count = " + count);
			}

			d.commit();
		} finally {
			qexec.close();
			// Close the dataset.
			d.end();
		}

		return items;

	}
	
	
// Determin the domain where class1 and class2 belongs	
	public ArrayList<String> getDomain(String class1, String class2) {


		ArrayList<String> items = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.READ);
		String sparqlQueryString = "prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> base <http://www.w3.org/TR/html4/> \r\n"
				+ "SELECT DISTINCT ?modelname\r\n"
				+ "WHERE {\r\n"
				+ "		?s <ModelName> ?modelname ;\r\n"
				+ "  			<Class1> | <Class2> \"" +class1+ "\";\r\n"
				+ "  			<Class1> | <Class2> \"" +class2+ "\";\r\n"
				+ "          \r\n"
				+ "}";
		
		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, d);
//		System.out.println("Query executed!");
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				String whole = soln.toString();
				//Remove the ( ?p = " ") part
				String core = whole.substring(16, whole.length() - 3);
				items.add(core);

			}

			d.commit();
		} finally {
			qexec.close();
			// Close the dataset.
			d.end();
		}

		return items;

	}	
	
	public ArrayList<String> getIslandClasses(String domain) {

		ArrayList<String> items = new ArrayList<String>();
		items = this.getAllDomainClasses(domain);
		ArrayList<String> related_classes = this.getRelatedClasses(domain);
		items.removeAll(related_classes);
		return items;

	}
	
	public ArrayList<String> getAllDomainClasses(String domain) {

		ArrayList<String> items = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.READ);

		String sparqlQueryString = "prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> base <http://www.w3.org/TR/html4/> \r\n"
				+ "SELECT DISTINCT ?classes\r\n"
				+ "WHERE {\r\n"
				+ "  		 ?s  <ModelName> \""+domain+"\";\r\n"
				+ "       		 <Name> ?classes.\r\n"
				+ "}";
	
		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, d);
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				String whole = soln.toString();
				String core = whole.substring(14, whole.length() - 3);
				items.add(core);

			}

			d.commit();
		} finally {
			qexec.close();
			// Close the dataset.
			d.end();
		}

		return items;

	}
	
	
//	Returns all classes from the KG (Including the islandes)
	public ArrayList<String> getAllClassesFromKG() {

		ArrayList<String> items = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.READ);

		String sparqlQueryString = "prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> base <http://www.w3.org/TR/html4/> \r\n"
				+ "SELECT DISTINCT ?classes\r\n"
				+ "WHERE {\r\n"
				+ "  		 ?s <Name> ?classes.\r\n"
//				+ "  		 ?s <Class> ?classes.\r\n"
				+ "}";
	
		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, d);
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				String whole = soln.toString();
				String core = whole.substring(14, whole.length() - 3);
				items.add(core);

			}

			d.commit();
		} finally {
			qexec.close();
			// Close the dataset.
			d.end();
		}

		return items;

	}
	
	
//	Returns all classes from the KG (Including the islandes)
	public ArrayList<String> getAllNonDistClassesFromKG() {

		ArrayList<String> items = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.READ);

		String sparqlQueryString = "prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> base <http://www.w3.org/TR/html4/> \r\n"
				+ "SELECT  ?classes\r\n"
				+ "WHERE {\r\n"
				+ "  		 ?s <Name> ?classes.\r\n"
//				+ "  		 ?s <Class> ?classes.\r\n"
				+ "}";
	
		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, d);
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				String whole = soln.toString();
				String core = whole.substring(14, whole.length() - 3);
				items.add(core);

			}

			d.commit();
		} finally {
			qexec.close();
			// Close the dataset.
			d.end();
		}

		return items;

	}
	

	public ArrayList query_twograms(String ent1, String ent2) {

		ArrayList<String> items1 = new ArrayList<String>();
		ArrayList<String> items2 = new ArrayList<String>();
		try {
			items1 = this.getRelatedClasses(ent1);
		} finally {
		}

		try {
			items2 = this.getRelatedClasses(ent2);
		} finally {
		}

		items1.addAll(items2);
		
		ArrayList<String> items = new ArrayList<String>(new HashSet<String>(items1));
		
		return items;
	}
	
	
	
	
	
	public ArrayList<String> getAttributes(String cl) {
		

		ArrayList<String> attributes = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.READ);
		String sparqlQueryString = "prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> base <http://www.w3.org/TR/html4/> \r\n"
				+ "select distinct ?o \r\n"
				+ "{   ?x <Name> \""+cl+"\" ;  \r\n"
				+ "       :hasChild/:hasChild/<Name> ?o .  }";
		
		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, d);
//		System.out.println("getAttribute excequted!");
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				String whole = soln.toString();
				String core = whole.substring(8, whole.length() - 3);
				attributes.add(core);
//				System.out.println(core);

			}

			d.commit();
		} finally {
			qexec.close();
			// Close the dataset.
			d.end();
		}

		return attributes;
		
	}
	
	
	
	public ArrayList<String> getClassFromAttributes(List<String> attr) {
		

		ArrayList<String> attributes = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.READ);
		String atr = "";
		if(attr.size() == 1) { atr += " regex(?atr,\""+attr.get(0)+"\", \"i\") ";}
		else {
		for(int i=0; i<attr.size()-1;i++) {
//			atr += "?atr = \""+attr.get(i)+"\" || ";
			atr += " regex(?atr,\""+attr.get(i)+"\", \"i\")  || ";
		}
//		atr += "?atr = \""+attr.get(attr.size()-1)+"\"";
		atr += " regex(?atr,\""+attr.get(attr.size()-1)+"\", \"i\") ";
		}

		String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> \r\n"
				+ "base <http://www.w3.org/TR/html4/>\r\n"
				+ "SELECT DISTINCT ?cl\r\n"
				+ "{\r\n"
				+ "	?s <Name> ?cl;\r\n"
				+ "    :hasChild/:hasChild/<Name> ?atr\r\n"
				+ "  	FILTER ( "+atr+" ).\r\n"
				+ "                         \r\n"
				+ "} LIMIT 10 ";

		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, d);
//		System.out.println(sparqlQueryString);
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				String whole = soln.toString();
				String core = whole.substring(9, whole.length() - 3);
				attributes.add(core);
//				System.out.println(core);

			}

			d.commit();
		} finally {
			qexec.close();
			// Close the dataset.
			d.end();
		}

		return attributes;
		
	}
	
	public ArrayList<String> getClassFromANDAttributes(List<String> attr) {
		

		ArrayList<String> attributes = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.READ);
		String atr = "";
		if(attr.size() == 1) { atr += " regex(?atr,\""+attr.get(0)+"\", \"i\") ";}
		else {
		for(int i=0; i<attr.size()-1;i++) {
//			atr += "?atr = \""+attr.get(i)+"\" || ";
			atr += " regex(?atr,\""+attr.get(i)+"\", \"i\")  && ";
		}
//		atr += "?atr = \""+attr.get(attr.size()-1)+"\"";
		atr += " regex(?atr,\""+attr.get(attr.size()-1)+"\", \"i\") ";
		}

		String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> \r\n"
				+ "base <http://www.w3.org/TR/html4/>\r\n"
				+ "SELECT DISTINCT ?cl\r\n"
				+ "{\r\n"
				+ "	?s <Name> ?cl;\r\n"
				+ "    :hasChild/:hasChild/<Name> ?atr\r\n"
				+ "  	FILTER ( "+atr+" ).\r\n"
				+ "                         \r\n"
				+ "} LIMIT 10 ";

		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, d);
//		System.out.println(sparqlQueryString);
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				String whole = soln.toString();
				String core = whole.substring(9, whole.length() - 3);
				attributes.add(core);
//				System.out.println(core);

			}

			d.commit();
		} finally {
			qexec.close();
			// Close the dataset.
			d.end();
		}

		return attributes;
		
	}
	
	
	public ArrayList<String> getAllDomainAttributes(String domain) {
		

		ArrayList<String> attributes = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.READ);
		String sparqlQueryString = "prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> base <http://www.w3.org/TR/html4/> \r\n"
				+ "SELECT DISTINCT ?o \r\n"
				+ "WHERE {   ?x <ModelName> \""+domain+"\" ;  \r\n"
				+ "       :hasChild/:hasChild/<Name> ?o . }";
		
		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, d);
//		System.out.println("getAttribute excequted!");
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				String whole = soln.toString();
				String core = whole.substring(8, whole.length() - 3);
				attributes.add(core);
//				System.out.println(core);

			}

			d.commit();
		} finally {
			qexec.close();
			// Close the dataset.
			d.end();
		}

		return attributes;
		
	}
	
	
	
	
	
	

	public ArrayList getConnectionFrekueny(String cl1, String cl2) {

		ArrayList<String> items = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.READ);
		String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> \r\n"
				+ "base <http://www.w3.org/TR/html4/> \r\n"
				+ "SELECT  (count(?p) as ?Amount)\r\n"
				+ "WHERE { ?s ?o  \""+cl1+ "\";\r\n"
				+ "		 <Class1> | <Class2> ?p . \r\n"
				+ "  FILTER regex(?p , \""+cl2+"\") } ";
		// See http://incubator.apache.org/jena/documentation/query/app_api.html
		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, d);
//		System.out.println("Query executed!");
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				
				String whole = soln.toString();
				String core = whole.substring(12, whole.length() - 2);
				items.add(core);
//				System.out.println(core);
			}

			d.commit();
		} finally {
			qexec.close();
			// Close the dataset.
			d.end();
		}

		return items;
	
	}
	
	
	
	public void deleteRepo() {

		ArrayList<String> items = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.WRITE);
		  
		  // ... perform a SPARQL Update 
		GraphStore graphStore = GraphStoreFactory.create(d) ; 
		String sparqlUpdateString = StrUtils.strjoinNL(
		  " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> \r\n" +
		  "base <http://www.w3.org/TR/html4/> \r\n" 
				  + " CLEAR ALL " ) ;
		  
		  UpdateRequest request = UpdateFactory.create(sparqlUpdateString) ;
		  UpdateProcessor proc = UpdateExecutionFactory.create(request, graphStore) ;
		  proc.execute() ;
		 
		
		
		try {
			d.commit();
			System.out.println("Deleted!");

		} finally {
//			qexec.close();
			// Close the dataset.
			d.end();
		}

	}
	
	
	public void insertWeight(String cl1, String cl2, int w) {

		ArrayList<String> items = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.WRITE);
		/*
		 * String sparqlQueryString =
		 * " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> \r\n" +
		 * "base <http://www.w3.org/TR/html4/> \r\n" + "CONSTRUCT {\r\n" +
		 * "	?s <w> "+w+" .\r\n" + "}\r\n" + "\r\n" + "Where {\r\n" +
		 * "	?s <Class1> | <Class2> \""+cl1+ "\";\r\n" +
		 * "               <Class1> | <Class2> \""+cl2+"\";\r\n" + "      } "; // See
		 * http://incubator.apache.org/jena/documentation/query/app_api.html Query query
		 * = QueryFactory.create(sparqlQueryString); QueryExecution qexec =
		 * QueryExecutionFactory.create(query, d);
		 */
		
		
		
		/*
		 * ArrayList<String> items = new ArrayList<String>(); Dataset d =
		 * TDBFactory.createDataset(directory); Model model = d.getDefaultModel();
		 * d.begin(ReadWrite.READ);
		 */
		  
		  
		  
		  // ... perform a SPARQL Update 
		GraphStore graphStore = GraphStoreFactory.create(d) ; 
		String sparqlUpdateString = StrUtils.strjoinNL(
		  " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> \r\n" +
		  "base <http://www.w3.org/TR/html4/> \r\n" 
				  + "INSERT {\r\n" + "	?s <w> "+ w
		  +" .\r\n" + "}\r\n" + "\r\n" + "Where {\r\n" +
		  "	?s <Class1> | <Class2> \""+cl1+"\";\r\n" 
		  +
		  "               <Class1> | <Class2> \""+cl2+"\";\r\n" + "      } " ) ;
		  
		  UpdateRequest request = UpdateFactory.create(sparqlUpdateString) ;
		  UpdateProcessor proc = UpdateExecutionFactory.create(request, graphStore) ;
		  proc.execute() ;
		 
		
		
		try {
			d.commit();
//			Model results = qexec.execConstruct();
			System.out.println("Insert has been executed successfully!");
			/*
			 * for (; results.hasNext();) { QuerySolution soln = results.nextSolution();
			 * 
			 * String whole = soln.toString(); String core = whole.substring(12,
			 * whole.length() - 2); items.add(core); // System.out.println(core); }
			 * 
			 * d.commit();
			 */
		} finally {
//			qexec.close();
			// Close the dataset.
			d.end();
		}

//		return items;
		

		
	
	}
	
	
	public void updateWeight(String cl1, String cl2, int w, int w_updated) {

		ArrayList<String> items = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.WRITE);
//		int updated_weight = w+1;  
		  
		  // ... perform a SPARQL Update 
		GraphStore graphStore = GraphStoreFactory.create(d) ; 
		String sparqlUpdateString = StrUtils.strjoinNL(
		  " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> \r\n"
		  + "base <http://www.w3.org/TR/html4/> \r\n"
		  + "DELETE {\r\n"
		  + "	?s <w> "+w+".\r\n"
		  + "}\r\n"
		  + "\r\n"
		  + "INSERT {\r\n"
		  + "	?s <w> "+w_updated+".\r\n"
		  + "}\r\n"
		  + "\r\n"
		  + "WHERE {\r\n"
		  + "	?s <Class1> | <Class2> \""+cl1+"\";\r\n"
		  + "               <Class1> | <Class2> \""+cl2+"\";\r\n"
		  + "      } " ) ;
		  
		  UpdateRequest request = UpdateFactory.create(sparqlUpdateString) ;
		  UpdateProcessor proc = UpdateExecutionFactory.create(request, graphStore) ;
		  proc.execute() ;
		 
		
		
		try {
			d.commit();
			System.out.println("Update has been executed successfully! "+w_updated);

		} finally {
//			qexec.close();
			// Close the dataset.
			d.end();
		}
	
	
	}

//	Get the weight (term frequency) between cl1 and cl2
	public ArrayList getWeights(String cl1, String cl2) {

		ArrayList<String> items = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.READ);
		String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> \r\n"
				+ "base <http://www.w3.org/TR/html4/>\r\n"
				+ "SELECT ?weight {\r\n"
				+ "	?s <Class1> | <Class2> \""+cl1+"\";\r\n"
				+ "       <Class1> | <Class2> \""+cl2+"\";\r\n"
				+ "                  <w> ?weight.\r\n"
				+ "}\r\n"
				+ "LIMIT 1 ";
		// See http://incubator.apache.org/jena/documentation/query/app_api.html
		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, d);
//		System.out.println("Query executed!");
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				
				String whole = soln.toString();
				String core = whole.substring(12, whole.length() - 2);
				items.add(core);
//				System.out.println(core);
			}

			d.commit();
		} finally {
			qexec.close();
			// Close the dataset.
			d.end();
		}

		return items;
	
	}
	
	
// Returns the datatypes of the respective attributes	
	public ArrayList<String> getDataTypes(String cl, int nr) {

		ArrayList<String> items = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.READ);

		String sparqlQueryString = "prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> base <http://www.w3.org/TR/html4/> \r\n"
				+ "SELECT ?d_type \r\n"
				+ "{   ?x <Name> \""+cl+"\" ;  \r\n"
				+ "		:hasChild/:hasChild/<DataType> ?d_type .} LIMIT "+nr;
	
		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, d);
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				String whole = soln.toString();
				String core = whole.substring(13, whole.length() - 3);
				items.add(core);

			}

			d.commit();
		} finally {
			qexec.close();
			// Close the dataset.
			d.end();
		}

		return items;

	}
	
	// Returns the datatype of the respective attribute	
		public String getDataType(String attr) {

			ArrayList<String> items = new ArrayList<String>();
			Dataset d = TDBFactory.createDataset(directory);
			Model model = d.getDefaultModel();
			d.begin(ReadWrite.READ);

			String sparqlQueryString = "prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> base <http://www.w3.org/TR/html4/> \r\n"
					+ "SELECT DISTINCT ?d_type \r\n"
					+ "{   ?x <Name> \""+attr+"\" ;\r\n"
					+ "		<DataType> ?d_type .} LIMIT 1";
		
			Query query = QueryFactory.create(sparqlQueryString);
			QueryExecution qexec = QueryExecutionFactory.create(query, d);
			try {
				ResultSet results = qexec.execSelect();
				for (; results.hasNext();) {
					QuerySolution soln = results.nextSolution();
					String whole = soln.toString();
					String core = whole.substring(13, whole.length() - 3);
					items.add(core);

				}

				d.commit();
			} 
			
			finally {
				qexec.close();
				// Close the dataset.
				d.end();
			}
			
			try {
				String s = items.get(0);
				
//				Replave all Status enumerations with string
				if(s.matches(".*Status.*")) { return "string";}
				
			} catch (Exception e) {
//				System.out.println("Error occured");
				return "string";
			}
			
			return items.get(0);

		}
	
		
//		Get the multiplicity of all connections for a given class
		public ArrayList<String> getConnectionMultiplicity(String cl) {
			
//			String[] multiplicity = new String[2];
			ArrayList<String> multiplicity = new ArrayList<String>();
			ArrayList<String> items = new ArrayList<String>();
			Dataset d = TDBFactory.createDataset(directory);
			Model model = d.getDefaultModel();
			d.begin(ReadWrite.READ);

			String sparqlQueryString = "prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> \r\n"
					+ "base <http://www.w3.org/TR/html4/>\r\n"
					+ "SELECT DISTINCT ?multiplicity1 ?multiplicity2\r\n"
					+ "{\r\n"
					+ "	?s <Class1> | <Class2> \""+cl+"\";\r\n"
					+ "                  <Multiplicity1> ?multiplicity1;\r\n"
					+ "                  <Multiplicity2> ?multiplicity2.\r\n"
					+ "}";
		
			Query query = QueryFactory.create(sparqlQueryString);
			QueryExecution qexec = QueryExecutionFactory.create(query, d);
			try {
				ResultSet results = qexec.execSelect();
				for (; results.hasNext();) {
					QuerySolution soln = results.nextSolution();
					RDFNode node1 = soln.get("multiplicity1");
					RDFNode node2 = soln.get("multiplicity2");
				    String multi1 = node1.asLiteral().getString();
				    String multi2 = node2.asLiteral().getString();
				    
				    String m = multi1 + " - " +multi2;
				    multiplicity.add(m);
//				    System.out.println("Multi1 is: "+multi1+"  "+multi2);


				}

				d.commit();
			} finally {
				qexec.close();
				// Close the dataset.
				d.end();
			}

			return multiplicity;

		}
		
		
	
//	Get the multiplicity of the connection between cl1 and cl2
	public String[] getConnectionMultiplicity(String cl1, String cl2) {
		
		String[] multiplicity = new String[2];  
		ArrayList<String> items = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.READ);

		String sparqlQueryString = "prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> \r\n"
				+ "base <http://www.w3.org/TR/html4/>\r\n"
				+ "SELECT ?multiplicity1 ?multiplicity2\r\n"
				+ "{\r\n"
				+ "	?s <Class1> | <Class2> \""+cl1+"\";\r\n"
				+ "       <Class1> | <Class2> \""+cl2+"\";\r\n"
				+ "                  <Multiplicity1> ?multiplicity1;\r\n"
				+ "                  <Multiplicity2> ?multiplicity2.\r\n"
				+ "}\r\n"
				+ "LIMIT 1";
	
		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, d);
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				RDFNode node1 = soln.get("multiplicity1");
				RDFNode node2 = soln.get("multiplicity2");
			    String multi1 = node1.asLiteral().getString();
			    String multi2 = node2.asLiteral().getString();
			    
			    multiplicity[0] = multi1;
			    multiplicity[1] = multi2;
//			    System.out.println("Multi1 is: "+multi1+"  "+multi2);


			}

			d.commit();
		} finally {
			qexec.close();
			// Close the dataset.
			d.end();
		}

		return multiplicity;

	}
	
	
//	Get all names of the classes had in any connection
	public ArrayList<String> getRoleNames(String cl) {

		ArrayList<String> items = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.READ);

		String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> \r\n"
				+ "base <http://www.w3.org/TR/html4/>\r\n"
				+ "SELECT DISTINCT ?Role\r\n"
				+ "{\r\n"
				+ "	?s <Class1> | <Class2> \""+cl+"\";\r\n"
				+ "       <Role1> | <Role2> ?Role.\r\n"
				+ "} ";
	
		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, d);
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				String whole = soln.toString();
				String core = whole.substring(11, whole.length() - 3);
				items.add(core);

			}

			d.commit();
		} finally {
			qexec.close();
			// Close the dataset.
			d.end();
		}

		return items;

	}
	
	

//	Get the Roles names of the classes had on a connection
public String[] getConnectionRoleNames(String cl1, String cl2) {
		
		String[] roles = new String[2];  
		ArrayList<String> items = new ArrayList<String>();
		Dataset d = TDBFactory.createDataset(directory);
		Model model = d.getDefaultModel();
		d.begin(ReadWrite.READ);

		String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> \r\n"
				+ "base <http://www.w3.org/TR/html4/>\r\n"
				+ "SELECT ?Role2 ?Role1\r\n"
				+ "{\r\n"
				+ "	?s <Class1> | <Class2> \""+cl1+"\";\r\n"
				+ "       <Class1> | <Class2> \""+cl2+"\";\r\n"
				+ "                  <Role2> ?Role2;\r\n"
				+ "                  <Role1> ?Role1.\r\n"
				+ "}\r\n"
				+ "LIMIT 1 ";
	
		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, d);
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				RDFNode node1 = soln.get("Role2");
				RDFNode node2 = soln.get("Role1");
			    String role1 = node1.asLiteral().getString();
			    String role2 = node2.asLiteral().getString();
			    
			    roles[0] = role1;
			    roles[1] = role2;
//			    System.out.println("Multi1 is: "+multi1+"  "+multi2);


			}

			d.commit();
		} finally {
			qexec.close();
			// Close the dataset.
			d.end();
		}

		return roles;

	}


//Get the name of the model
public ArrayList<String> getModelName(String cl) {

	ArrayList<String> items = new ArrayList<String>();
	Dataset d = TDBFactory.createDataset(directory);
	Model model = d.getDefaultModel();
	d.begin(ReadWrite.READ);

//	String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> base <http://www.w3.org/TR/html4/> \r\n"
//			+ "SELECT DISTINCT ?model \r\n"
//			+ "WHERE {   ?x <ModelName> ?model ;  \r\n"
//			+ "       		<Class1> | <Class2> \""+cl+"\" . }\r\n"
//			+ "ORDER BY ASC(?model) ";
	
//	String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#>\r\n"
//			+ "base <http://www.w3.org/TR/html4/>\r\n"
//			+ "	SELECT DISTINCT ?model\r\n"
//			+ "WHERE { ?s <ModelName> ?model;\r\n"
//			+ "           	<Class1> | <Class2> ?cl\r\n"
//			+ "  FILTER (regex(?cl,\""+cl+"\", \"i\")).\r\n"
//			+ "}  ";	
	
	String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#>\r\n"
			+ "base <http://www.w3.org/TR/html4/>\r\n"
			+ "	SELECT DISTINCT ?cl\r\n"
			+ "WHERE { ?s <ModelName> ?cl;\r\n"
			+ "  FILTER (regex(?cl,\""+cl+"\", \"i\")).\r\n"
			+ "}  ";	

	Query query = QueryFactory.create(sparqlQueryString);
	QueryExecution qexec = QueryExecutionFactory.create(query, d);
	try {
		ResultSet results = qexec.execSelect();
		for (; results.hasNext();) {
			QuerySolution soln = results.nextSolution();
			String whole = soln.toString();
//			String core = whole.substring(12, whole.length() - 3);
			String core = whole.substring(9, whole.length() - 3);
			items.add(core);

		}

		d.commit();
	} finally {
		qexec.close();
		// Close the dataset.
		d.end();
	}

	return items;

}



//Get the name of the model whom the class belongs to
public ArrayList<String> getModelNameForGivenClass(String cl) {

	ArrayList<String> items = new ArrayList<String>();
	Dataset d = TDBFactory.createDataset(directory);
	Model model = d.getDefaultModel();
	d.begin(ReadWrite.READ);

//	String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> base <http://www.w3.org/TR/html4/> \r\n"
//			+ "SELECT DISTINCT ?model \r\n"
//			+ "WHERE {   ?x <ModelName> ?model ;  \r\n"
//			+ "       		<Class1> | <Class2> \""+cl+"\" . }\r\n"
//			+ "ORDER BY ASC(?model) ";
	
	String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#>\r\n"
			+ "base <http://www.w3.org/TR/html4/>\r\n"
			+ "	SELECT DISTINCT ?model\r\n"
			+ "WHERE { ?s <ModelName> ?model;\r\n"
			+ "           	<Class1> | <Class2> ?cl\r\n"
			+ "  FILTER (regex(?cl,\""+cl+"\", \"i\")).\r\n"
			+ "}  ";	
	
	Query query = QueryFactory.create(sparqlQueryString);
	QueryExecution qexec = QueryExecutionFactory.create(query, d);
	try {
		ResultSet results = qexec.execSelect();
		for (; results.hasNext();) {
			QuerySolution soln = results.nextSolution();
			String whole = soln.toString();
			String core = whole.substring(12, whole.length() - 3);
			items.add(core);

		}

		d.commit();
	} finally {
		qexec.close();
		// Close the dataset.
		d.end();
	}

	return items;

}


//Get all names of the classes had in any connection
public ArrayList<String> getBaseClasses(String cl) {

	ArrayList<String> items = new ArrayList<String>();
	Dataset d = TDBFactory.createDataset(directory);
	Model model = d.getDefaultModel();
	d.begin(ReadWrite.READ);

	String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> \r\n"
			+ "base <http://www.w3.org/TR/html4/> \r\n"
			+ "SELECT DISTINCT ?base_classes\r\n"
			+ "WHERE {\r\n"
			+ "  		 ?s  <Name> \""+cl+"\";\r\n"
			+ "       		 <BaseClass> ?base_classes.\r\n"
			+ "} ";

	Query query = QueryFactory.create(sparqlQueryString);
	QueryExecution qexec = QueryExecutionFactory.create(query, d);
	try {
		ResultSet results = qexec.execSelect();
		for (; results.hasNext();) {
			QuerySolution soln = results.nextSolution();
			String whole = soln.toString();
			String core = whole.substring(19, whole.length() - 3);
			if(core.equals("")) {continue;} 
			else {items.add(core);}

		}

		d.commit();
	} finally {
		qexec.close();
		// Close the dataset.
		d.end();
	}

	return items;

}


public ArrayList<String> getAllModelNames() {

	ArrayList<String> items = new ArrayList<String>();
	Dataset d = TDBFactory.createDataset(directory);
	Model model = d.getDefaultModel();
	d.begin(ReadWrite.READ);

	String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> \r\n"
			+ "base <http://www.w3.org/TR/html4/>\r\n"
			+ "SELECT DISTINCT ?model_names\r\n"
			+ "WHERE\r\n"
			+ "{\r\n"
			+ "	?s <ModelName> ?model_names.\r\n"
			+ "} ";

	Query query = QueryFactory.create(sparqlQueryString);
	QueryExecution qexec = QueryExecutionFactory.create(query, d);
	try {
		ResultSet results = qexec.execSelect();
		for (; results.hasNext();) {
			QuerySolution soln = results.nextSolution();
			String whole = soln.toString();
			String core = whole.substring(18, whole.length() - 3);
			items.add(core);

		}

		d.commit();
	} finally {
		qexec.close();
		// Close the dataset.
		d.end();
	}

	return items;

}


public ArrayList<String> getAllNonDistinctModelNames() {

	ArrayList<String> items = new ArrayList<String>();
	Dataset d = TDBFactory.createDataset(directory);
	Model model = d.getDefaultModel();
	d.begin(ReadWrite.READ);

	String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> \r\n"
			+ "base <http://www.w3.org/TR/html4/>\r\n"
			+ "SELECT ?model_names\r\n"
			+ "WHERE\r\n"
			+ "{\r\n"
			+ "	?s <ModelName> ?model_names.\r\n"
			+ "} ";

	Query query = QueryFactory.create(sparqlQueryString);
	QueryExecution qexec = QueryExecutionFactory.create(query, d);
	try {
		ResultSet results = qexec.execSelect();
		for (; results.hasNext();) {
			QuerySolution soln = results.nextSolution();
			String whole = soln.toString();
			String core = whole.substring(18, whole.length() - 3);
			items.add(core);

		}

		d.commit();
	} finally {
		qexec.close();
		// Close the dataset.
		d.end();
	}

	return items;

}



/// For testing the reused class study


public ArrayList<String> getAllClasssesAndAttributes() {
	

	ArrayList<String> attributes = new ArrayList<String>();
	Dataset d = TDBFactory.createDataset(directory);
	Model model = d.getDefaultModel();
	d.begin(ReadWrite.READ);
	String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#>\r\n"
			+ "base <http://www.w3.org/TR/html4/>\r\n"
			+ "SELECT distinct ?p {\r\n"
//			+ "	?s :hasChild/:hasChild/:hasChild/<Name> ?p\r\n"
			+ "	?s <Name> ?p\r\n"
			+ "\r\n"
			+ "} ";
	
	Query query = QueryFactory.create(sparqlQueryString);
	QueryExecution qexec = QueryExecutionFactory.create(query, d);
//	System.out.println("getAttribute excequted!");
	try {
		ResultSet results = qexec.execSelect();
		for (; results.hasNext();) {
			QuerySolution soln = results.nextSolution();
			String whole = soln.toString();
			String core = whole.substring(8, whole.length() - 3);
			attributes.add(core);
//			System.out.println(core);

		}

		d.commit();
	} finally {
		qexec.close();
		// Close the dataset.
		d.end();
	}

	return attributes;
	
}


public ArrayList<String> getAllAttributes() {
	

	ArrayList<String> attributes = new ArrayList<String>();
	Dataset d = TDBFactory.createDataset(directory);
	Model model = d.getDefaultModel();
	d.begin(ReadWrite.READ);
	String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#>\r\n"
			+ "base <http://www.w3.org/TR/html4/>\r\n"
			+ "SELECT distinct ?atr {    	\r\n"
			+ "		?s <Name> ?cl;\r\n"
			+ "	   :hasChild/:hasChild/<Name> ?atr\r\n"
			+ "} ";
	
	Query query = QueryFactory.create(sparqlQueryString);
	QueryExecution qexec = QueryExecutionFactory.create(query, d);
//	System.out.println("getAttribute excequted!");
	try {
		ResultSet results = qexec.execSelect();
		for (; results.hasNext();) {
			QuerySolution soln = results.nextSolution();
			String whole = soln.toString();

			String core = whole.substring(10, whole.length() - 3);
			attributes.add(core);
//			System.out.println(core);

		}

		d.commit();
	} finally {
		qexec.close();
		// Close the dataset.
		d.end();
	}

	return attributes;
	
}




public ArrayList<String> getBONames() {

	ArrayList<String> items = new ArrayList<String>();
	Dataset d = TDBFactory.createDataset(directory);
	Model model = d.getDefaultModel();
	d.begin(ReadWrite.READ);

	String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#> \r\n"
			+ "base <http://www.w3.org/TR/html4/>\r\n"
			+ "SELECT DISTINCT ?model_names\r\n"
			+ "WHERE\r\n"
			+ "{\r\n"
			+ "	?s <Model_Name> ?model_names.\r\n"
			+ "} ";

	Query query = QueryFactory.create(sparqlQueryString);
	QueryExecution qexec = QueryExecutionFactory.create(query, d);
	try {
		ResultSet results = qexec.execSelect();
		for (; results.hasNext();) {
			QuerySolution soln = results.nextSolution();
			String whole = soln.toString();
			String core = whole.substring(18, whole.length() - 3);
			items.add(core);

		}

		d.commit();
	} finally {
		qexec.close();
		// Close the dataset.
		d.end();
	}

	return items;

}


public ArrayList<String> getModelNames(String modelName) {
	

	ArrayList<String> attributes = new ArrayList<String>();
	Dataset d = TDBFactory.createDataset(directory);
	Model model = d.getDefaultModel();
	d.begin(ReadWrite.READ);
	String sparqlQueryString = " prefix : <http://acandonorway.github.com/XmlToRdf/ontology.ttl#>\r\n"
			+ "base <http://www.w3.org/TR/html4/>\r\n"
			+ "SELECT DISTINCT ?model {    	\r\n"
			+ "		?s <Model_Name> \""+modelName+"\";\r\n"
			+ "	   	 :hasChild/:hasChild/<ModelName> ?model;\r\n"
			+ "} ";
	
	Query query = QueryFactory.create(sparqlQueryString);
	QueryExecution qexec = QueryExecutionFactory.create(query, d);
//	System.out.println("getAttribute excequted!");
	try {
		ResultSet results = qexec.execSelect();
		for (; results.hasNext();) {
			QuerySolution soln = results.nextSolution();
			String whole = soln.toString();

			String core = whole.substring(12, whole.length() - 3);
			attributes.add(core);
//			System.out.println(core);

		}

		d.commit();
	} finally {
		qexec.close();
		// Close the dataset.
		d.end();
	}

	return attributes;
	
}

}
