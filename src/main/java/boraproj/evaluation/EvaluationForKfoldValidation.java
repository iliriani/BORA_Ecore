package boraproj.evaluation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

import boraproj.controller.NGram;
import boraproj.services.RepoConnect;

public class EvaluationForKfoldValidation {
//	RepoConnect repoCon ;
//	NGram n_gram;
//	Evaluation_2 evaluation;
	
//	String file = "C:\\Users\\Admin\\Desktop\\10-fold evaluation\\1\\train\\repository\\KG-train1.rdf";
//	String repoPath = "C:\\Users\\Admin\\Desktop\\10-fold evaluation\\1\\train\\repository";
	
	
	public EvaluationForKfoldValidation (String path) {
//		this.repoCon = new RepoConnect(path);
//		n_gram = new NGram(path);
	}
	
	public void createRepo(String file, String repoPath) {
		
		RepoConnect repoCon = new RepoConnect(repoPath);
		repoCon.createDataset(file, repoPath);
	}
	
	public ArrayList<String> getOnegram(String cl, String repo){
		NGram ngram = new NGram(repo);
		ArrayList<String> con_classes = ngram.oneGram(cl);
		return con_classes;
	}
	
	public LinkedHashMap getAttributesWithDataTypes(String cl, String repoPath) {
		
		RepoConnect repoCon = new RepoConnect(repoPath);
		ArrayList<String> attributes = repoCon.getAttributes(cl);
		ArrayList<String> dataType;
		LinkedHashMap<String,String> maped = new LinkedHashMap<String,String>();
		
		int i=0;
		for(String a: attributes) {
			String attr = repoCon.getDataType(a);
			maped.put(a, attr);
			i++;
		}
		
		return maped;
	}
	
	public ArrayList<String> getConnectionFrequencies(String cl1, String cl2,String repoPath){
		
		RepoConnect repoCon = new RepoConnect(repoPath);
		return repoCon.getConnectionFrekueny(cl1, cl2);
	}
	
	public ArrayList<String> getConnectionMultiplicities(String cl, String repoPath){
		
		RepoConnect repoCon = new RepoConnect(repoPath);
		return repoCon.getConnectionMultiplicity(cl);
	}
	
	
	public ArrayList<String> getModelUnderConstruction(String repo){
		RepoConnect repoCon = new RepoConnect(repo);
		ArrayList<String> all_classes = new ArrayList<String>();
		all_classes = repoCon.getAllClasses();
		return all_classes;
	}
	
	
	public void compare(String repoTest, String repoTrain) {
		
		ArrayList<String> all_test_classes = new ArrayList<String>(getModelUnderConstruction(repoTest));
		ArrayList<Double> precisions = new ArrayList<Double>();
		ArrayList<Double> recalls = new ArrayList<Double>(); 
		ArrayList<Double> f_measures = new ArrayList<Double>(); 
		ArrayList<Double> map = new ArrayList<Double>(); 
//		System.out.println("Model under contruction: "+all_test_classes);
		
		for(String test_class: all_test_classes) {
			
			ArrayList<String> train_suggestions = new ArrayList<String>(getOnegram(test_class, repoTrain));
			ArrayList<String> test_suggestions = new ArrayList<String>(getOnegram(test_class, repoTest));
//			System.err.println("------------------------------------------");
//			System.out.println("Class for consideration: "+test_class);
//			System.out.println("On the test: "+ test_suggestions);
//			System.out.println("suggestion are: "+train_suggestions);
			
			double precision = getPrecision(test_suggestions, train_suggestions);
			double recall = getRecall(test_suggestions, train_suggestions);
			
			precisions.add(precision); recalls.add(recall); f_measures.add(getfMeaure(precision, recall)); map.add(getmAP(test_suggestions, train_suggestions));
			
//			System.out.println("Precision: "+precision+" , Recall: "+recall+" , F-measure: "+getfMeaure(precision, recall)+ " mAP: "+getmAP(test_suggestions, train_suggestions));
//			System.out.println("+++++++++++++++++++++++++++++++++++++++++");
			
		}
		
		System.out.println("Average precision: "+calculateAverage(precisions)+ " average recall: "+calculateAverage(recalls) + " average fmeaure: "+calculateAverage(f_measures) +" map: "+calculateAverage(map));
				
	}
	
	
	
	public void compareAtttributeAndRelations(String repoTest, String repoTrain) {
		
		ArrayList<String> all_test_classes = new ArrayList<String>(getModelUnderConstruction(repoTest));
		ArrayList<Double> precisions = new ArrayList<Double>();
		ArrayList<Double> recalls = new ArrayList<Double>(); 
		ArrayList<Double> f_measures = new ArrayList<Double>(); 
		ArrayList<Double> map = new ArrayList<Double>(); 
		
		ArrayList<String> test_features = new ArrayList<String>();
		ArrayList<String> train_features = new ArrayList<String>();
		
		for(String test_class: all_test_classes) {
			LinkedHashMap test_attributes = new LinkedHashMap<>(getAttributesWithDataTypes(test_class, repoTest));
			LinkedHashMap train_attributes = new LinkedHashMap<>(getAttributesWithDataTypes(test_class, repoTrain));
			
			ArrayList<String> test_cl_conn = new ArrayList<String>(getConnectionMultiplicities(test_class, repoTest));
			ArrayList<String> train_cl_conn = new ArrayList<String>(getConnectionMultiplicities(test_class, repoTrain));
			
			Set<String> test_attr_set = test_attributes.keySet();
			for(String test_atrr: test_attr_set) {
//				System.out.println(test_atrr + ": "+test_attributes.get(test_atrr));
				String add_test = test_atrr+"-"+test_attributes.get(test_atrr);
				test_features.add(add_test);

			}
			
			Set<String> train_attr_set = train_attributes.keySet();
			for(String train_atrr: train_attr_set) {
				String add_train = train_atrr+"-"+train_attributes.get(train_atrr);
				train_features.add(add_train);

			}
//			System.out.println(test_cl_conn);
//			System.out.println("------------------");
//			System.out.println(train_cl_conn);
			
			test_features.addAll(test_cl_conn);
			train_features.addAll(train_cl_conn);
		
		}
//		System.out.println("Test is: "+test_features);
//		System.out.println("------------------");
//		System.out.println("Train is: "+train_features);
		
		double precision = getPrecision(test_features, train_features);
		double recall = getRecall(test_features, train_features);
		
		precisions.add(precision); recalls.add(recall); f_measures.add(getfMeaure(precision, recall)); map.add(getmAP(test_features, train_features));
		System.out.println("+++++++++++++++++++++++++++++++++++++++++");
		System.out.println("Average precision: "+calculateAverage(precisions)+ " average recall: "+calculateAverage(recalls) + " average fmeaure: "+calculateAverage(f_measures)+" map: "+calculateAverage(map));
		
	}
	
	
	
	public double getPrecision(ArrayList<String> test_classes, ArrayList<String> sug_classes) {
		
		double suggested = sug_classes.size();
		sug_classes.retainAll(test_classes);
		double prec = sug_classes.size()/suggested;
		if (Double.isNaN(prec)) {return 0.0 ;}
		return prec;
	}
	
	
	
	public double getRecall(ArrayList<String> test_classes, ArrayList<String> sug_classes) {
		double relevant = test_classes.size();
		test_classes.retainAll(sug_classes);
		
		double rec = test_classes.size()/relevant;
		if (Double.isNaN(rec)) {return 0.0 ;}
		
		return rec;	
	}
	
	public double getfMeaure(double precision, double recall) {
		
		double numeruesi = 2*(precision*recall);
		double emeruesi = precision + recall;
		
		double f_measure = numeruesi/emeruesi;
		
		if (Double.isNaN(f_measure)) {return 0.0 ;}
		
		return f_measure;
	}
	

	public double getmAP(ArrayList<String> test_classes, ArrayList<String> sug_classes) {
		
		double sum = 0;
		double matches = 0;
		
		for(int i=0; i<sug_classes.size(); i++) {
			if(test_classes.contains(sug_classes.get(i))) {
				matches++;
				sum += (((double)matches)/((double)(i+1)));
			}
		}
		
//		System.out.println("sum is: "+sum+" matches is: "+matches);
		double map = sum/matches;
		if (Double.isNaN(map)) {return 0.0 ;}
		return map;
	}
	
	
	public double calculateMap(ArrayList<String> orig, ArrayList<String> pred, int kmap) {
		double k=0;
//		kmap = pred.size();
		if(orig.size()==0 || pred.size()==0)
			return 0;
		else{
			int corrItems=0;
			for (int i = 0; i < kmap; i++) {
				String tmp = pred.get(i);
				if(orig.contains(tmp)){
					corrItems++;
					k+=(((double)1/kmap)*((double)corrItems/(i+1)));
//					System.err.println((double) 1/kmap + ", " +(double)corrItems/(i+1));
				}
			}
			
		}
		return k;
	}
	
	
	public double calculateAverage(ArrayList <Double> my_list) {
	    return my_list.stream()
	                .mapToDouble(d -> d)
	                .average()
	                .orElse(0.0);
	}
	
}
