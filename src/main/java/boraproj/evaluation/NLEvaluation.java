package boraproj.evaluation;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import boraproj.services.BotService;
import boraproj.services.RepoConnect;
import RefClasses.NLModeler;

public class NLEvaluation {
	
	private RepoConnect conn;
	private BotService service;
	
	public NLEvaluation() {
		conn  = new RepoConnect();
		service = new BotService();
	}
	
	public String getRandomClass(){
		
		ArrayList<String> all_classes = new ArrayList<>(conn.getAllClasses());
		int random_class_index = (int) (Math.random()*all_classes.size()-1);
		
		return all_classes.get(random_class_index);
	}
	
	public String getRandomClass(String cl){
		
		ArrayList<String> all_classes = new ArrayList<>(conn.getRelatedDomainClasses(cl));
		int random_class_index = (int) (Math.random()*all_classes.size());
		
		return all_classes.get(random_class_index);
	}

	public String getRandomModel(){
		
		ArrayList<String> all_models = new ArrayList<>(conn.getAllModelNames());
		int random_model_index = (int) (Math.random()*all_models.size()-1);
		
		return all_models.get(random_model_index);
	}
	
	
	public String getUserNLSimulation(String entity) {
		ArrayList<String> nl_descriptions = new ArrayList<>();
//		NL request for classes
		nl_descriptions.add("Give me a "+entity);
		nl_descriptions.add("Give me a "+entity+" class");
		nl_descriptions.add("Give me a please a "+entity+" class!");
		nl_descriptions.add("I like to have a "+entity);
		nl_descriptions.add("I like to have a "+entity+" class");
		nl_descriptions.add("I would like to have a "+entity);
		nl_descriptions.add("I would like to have a "+entity+" class");
		nl_descriptions.add("I would like to have a "+entity+" class");
		nl_descriptions.add("Can I have a "+entity+" class?");
		nl_descriptions.add("Can I have a "+entity+" class please!");
		

		
		int random_nl_index = (int) (Math.random()*nl_descriptions.size()-1);
		
		return nl_descriptions.get(random_nl_index);
	}
	
	
	
	public String getUserNLSimulationForModelClasses(String cl1, String cl2) {
		ArrayList<String> nl_descriptions = new ArrayList<>();
		
//		NL request for models
		nl_descriptions.add("I want a model that has a "+cl1 + " and "+cl2);
		nl_descriptions.add("Give me a model that has a "+cl1 + " and "+cl2);
		nl_descriptions.add("I want a model with a "+cl1 + " and "+cl2);
		nl_descriptions.add("Give me a model with a "+cl1 + " and "+cl2);
		nl_descriptions.add("A model that has a "+cl1 + " and "+cl2);
		nl_descriptions.add("A model with a "+cl1 + " and "+cl2);
		nl_descriptions.add("Can I have a model that has a "+cl1 + " and "+cl2);
		nl_descriptions.add("I would like to have a model with a class "+cl1 + " and "+cl2);
		nl_descriptions.add("Any model with a class "+cl1 +" and "+cl2);
		nl_descriptions.add("Is there any model with that has a class "+cl1+" and "+cl2);
		
		int random_nl_index = (int) (Math.random()*nl_descriptions.size()-1);
		
		return nl_descriptions.get(random_nl_index);
	}
	
	public double getMRR(String description, String entity) {
		Set<String> recommendations = new LinkedHashSet<>();
//		
//		
//		ArrayList<String> rec1 = new ArrayList<>();
//		ArrayList<String> rec2 = new ArrayList<>();
//		if(service.getMatchingModels(description).get(0).getClasses() != null) {
//			rec1 = new ArrayList<>(service.getMatchingModels(description).get(0).getClasses());
//			recommendations.addAll(rec1);}
//			
//		if(service.getMatchingModels(description).get(1).getClasses() != null) {
//			rec2 = new ArrayList<>(service.getMatchingModels(description).get(0).getClasses());
//			recommendations.addAll(rec2);		
//	}
//		ArrayList<String> rec = new ArrayList<>(recommendations);
//		
//		int count = 0;
//		for (int i = 0; i < rec.size(); i++) {
//			if(rec.get(i) == entity) { count = i+1;}
//		}
		
		System.out.println(description);
		ArrayList<String> ml_modeler = new ArrayList<>(service.getMatchingModels(description).get(0).getClasses());
		
//		String ml_modeler = service.getMatchingModels(description).get(0).getDomain();
//		if(ml_modeler.toLowerCase().equals(entity.toLowerCase())) { return 1.0;}
//		return 0.0;
		
//		for (int i = 0; i<ml_modeler.size(); i++) {
//			System.out.println(ml_modeler.get(i).getClasses());
//			recommendations.addAll(ml_modeler.get(i).getClasses());
//		}
		
//		ArrayList<String> rec = new ArrayList<>(recommendations);
		
		int count = 0;
		for (int i = 0; i < ml_modeler.size(); i++) {
			System.out.println(ml_modeler.get(i).toLowerCase()+" == "+entity.toLowerCase());
			if(ml_modeler.get(i).toLowerCase().equals(entity.toLowerCase())) { count = i+1;break;}
		}
		
		if(count == 0) {return 0;}
		
		return 1.0/count;
	}
	
	
	
	public double getMRR_Models(String description, String entity) {
		
		List<NLModeler> rec= service.getMatchingModels(description);
		ArrayList<String> rec_dom = new ArrayList<>();
		System.out.println(description);
		for(int i=0; i< rec.size();i++) {
		String domains = service.getMatchingModels(description).get(i).getDomain();	
		rec_dom.add(domains);}
		
		
		int count = 0;
		for (int i = 0; i < rec_dom.size(); i++) {
			System.out.println(rec_dom.get(i).toLowerCase()+" == "+entity.toLowerCase());
			if(rec_dom.get(i).toLowerCase().equals(entity.toLowerCase())) { count = i+1;break;}
		}
		
		if(count == 0) {return 0;}
		
		return 1.0/count;
	}

}
