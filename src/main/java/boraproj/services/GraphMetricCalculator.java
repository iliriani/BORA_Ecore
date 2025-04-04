package boraproj.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import org.apache.jena.graph.Graph;
//import org.apache.jena.graph.GraphUtil;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.Jena;
import org.apache.jena.graph.GraphUtil;
//import org.apache.jena.graph.Centrality;
import org.apache.jena.tdb.TDB;
//import org.apache.jena.tdb.lib.GraphLib;



public class GraphMetricCalculator {
	
	private RepoConnect repo;
	public static ArrayList<String> visited;
	public static Stack<String> to_visit;
	public static ArrayList<String> temp_classes;
	public static ArrayList<ArrayList<String>> all_chains;
	public static ArrayList<String> consecutives;
	
	public GraphMetricCalculator() {
		
		repo = new RepoConnect("C:\\Users\\Admin\\Desktop\\ZD\\repo");
		visited = new ArrayList<>();
		to_visit = new Stack<>();
		all_chains = new ArrayList<ArrayList<String>>();
		consecutives = new ArrayList<>();
	}
	
	
	/*
	 * get all related classes
	 * 
	 * create another temp list of related classes
	 * 
	 * start to get all relation iteratively of each connected classes while
	 * iteration, remove the classes that appear during the iteration add the
	 * appeating classes within a stack and through each iteration, calculate the
	 * length of a path
	 */
	
	
	public int getGraphOrder() {
		
		ArrayList<String> all_classes = new ArrayList<>(repo.getAllNonDistClassesFromKG());
		System.out.println(all_classes.size());
		return all_classes.size();
		
	}

	
	public int getGraphSize() {
		
		ArrayList<String> all__con_classes = new ArrayList<>(repo.getAllNonDistinctClasses());
		System.out.println(all__con_classes.size());
		return all__con_classes.size();
		
	}
	
	
	public void getGraphDegreeSequence() {
		
		ArrayList<String> all_classes = new ArrayList<>(repo.getAllClasses());
		temp_classes= new ArrayList<>(all_classes);
		
		for(String cl: all_classes) {
			if(!temp_classes.contains(cl)) { continue;}
			else {
			traverse(cl);
			}
		}
		
		for(int i=0; i<all_chains.size();i++) {
			System.out.println(all_chains.get(i) + " : "+all_chains.get(i).size());
		}
		
//		HashMap<String, Integer> lengths = new HashMap<>();
//		
//		for(String cl: all_classes) {
////			ArrayList<String> con_cl = new ArrayList<>(repo.getRelatedClasses(cl));
//			int length = 0;
//			while(traverseConn(cl)) {
//				length++;
//			}
//			
//			lengths.put(cl, length);
//		}
//		
//		for(Entry<String, Integer> set : lengths.entrySet()) {
//			System.out.println("Class: "+set.getKey()+" length: "+set.getValue());
//		}
	}
	
	
	public void traverse(String cl) {
		
		ArrayList<String> con_classes = new ArrayList<>(repo.getRelatedClasses(cl)); 
		System.out.println(cl+" - Connected classes "+con_classes);
		temp_classes.remove(cl);
		System.out.println("temp_classes: "+temp_classes);
		
		consecutives.add(cl);
		
		System.out.println("Consecutives: "+ consecutives);
		visited.add(cl);
		System.out.println("Visited: "+visited);
		
		con_classes.removeAll(visited);
		
		
		//If if a class has no new connections, then this path of consecutives will be added to the chains
		if(con_classes.size() == 0) { all_chains.add(consecutives); consecutives.remove(cl);}
		
		
		else {
		
			to_visit.addAll(con_classes);
		
		while(to_visit.size() > 0) {

			System.out.println("Stack: "+to_visit);
			String curr_cl = to_visit.peek();
			
			traverse(curr_cl);
			}
		}
		System.out.println("-------Graph traverssed successfully!--------------");
		
	}
	
	public boolean traverseConn(String cl) {
		
//		boolean processing = true;	
		visited.add(cl);
		ArrayList<String> con_classes = new ArrayList<>(repo.getRelatedClasses(cl));
		
		con_classes.removeAll(visited);
		con_classes.removeAll(to_visit);
		
		//Graph has been traversed completely
		if(con_classes.size() == 0 && to_visit.size() == 0) {return false;}
		
		to_visit.addAll(con_classes);
		
		while(to_visit.size() > 0) {
//			length++;
			String curr_cl = to_visit.peek();
			traverseConn(curr_cl);
		}
		
		return false;
	}
	
	
	
	public double getGraphDensity() {
		
		Dataset dataset = TDBFactory.createDataset("C:\\Users\\Admin\\Desktop\\ZD\\repo");
		Graph graph = dataset.asDatasetGraph().getDefaultGraph();
//		Graph graph = ModelFactory.createDefaultGraph();
		
//		double density = GraphUtil.density(graph);
//		System.out.println("Density of the graph is: "+density);
		
		//centrality
//		Map<Node, Double> degreeCentrality = GraphUtil.degreeCentrality(graph);

		double E = (double)getGraphSize(); // number of edges
		double V = (double)getGraphOrder(); // number of verteces
		double density = (2 * E) / (V * (V - 1));
		System.out.println("Desnsity is: "+density);
		return density;
		
//		String version = TDB.VERSION;
//		System.out.println("Apache Jena TDB version: " + version);
		
	}
	
	
}
