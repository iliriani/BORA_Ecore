package boraproj.services;

import java.util.ArrayList;
import java.util.HashSet;

public class GetReusedModelElements {
	
	public RepoConnect repo = new RepoConnect("C:\\Users\\Admin\\Desktop\\ZD\\repo");
	
	public GetReusedModelElements(){}

	
	public void getReusedClassesFromDifferentModels() {
		
		ArrayList<String> distinct_models = new ArrayList<>(repo.getBONames());
		
		int total_classes = 0;
		ArrayList<Integer> reused = new ArrayList<>(); 
		
		for(String model_name: distinct_models) {
			
			int count=0;
			ArrayList<String> all_models = new ArrayList<>(repo.getModelNames(model_name));
			for(String ins_model:all_models) {
				
				total_classes++;
				if(!model_name.equals(ins_model)) {count++;}
			}
			reused.add(count);
			
		}
		
		int total=0;
		for(int t: reused) {total+=t;}
		
		System.out.println("Total classes are: "+total_classes);
		System.out.println("Total reused classes are: "+total);
		
//		ArrayList<String> all_models = new ArrayList<>(repo.getAllNonDistinctModelNames());
//		
//		ArrayList<String> distinct_models = new ArrayList<>(repo.getAllModelNames());
//		
//		System.out.println("All classes with ModelName are: "+all_models.size());
//		System.out.println("All classes distinct classes with ModelName are: "+distinct_models.size());
//		int count = all_models.size() - distinct_models.size();
//		System.out.println("Reused models are: "+count);
		
	}
	
	public ArrayList<String> getAllClasses() {
		
//		ArrayList<String> all_classes = new ArrayList<>(repo.getAllClassesFromKG());
//		ArrayList<String> distinct_classes = new ArrayList<>(repo.getAllClasses());
//		ArrayList<String> non_distinct_classes = new ArrayList<>(repo.getNonDistinctClasses());
//		
//		System.out.println("All con_classes are: "+non_distinct_classes.size());
//		System.out.println("Distinct classes are: "+distinct_classes.size());
//		System.out.println("All classes are: "+all_classes.size());
		
		ArrayList<String> classes_attributes = new ArrayList<>(repo.getAllClasssesAndAttributes());
		ArrayList<String> attributes = new ArrayList<>(repo.getAllAttributes());
		
		ArrayList<String> differences = new ArrayList<>(classes_attributes);
		differences.removeAll(attributes);

		System.out.println("Only classes are: "+differences.size()+" "+differences);
//		System.out.println("Length of together: "+classes_attributes.size());
//		System.out.println("Lengh of attributes: "+attributes.size());
//		System.out.println("All classes are: "+(classes_attributes.size()-attributes.size()));
		
		return differences;
	}
	
	public void getConnectedClasses() {
		
		
		ArrayList<String> all_classes = new ArrayList<>(this.getAllClasses());
		ArrayList<String> all_con_classes = new ArrayList<>(repo.getAllClasses());
		
//		ArrayList<String> common = new ArrayList<String>(all_classes);
//		all_classes.retainAll(all_con_classes);
		
		System.out.println("Connected classes are: "+all_con_classes.size());
		
//		ArrayList<String> all_classes = new ArrayList<>(repo.getAllClassesFromKG());
//		ArrayList<String> all_con_classes = new ArrayList<>(repo.getAllClasses());
//		
//		ArrayList<String> differences = new ArrayList<>(all_con_classes);
//		differences.removeAll(all_classes);
//		
//		
//		System.out.println("Total classes: "+all_classes.size()+ " "+all_classes);
//		System.out.println("Total connected classes: "+all_con_classes.size()+" "+all_con_classes );
//		System.out.println("Different classes: "+differences);

	}
	
}
