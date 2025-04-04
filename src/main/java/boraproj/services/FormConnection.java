package boraproj.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import RefClasses.Controls;
import RefClasses.Datasets;

@Service
public class FormConnection {

	
		private String directory = "C:\\Users\\Admin\\Desktop\\Forms\\repo";
		
//		For linkux path
//		private String directory = "formRepo";
		

		
		public static ArrayList<String> retrievedItems;

		
		public FormConnection() { }
		
		public FormConnection(String directory) {

			this.directory = directory;
		}
		
		
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
		
// Get all DLS functionin from the repostiory		
		public ArrayList getAllDSLFunctions(String cl) {

			ArrayList<String> items = new ArrayList<String>();
			Dataset d = TDBFactory.createDataset(directory);
			Model model = d.getDefaultModel();
			d.begin(ReadWrite.READ);

			String sparqlQueryString = " PREFIX schema: <http://schema.org/>\r\n"
					+ "SELECT *\r\n"
					+ "WHERE {\r\n"
					+ "    ?s 	<https://schema.org/Code> ?o .\r\n"
					+ "FILTER regex(?o , \""+cl+"\").} ";
		
			Query query = QueryFactory.create(sparqlQueryString);
			QueryExecution qexec = QueryExecutionFactory.create(query, d);
//			System.out.println("Query executed!");
			try {
				ResultSet results = qexec.execSelect();
				for (; results.hasNext();) {
					QuerySolution soln = results.nextSolution();
					String whole = soln.toString();
					String core = whole.substring(27, whole.length() - 89);
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
		
		
//		Get all meta-classes connected to any Form
		public ArrayList getMetaClassesConnectedToForm() {

			ArrayList<String> items = new ArrayList<String>();
			Dataset d = TDBFactory.createDataset(directory);
			Model model = d.getDefaultModel();
			d.begin(ReadWrite.READ);

			String sparqlQueryString = " PREFIX schema: <http://schema.org>\r\n"
					+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n"
					+ "SELECT DISTINCT ?classes\r\n"
					+ "WHERE {\r\n"
					+ "    ?s 	<https://schema.org/Datatype> ?classes ;\r\n"
					+ "      <https://schema.org/Type> 1.\r\n"
					+ "}";
		
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
		
		
//		Get all meta-classes connected to any Form
		public HashMap getMetaClassesAndUIComponents(String cl) {

//			ArrayList<String> items = new ArrayList<String>();
			HashMap<String,String> cl_comp = new HashMap<String,String>();
			Dataset d = TDBFactory.createDataset(directory);
			Model model = d.getDefaultModel();
			d.begin(ReadWrite.READ);

			String sparqlQueryString = "PREFIX schema: <http://schema.org>\r\n"
					+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n"
					+ "SELECT DISTINCT ?class ?comp\r\n"
					+ "WHERE {\r\n"
					+ "    ?s 	<https://schema.org/Datasource> ?class ;\r\n"
					+ "        <https://schema.org/$type> ?comp;\r\n"
					+ "       FILTER regex(?class , \""+cl+"\").}";
		
			Query query = QueryFactory.create(sparqlQueryString);
			QueryExecution qexec = QueryExecutionFactory.create(query, d);
//			System.out.println("Query executed!");
			try {
				ResultSet results = qexec.execSelect();
				for (; results.hasNext();) {
					QuerySolution soln = results.nextSolution();
					RDFNode metaClass_node = soln.get("class");
					RDFNode UIComponent_node = soln.get("comp");
					String metaClass = metaClass_node.asLiteral().getString();
					String UIComponent = UIComponent_node.asLiteral().getString();
					
					cl_comp.put(metaClass, UIComponent);

				}

				d.commit();
			} finally {
				qexec.close();
				// Close the dataset.
				d.end();
			}

			return cl_comp;

		}
		
//		Get the Controls info from the Forms related to a meta-class
		public ArrayList<Controls> getControlsInfo(String cl) {

			
//			List<ArrayList<String>> allControls = new ArrayList<ArrayList<String>>();
			ArrayList<Controls> allControls = new ArrayList<>();
			Dataset d = TDBFactory.createDataset(directory);
			Model model = d.getDefaultModel();
			d.begin(ReadWrite.READ);

			String sparqlQueryString = " BASE <https://schema.org/>\r\n"
					+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n"
					+ "SELECT DISTINCT  ?d_source ?type ?name\r\n"
					+ "WHERE {{\r\n"
					+ "    ?s <Datasource> ?d_source;\r\n"
					+ "       <$type> ?type;\r\n"
					+ "       <Name> ?name;\r\n"
					+ "  FILTER regex(?d_source , \""+cl+"."+"\")	\r\n"
					+ "  }} ";
		
			Query query = QueryFactory.create(sparqlQueryString);
			QueryExecution qexec = QueryExecutionFactory.create(query, d);
//			System.out.println("Query executed!");
			try {
				ResultSet results = qexec.execSelect();
				for (; results.hasNext();) {
					QuerySolution soln = results.nextSolution();
					RDFNode d_source = soln.get("d_source");
					RDFNode type = soln.get("type");
					RDFNode name = soln.get("name");
					String D_Source = d_source.asLiteral().getString();
					String type_s = type.asLiteral().getString();
					String name_s = name.asLiteral().getString();
					Controls c = new Controls();
					c.setDatasource(D_Source);
					c.setType(type_s);
					c.setName(name_s);
					allControls.add(c);
//					List<String> controls = new ArrayList<>();
//					controls.add(D_Source); controls.add(type_s); controls.add(name_s);
//					allControls.add((ArrayList<String>) controls);
//					controls.clear();

				}

				d.commit();
			} finally {
				qexec.close();
				// Close the dataset.
				d.end();
			}

			return allControls;

		}		
	
		
//	Get the info for all datasets related to a meta-class	
public ArrayList<Datasets> getDataSetsInfo(String cl) {

			
//			List<ArrayList<String>> allControls = new ArrayList<ArrayList<String>>();
			ArrayList<Datasets> allDatasets = new ArrayList<>();
			Dataset d = TDBFactory.createDataset(directory);
			Model model = d.getDefaultModel();
			d.begin(ReadWrite.READ);

			String sparqlQueryString = " prefix rdfs:  <http://www.w3.org/2000/01/rdf-schema#> \r\n"
					+ "base <https://schema.org/>\r\n"
					+ "SELECT DISTINCT ?name ?op ?filter\r\n"
					+ "WHERE {\r\n"
					+ "  	?s rdfs:Class \""+cl+"\" ;\r\n"
					+ "       <Name> ?name ;\r\n"
					+ "      <Operation> ?op ;\r\n"
					+ "      <Filter> ?filter;\r\n"
					+ "} ";
		
			Query query = QueryFactory.create(sparqlQueryString);
			QueryExecution qexec = QueryExecutionFactory.create(query, d);
//			System.out.println("Query executed!");
			try {
				ResultSet results = qexec.execSelect();
				for (; results.hasNext();) {
					QuerySolution soln = results.nextSolution();
					RDFNode filter = soln.get("filter");
					RDFNode op = soln.get("op");
					RDFNode name = soln.get("name");
					String filter_s = filter.asLiteral().getString();
					String op_s = op.asLiteral().getString();
					String name_s = name.asLiteral().getString();
					Datasets c = new Datasets();
					c.setFilter(filter_s);
					c.setOperation(op_s);
					c.setName(name_s);
					allDatasets.add(c);
//					List<String> controls = new ArrayList<>();
//					controls.add(D_Source); controls.add(type_s); controls.add(name_s);
//					allControls.add((ArrayList<String>) controls);
//					controls.clear();

				}

				d.commit();
			} finally {
				qexec.close();
				// Close the dataset.
				d.end();
			}

			return allDatasets;

		}		
		
}
